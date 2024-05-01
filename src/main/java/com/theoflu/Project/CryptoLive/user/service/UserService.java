package com.theoflu.Project.CryptoLive.user.service;

import com.theoflu.Project.CryptoLive.user.dto.UserDto;
import com.theoflu.Project.CryptoLive.user.entity.Role;
import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.request.AssignRoleReq;
import com.theoflu.Project.CryptoLive.user.request.UserActivateReq;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.response.AssignRoleResponse;
import com.theoflu.Project.CryptoLive.user.response.CodeResponse;
import com.theoflu.Project.CryptoLive.user.response.FavResponse;
import com.theoflu.Project.CryptoLive.verificationCode.entity.EsVerificationCode;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Optional;

public interface UserService {

    FavResponse updateFavStreamer(String username, UserFavRequest userFavRequest);
    String streamkeyCreator (UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException;

    AssignRoleResponse assignRole(AssignRoleReq req);
    Optional<CodeResponse> activationCode(UserActivateReq userActivateReq);
    CodeResponse verificationCode(EsVerificationCode esVerificationCode) throws Exception;
}
