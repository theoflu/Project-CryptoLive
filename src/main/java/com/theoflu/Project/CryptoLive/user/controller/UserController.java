package com.theoflu.Project.CryptoLive.user.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@org.springframework.stereotype.Controller
@CrossOrigin(origins = "*", maxAge = 3600,allowedHeaders = "*")
@RestController

@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
}
