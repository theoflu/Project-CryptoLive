package com.theoflu.Project.CryptoLive.verificationCode.entity;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@Document(indexName = "code")
@EqualsAndHashCode(of="id")
@Builder
public class EsVerificationCode {
    @Id
    private String id;
    private Long userId;
    private  String code;
}