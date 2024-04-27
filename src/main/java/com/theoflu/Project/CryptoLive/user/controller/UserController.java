package com.theoflu.Project.CryptoLive.user.controller;

import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
@RestController

@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private  final UserService userService;

    @PostMapping("/deneme")
    private String deneme(@RequestBody UserEntity user)throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userService.streamkeyCreator(user);
    }
}
