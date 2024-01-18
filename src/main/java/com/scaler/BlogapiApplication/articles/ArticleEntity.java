package com.scaler.BlogapiApplication.articles;

import com.scaler.BlogapiApplication.common.BaseEntity;
import com.scaler.BlogapiApplication.users.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@NoArgsConstructor(force = true)
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
@Entity(name= "articles")
public class ArticleEntity extends BaseEntity {
    @NonNull
    @Column(nullable = false,length = 150)
    String title;
    @Column
    String slug;
    @Column(length = 250)
    String subtitle;
    @NonNull
    @Column(nullable = false, length = 3000)
    String body;

    @ManyToOne
    @JoinColumn(name= "user_entity")
    UserEntity author;

    @ManyToOne(targetEntity = UserEntity.class)
    @JoinTable(
            name = "article_likes",
            joinColumns = {@JoinColumn(name = "article_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    List<UserEntity> likes;
}
