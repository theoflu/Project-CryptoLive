package com.theoflu.Project.CryptoLive.user.repository;

import com.theoflu.Project.CryptoLive.user.entity.UserFavStreamerEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface UserFavRepository extends ElasticsearchRepository<UserFavStreamerEntity,Long> {
    UserFavStreamerEntity findByUserId(Long id);
}
