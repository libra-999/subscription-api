package org.project.subscriptionservice.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.project.subscriptionservice.controller.request.Login;
import org.project.subscriptionservice.controller.request.Register;
import org.project.subscriptionservice.controller.response.AuthResponse;
import org.project.subscriptionservice.controller.response.CodeResponse;
import org.project.subscriptionservice.domain.service.UserService;
import org.project.subscriptionservice.share.entity.HttpBodyResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

import static org.project.subscriptionservice.share.controller.ControllerHandler.responseSucceed;

@RequestMapping("v1/api/")
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    @PostMapping("auth/login")
    public ResponseEntity<HttpBodyResponse<AuthResponse>> login(@RequestBody @Validated Login request) {
        Map<String, String> map = service.login(request);
        return AuthMapResponse(map);
    }

    @PostMapping("auth/register")
    public ResponseEntity<HttpBodyResponse<AuthResponse>> register(@RequestBody @Validated Register request) {
        Map<String, String> map = service.register(request);
        return AuthMapResponse(map);
    }

    @GetMapping("/captcha")
    public ResponseEntity<HttpBodyResponse<CodeResponse>> codeGenerate(HttpServletRequest servletRequest) {
        Map<String, String> map = service.code(servletRequest);
        return responseSucceed(CodeResponse.of(map.get("url"), map.get("uuid")));
    }

    @GetMapping("/image-code")
    public ResponseEntity<?> imageCode(String uuid, HttpServletResponse servletResponse) {
        service.imageCode(uuid, servletResponse);
        return responseSucceed();
    }

    private ResponseEntity<HttpBodyResponse<AuthResponse>> AuthMapResponse(Map<String, String> map) {
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(map.get("token"));
        authResponse.setUsername(map.get("username"));
        authResponse.setPhone(map.get("phone"));
        authResponse.setJob(map.get("job"));
        authResponse.setActive(map.get("active"));
        authResponse.setEmail(map.get("email"));
        return responseSucceed(authResponse);
    }


}
