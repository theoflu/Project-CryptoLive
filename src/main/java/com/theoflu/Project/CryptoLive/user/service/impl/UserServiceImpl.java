package com.theoflu.Project.CryptoLive.user.service.impl;

import com.theoflu.Project.CryptoLive.mail.MailService;
import com.theoflu.Project.CryptoLive.user.entity.Role;
import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import com.theoflu.Project.CryptoLive.user.entity.UserEntity;
import com.theoflu.Project.CryptoLive.user.repository.RoleRepository;
import com.theoflu.Project.CryptoLive.user.repository.UserFavRepository;
import com.theoflu.Project.CryptoLive.user.repository.UserRepository;
import com.theoflu.Project.CryptoLive.user.request.AssignRoleReq;
import com.theoflu.Project.CryptoLive.user.request.UserActivateReq;
import com.theoflu.Project.CryptoLive.user.request.UserFavRequest;
import com.theoflu.Project.CryptoLive.user.response.AssignRoleResponse;
import com.theoflu.Project.CryptoLive.user.response.CodeResponse;
import com.theoflu.Project.CryptoLive.user.response.FavResponse;
import com.theoflu.Project.CryptoLive.user.service.UserService;
import com.theoflu.Project.CryptoLive.verificationCode.entity.EsVerificationCode;
import com.theoflu.Project.CryptoLive.verificationCode.repository.EsVerificationCodeRepo;
import com.theoflu.Project.CryptoLive.verificationCode.service.VerificationCodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.*;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserFavRepository userFavRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationCodeService verificationCodeService;
    private final EsVerificationCodeRepo esVerificationCodeRepo;
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
    public AssignRoleResponse assignRole(AssignRoleReq req) {
        Set<Role> roles = new HashSet<>();
        for(int i=0;i <req.getRoles().size();i++) {
            roles.add(roleRepository.findByName(req.getRoles().get(i)).orElseThrow(() -> new RuntimeException("Error: Role is not found.")));
        }

        Optional<UserEntity> user=userRepository.findByUsername(req.getUsername()).map(
                userEnt-> {

                    userEnt.setRoles(roles);
                    return userRepository.save(userEnt);
                }
        );


       return new AssignRoleResponse("Rol atandı");
    }

    @Override
    public Optional<CodeResponse> activationCode(UserActivateReq userActivateReq) {
        return userRepository.findUserByEmail(userActivateReq.getEmail())
                .map(user->
                {
                    String code=  verificationCodeService.randomCode(user.getId(),user.getEmail());
                   // mailService.sendMail(user.getEmail(),code);
                    return new CodeResponse("Kullanıcı Onay Kodu Gönderildi.");
                });
    }
    @Override
    public CodeResponse verificationCode(EsVerificationCode esVerificationCode)  {
        EsVerificationCode existingCode = esVerificationCodeRepo.findByUserId(esVerificationCode.getUserId());
        if (existingCode != null && existingCode.getCode().equals(esVerificationCode.getCode())) {
            UserEntity user = userRepository.findById(existingCode.getUserId()).get();
            user.setEnabled(true);
            esVerificationCodeRepo.delete(existingCode);
            return new CodeResponse("200");
        } else {
            return new CodeResponse("404");  // Consider a custom exception class
        }
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
