package com.theoflu.Project.CryptoLive.user.service;

import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.response.FavResponse;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

public interface UserService {

    FavResponse updateFavStreamer(String username, UserFavRequest userFavRequest);
    String streamkeyCreator (UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException;
}
