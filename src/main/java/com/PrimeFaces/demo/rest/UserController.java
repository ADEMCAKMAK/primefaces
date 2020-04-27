package com.PrimeFaces.demo.rest;

import com.PrimeFaces.demo.rest.base.IUserController;
import com.PrimeFaces.demo.service.UserService;
import com.PrimeFaces.demo.service.dto.UserDTO;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;


@RestController
@RequestMapping("/user")
public class UserController implements IUserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public void register(@Valid @RequestBody UserDTO userDTO,
                         HttpServletRequest request,
                         HttpServletResponse response) {
        this.userService.register(userDTO);
    }

}
