package com.theoflu.Project.CryptoLive.user.controller;

import com.theoflu.Project.CryptoLive.user.configs.JwtUtils;
import com.theoflu.Project.CryptoLive.user.entity.Role;
import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.request.AssignRoleReq;
import com.theoflu.Project.CryptoLive.user.request.UserActivateReq;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.response.CodeResponse;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import com.theoflu.Project.CryptoLive.verificationCode.entity.EsVerificationCode;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.Optional;

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
    @PostMapping("/assignRole")
    public ResponseEntity<?> assignRole(@RequestHeader("Authorization") String token, @RequestBody AssignRoleReq request) {
        String username = jwtUtils.getUserNameFromJwtToken(token.substring(6));

        return ResponseEntity.ok( userService.assignRole(request));
    }

    @PostMapping("/activationCode")
    public CodeResponse activationCode(@RequestBody UserActivateReq req) {
        Optional<CodeResponse> response = userService.activationCode(req);
        if (!response.isEmpty()) {
            return new CodeResponse("Kullanıcı Onay Kodu Gönderildi.");
        } else {
            // Handle the case where activation failed (e.g., return an error response)
            return new CodeResponse(" Activation failed");
        }
    }

    @PostMapping("/activate")
    public ResponseEntity<?> activateAccount(@RequestBody EsVerificationCode req) throws Exception {
        CodeResponse response = userService.verificationCode(req);
        if (response.getMessage().equals("200")) {
            return ResponseEntity.ok().body("Verification completed!");
        } else {
            // Handle the case where verification failed (e.g., return an error response)
            return ResponseEntity.badRequest().body("Verification failed!");
        }
    }

}
