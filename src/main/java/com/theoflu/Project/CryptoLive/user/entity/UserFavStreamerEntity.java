package com.theoflu.Project.CryptoLive.user.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.ArrayList;
import java.util.List;

@Data
@Document(indexName = "favs")
@EqualsAndHashCode(of="id")
@Builder
public class UserFavStreamerEntity {
    @Id
    private String id;
    private long userId; // takipleyen kullanıcmın idsi
    private ArrayList<Long> favStreamersList;


}
