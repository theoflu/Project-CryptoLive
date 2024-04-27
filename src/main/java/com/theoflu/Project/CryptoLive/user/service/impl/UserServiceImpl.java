package com.theoflu.Project.CryptoLive.user.service.impl;

import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Override
    public String streamkeyCreator(UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        String password = "kullanıcı_parolası";

        byte[] salt = new byte[16]; // Rastgele 16 byte salt
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        // PBKDF2 ile anahtar türetme
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(user.getPassword().toCharArray(), salt, 65536, 256); // 256-bit anahtar, 65536 iterasyon
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();

        // Üretilen anahtarı Base64 ile kodla
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);

        // Anahtarı yazdır
        System.out.println("Oluşturulan anahtar: " + base64Key);

        return "Oluşturulan anahtar: " + base64Key;
    }
}
