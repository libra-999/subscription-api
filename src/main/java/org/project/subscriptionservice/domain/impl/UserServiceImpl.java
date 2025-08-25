package org.project.subscriptionservice.domain.impl;

import cn.hutool.captcha.generator.RandomGenerator;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.aop.MetaHandler;
import org.project.subscriptionservice.bean.UserEntity;
import org.project.subscriptionservice.bean.enums.AccountStatus;
import org.project.subscriptionservice.config.jwt.Util;
import org.project.subscriptionservice.config.security.UserDetail;
import org.project.subscriptionservice.context.RedisKeyConstant;
import org.project.subscriptionservice.controller.request.Login;
import org.project.subscriptionservice.controller.request.Register;
import org.project.subscriptionservice.controller.request.UserCreation;
import org.project.subscriptionservice.dao.UserDao;
import org.project.subscriptionservice.domain.exception.UserException;
import org.project.subscriptionservice.domain.service.RedisService;
import org.project.subscriptionservice.domain.service.UserService;
import org.project.subscriptionservice.share.entity.MetaData;
import org.project.subscriptionservice.share.entity.PaginationQuery;
import org.project.subscriptionservice.share.entity.Paging;
import org.project.subscriptionservice.share.globalException.HttpException;
import org.project.subscriptionservice.share.utility.CaptchaUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final RedisService redisService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private static final String BASE_CHAR = "ABCDEFGHJKLMNOPQRSTUVWXYZ";
    private static final String BASE_NUM = "1234567890";
    private final Util jwt;


    @Override
    @MetaHandler
    public Paging<UserEntity> list(PaginationQuery query, MetaData metaData) {
        return userDao.list(query.getKeyword(), query.getPage(), query.getSize(), metaData.getUsername());
    }

    @Override
    @MetaHandler
    public UserEntity view(Integer id, MetaData metaData) {
        try{
            return userDao.view(id, metaData.getUsername());
        }catch (HttpException e){
            throw UserException.notFound();
        }
    }

    @Override
    @MetaHandler
    @Transactional(rollbackFor = HttpException.class)
    public UserEntity create(UserCreation request, MetaData metaData) {

        UserEntity user = new UserEntity();
        CheckExistFields(user, request);
        user.setJob("Student");
        user.setPhone(request.getPhone());
        user.setCreatedAt(new Date());
        user.setCreatedBy(metaData.getUsername());

        userDao.create(user);
        return user;
    }

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = HttpException.class)
    public Map<String, String> register(Register entity) {

        UserEntity user = new UserEntity();
        user.setUsername(entity.getUsername());
        user.setPassword(passwordEncoder.encode(entity.getPassword()));
        user.setEmail(entity.getEmail());
        user.setActive(AccountStatus.ACTIVE);
        user.setLocked(0);
        user.setJob(entity.getJob());
        user.setPhone(entity.getPhone());
        user.setCreatedAt(new Date());
        user.setCreatedBy(entity.getUsername());

        userDao.create(user);

        Map<String, String> map = new HashMap<>();
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword())
        );

        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        String token = jwt.generateJwtToken(authentication);

        return MapUserFields(token, userDetail, map);
    }

    @Override
    public Map<String, String> login(Login entity) {
        String code = redisService.getValue(RedisKeyConstant.CAPTCHA + entity.getUuid());
        if (Objects.isNull(code) || !Objects.equals(entity.getCode(), code)) {
            throw UserException.invalidCode();
        }

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(entity.getUsername(), entity.getPassword())
        );
        if (!authentication.isAuthenticated()) {
            throw UserException.notAuthorized();
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwt.generateJwtToken(authentication);
        UserDetail userDetail = (UserDetail) authentication.getPrincipal();
        Map<String, String> map = new HashMap<>();
        return MapUserFields(token, userDetail, map);
    }

    @Override
    public Map<String, String> code(HttpServletRequest res) {
        String uuid = UUID.randomUUID().toString().substring(2);
        Map<String, String> map = new HashMap<>();
        String url = res.getScheme() + "://" +
            res.getServerName() +
            (res.getServerPort() != 80 && res.getServerPort() != 443 ? ":" + res.getServerPort() : "")
            + "/v1/api/image-code?uuid=" + uuid;
        map.put("url", url);
        map.put("uuid", uuid);
        return map;
    }

    @Override
    @SneakyThrows
    public void imageCode(String uuid, HttpServletResponse response) {
        if (Objects.isNull(uuid)) {
            throw UserException.invalidCode();
        }
        CaptchaUtil captchaUtil = new CaptchaUtil();
        RandomGenerator random = new RandomGenerator(BASE_CHAR + BASE_NUM, 4);
        captchaUtil.setGenerator(random);
        captchaUtil.setFont(new Font(Font.MONOSPACED, Font.ITALIC, 50));
        String code = captchaUtil.getCode();

        ServletOutputStream outputStream = null;
        try {
            outputStream = response.getOutputStream();
            captchaUtil.write(outputStream);
            redisService.setValue(RedisKeyConstant.CAPTCHA + uuid, code, 60L, TimeUnit.SECONDS);

        } catch (Exception e) {
            throw new HttpException(HttpStatus.BAD_REQUEST, e.getMessage());
        } finally {
            Objects.requireNonNull(outputStream).close();
        }
    }

    @Override
    public void delete(Integer id, MetaData metaData) {
        userDao.forceDelete(id);
    }

    private Map<String, String> MapUserFields(String token, UserDetail userDetail, Map<String, String> map) {
        map.put("token", token);
        map.put("username", userDetail.getUsername());
        map.put("email", userDetail.getEmail());
        map.put("phone", userDetail.getPhone());
        map.put("job", userDetail.getJob());
        map.put("active", userDetail.getActive().toString());
        return map;
    }

    private void CheckExistFields(UserEntity entity, UserCreation request) {
        if (userDao.getByUsername(request.getUsername()) != null)
            throw UserException.alreadyExists();
        if (userDao.getByEmail(request.getEmail()) != null)
            throw new HttpException(HttpStatus.CONFLICT, "Email already exists");
        if (userDao.getByPhone(request.getPhone()) != null)
            throw new HttpException(HttpStatus.CONFLICT, "Phone already exists");

        entity.setUsername(request.getUsername());
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        entity.setEmail(request.getEmail());
        entity.setActive(AccountStatus.ACTIVE);
        entity.setLocked(0);
    }
}
