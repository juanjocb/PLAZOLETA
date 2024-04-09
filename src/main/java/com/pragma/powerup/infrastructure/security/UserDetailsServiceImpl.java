package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.domain.model.User;
import com.pragma.powerup.infrastructure.out.jpa.mapper.UserEntityMapper;
import com.pragma.powerup.infrastructure.out.jpa.repository.IUserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final IUserRepository userRepository;
    private final UserEntityMapper userEntityMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userEntityMapper.toUserEntity(userRepository
                .findOneByEmail(email));

        if(user != null){
            return new UserDetailsImpl(user);
        }

        throw new UsernameNotFoundException("Usuario no encontrado con email: " + email);

    }
}
