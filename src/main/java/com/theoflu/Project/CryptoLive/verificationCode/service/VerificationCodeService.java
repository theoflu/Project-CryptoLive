package com.theoflu.Project.CryptoLive.verificationCode.service;

public interface VerificationCodeService {
    String randomCode(Long id, String mail);
}
