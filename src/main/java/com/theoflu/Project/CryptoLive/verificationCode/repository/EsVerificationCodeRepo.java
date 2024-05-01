package com.theoflu.Project.CryptoLive.verificationCode.repository;

import com.theoflu.Project.CryptoLive.verificationCode.entity.EsVerificationCode;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.Optional;

public interface EsVerificationCodeRepo  extends ElasticsearchRepository<EsVerificationCode,Long>{


    EsVerificationCode findByUserId(Long aLong);


    EsVerificationCode deleteByUserId(Long id);
}
