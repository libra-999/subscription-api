package org.project.subscriptionservice.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.project.subscriptionservice.dao.UserDao;
import org.project.subscriptionservice.domain.exception.UserException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailService implements UserDetailsService {

    private final UserDao repository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            return UserDetail.build(repository.getByUsername(username));
        } catch (InternalAuthenticationServiceException e) {
            throw UserException.notAuthorized();
        }
    }
}
