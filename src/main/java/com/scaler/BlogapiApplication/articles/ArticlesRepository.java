package com.scaler.BlogapiApplication.articles;

import com.scaler.BlogapiApplication.users.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
@Repository
public interface ArticlesRepository extends JpaRepository<ArticleEntity, UUID> {
    List<ArticleEntity> findByAuthor(UserEntity userEntity);
}
