package com.scaler.BlogapiApplication.users;

import com.scaler.BlogapiApplication.articles.ArticleEntity;
import com.scaler.BlogapiApplication.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;
@Setter
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor()
@RequiredArgsConstructor
@Getter
@Entity(name = "users")
public class UserEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false,unique = true, length = 30)
    String username;
    @NonNull
    @Column(nullable = false,unique = true, length = 50)
    String email;
    @NonNull
    @Column(nullable = false)
    String password;
    @Column
    String bio;

    @ManyToMany(targetEntity = UserEntity.class, mappedBy = "following")
    List<UserEntity> followers;

    @ManyToMany
    List<UserEntity> following;

    @ManyToMany(targetEntity = ArticleEntity.class, mappedBy = "likes")
    List<ArticleEntity> favourite_Articles;
}
