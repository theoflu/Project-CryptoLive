package com.theoflu.Project.CryptoLive.verificationCode.service;


import com.theoflu.Project.CryptoLive.verificationCode.entity.EsVerificationCode;
import com.theoflu.Project.CryptoLive.verificationCode.repository.EsVerificationCodeRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class VerificationCodeServiceImpl implements VerificationCodeService {
    private final PasswordEncoder passwordEncoder;
    private final EsVerificationCodeRepo esVerificationCodeRepo;
    @Override
    public String randomCode(Long id, String mail){
        Date currentDate = new Date();
        long c= id.toString().getBytes().length;
        long d= mail.getBytes().length;
        long e=passwordEncoder.encode(id+mail).getBytes().length;
        long f=passwordEncoder.encode(id.toString()).getBytes().length;
        long g=passwordEncoder.encode(mail).getBytes().length;
        long milliseconds = currentDate.getTime();
        long code=(c*d*f*g*milliseconds)/e;
        int intValue = (int) code;
        String asd=""+intValue;
        String middleFourDigits = asd.replaceAll(".*?(\\d{4}).*", "$1");
        save(id,middleFourDigits);
        return  middleFourDigits ;

    }
    public EsVerificationCode save(Long id, String code) {
        EsVerificationCode existingCode = esVerificationCodeRepo.findByUserId(id);
        EsVerificationCode esVerificationCode;
        if (existingCode != null) {
            esVerificationCode = EsVerificationCode.builder()
                    .id(existingCode.getId())
                    .userId(id)
                    .code(code)
                    .build();
        } else {
            esVerificationCode = EsVerificationCode.builder()
                    .userId(id)
                    .code(code)
                    .build();
        }
        return esVerificationCodeRepo.save(esVerificationCode);
    }
}