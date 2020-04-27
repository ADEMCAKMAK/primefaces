package com.PrimeFaces.demo.service;


import com.PrimeFaces.demo.entity.User;
import com.PrimeFaces.demo.repository.UserRepository;
import com.PrimeFaces.demo.security.jwt.TokenProvider;
import com.PrimeFaces.demo.service.base.IUserService;
import com.PrimeFaces.demo.service.dto.UserDTO;
import com.PrimeFaces.demo.service.helper.MapperHelper;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;


@Transactional(readOnly = true)
@Service
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public UserService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       TokenProvider tokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.tokenProvider = tokenProvider;
    }

    @Override
    public String generateToken(Authentication authentication) {
        return this.tokenProvider.generateToken(authentication);
    }

    @Override
    @Transactional
    public void register(UserDTO userDTO) {
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        this.userRepository.save(MapperHelper.getMapper().map(userDTO, User.class));
    }

    @Override
    public Optional<UserDTO> findByUsername(String username) {
        return Optional.ofNullable(MapperHelper.getMapper().map(this.userRepository.findByUsername(username).orElse(null), UserDTO.class));
    }

}
