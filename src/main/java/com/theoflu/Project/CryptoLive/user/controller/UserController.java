package com.theoflu.Project.CryptoLive.user.controller;

import com.theoflu.Project.CryptoLive.user.configs.JwtUtils;
import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
@RestController

@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private  final UserService userService;
    private final JwtUtils jwtUtils;
    @PostMapping("/resetStreamkey")
    private String resetStreamkey(@RequestBody UserEntity user)throws NoSuchAlgorithmException, InvalidKeySpecException {
        return userService.streamkeyCreator(user);
    }

    @PostMapping("/favourite")
    public ResponseEntity<?> favourite(@RequestHeader("Authorization") String token, @RequestBody UserFavRequest request) {
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(6));
        return ResponseEntity.ok( userService.updateFavStreamer(username,request));
    }

}
