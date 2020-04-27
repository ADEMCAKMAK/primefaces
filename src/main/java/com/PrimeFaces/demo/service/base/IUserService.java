package com.PrimeFaces.demo.service.base;

import com.PrimeFaces.demo.entity.User;
import com.PrimeFaces.demo.service.dto.UserDTO;
import org.springframework.security.core.Authentication;

import java.util.Optional;

public interface IUserService {

    String generateToken(Authentication authentication);

    Optional<UserDTO> findByUsername(String username);

    void register(UserDTO userDTO);

}
