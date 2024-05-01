package com.theoflu.Project.CryptoLive.user.service.impl;

import com.theoflu.Project.CryptoLive.mail.MailService;
import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.repository.UserFavRepository;
import com.theoflu.Project.CryptoLive.user.repository.UserRepository;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.response.FavResponse;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFavRepository userFavRepository;
    private final PasswordEncoder passwordEncoder;
    private final MailService mailService;

    @Override
    public String streamkeyCreator(UserEntity user) throws NoSuchAlgorithmException, InvalidKeySpecException {

        byte[] salt = new byte[16]; // Rastgele 16 byte salt
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        // PBKDF2 ile anahtar türetme
        String forUserStreamKey= user.getUsername()+" "+ user.getEmail()+" "+ user.getPassword();
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(forUserStreamKey.toCharArray(), salt, 65536, 256); // 256-bit anahtar, 65536 iterasyon
        byte[] keyBytes = factory.generateSecret(spec).getEncoded();
        // Üretilen anahtarı Base64 ile kodla
        String base64Key = Base64.getEncoder().encodeToString(keyBytes);
        // Anahtarı yazdır

        //mailService.sendMail(user.getEmail(),"base64Key");
        return  base64Key;
    }
    @Override
    public FavResponse updateFavStreamer(String username, UserFavRequest userFavRequest) {
        UserEntity user= userRepository.findUserEntityByUsername(username); // takipleyecek adam
        UserEntity favUser= userRepository.findUserEntityByUsername(userFavRequest.getFavStreamerName()); // takiplenecek adamı bul
        UserFavStreamerEntity favs = userFavRepository.findByUserId(user.getId());// kullanıcımın favorilerini bulmuş oldum
        if (favs.getFavStreamersList().contains(favUser.getId())){
            favs.getFavStreamersList().remove(favUser.getId());
            userFavRepository.save(favs);
            return new FavResponse("Yayıncı Takipten Çıkarıldı.");
        }
        else {
            favs.getFavStreamersList().add(favUser.getId());// favori listesine ekledim
            userFavRepository.save(favs);
            return new FavResponse("Yayıncı Takip Edildi.");

        }

    }


}
