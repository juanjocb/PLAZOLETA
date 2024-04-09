package com.pragma.powerup.application.handler.impl;


import com.pragma.powerup.application.dto.request.LoginRequestDto;
import com.pragma.powerup.application.dto.request.UserRequestDto;
import com.pragma.powerup.application.dto.response.TokenResponse;
import com.pragma.powerup.application.exception.UserNotLegalAge;
import com.pragma.powerup.application.handler.IUserHandler;
import com.pragma.powerup.application.mapper.UserRequestMapper;
import com.pragma.powerup.domain.api.IUserServicePort;
import com.pragma.powerup.domain.model.Role;
import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.infrastructure.out.jpa.entity.UserEntity;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
//import com.pragma.powerup.infrastructure.security.AuthCredentials;
//import com.pragma.powerup.infrastructure.security.TokenUtils;
import lombok.RequiredArgsConstructor;
import org.mindrot.jbcrypt.BCrypt;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserHandler implements IUserHandler {

    private final IUserServicePort userServicePort;
    private final UserRequestMapper userRequestMapper;
    private final IUserRepository userRepository;
//    private final AuthenticationManager authenticationManager;
//    private final TokenUtils tokenUtils;
    private final UserEntityMapper userEntityMapper;

    @Override
    public void saveUser(UserRequestDto userRequestDto) {

        if(!isOlder(userRequestDto.getBirthDate())){
            throw new UserNotLegalAge();
        }
        User user = userRequestMapper.toUser(userRequestDto);
        user.setPassword(hashPassword(user.getPassword()));
        Role role = new Role(1L, "admin", "this is admin the restaurant");
        user.setRole(role);
        userServicePort.saveUser(user);
    }

//    public TokenResponse loginCliente(LoginRequestDto request) {
//        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
//        User user = userEntityMapper.toUserEntity(userRepository.findOneByEmail(request.getEmail()));
//        String token = tokenUtils.getToken(user);
//        return TokenResponse.builder()
//                .token(token)
//                .role("cliente")
//                .build();
//    }

    public boolean isOlder(LocalDate birthDate) {
        LocalDate newbirthDate = birthDate.plusYears(18);
        return newbirthDate.isBefore(LocalDate.now()) || newbirthDate.equals(LocalDate.now());
    }

    private String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }
}