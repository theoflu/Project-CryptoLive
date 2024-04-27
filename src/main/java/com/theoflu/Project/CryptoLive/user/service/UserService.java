package com.theoflu.Project.CryptoLive.user.service;

import com.theoflu.Project.CryptoLive.user.entity.UserEntity;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserService {

    String streamkeyCreator (UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
