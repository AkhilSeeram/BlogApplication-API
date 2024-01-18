package com.scaler.BlogapiApplication.articles;

import com.scaler.BlogapiApplication.Exceptions.ArticleNotFoundException;
import com.scaler.BlogapiApplication.users.UserEntity;
import com.scaler.BlogapiApplication.users.UsersRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
@Service

public class ArticlesService {
    private final ArticlesRepository articlesRepository;
    private final UsersRepository usersRepository;
    public ArticlesService(ArticlesRepository articlesRepository,UsersRepository usersRepository){
        this.articlesRepository=articlesRepository;
        this.usersRepository=usersRepository;
    }
    public List<ArticleEntity> getArticlesByUser(UUID userid){
        Optional<UserEntity> user=usersRepository.findById(userid);
        List<ArticleEntity> articles =articlesRepository.findByAuthor(user.get());
        return articles;
    }
    public ArticleEntity createArticle(String title,String subtitle,String body,String username){
        UserEntity user=usersRepository.findByUsername(username);
        var savedArticle=articlesRepository.save(ArticleEntity.builder()
                .title(title)
                .subtitle(subtitle)
                .body(body)
                .author(user)
                .build());
        return savedArticle;
    }
    public ArticleEntity getArticleById(UUID id){
        var savedArticle=articlesRepository.findById(id);
        return savedArticle.orElseThrow(() -> new ArticleNotFoundException("Article not found-enter correct details"));
    }
    public List<ArticleEntity> getAllArticles(ArticleFilter articleFilter){
        List<ArticleEntity> articles;
        if(articleFilter==null){
            articles=articlesRepository.findAll();
            return articles;
        }
        if(articleFilter.username!=null){
            UserEntity user=usersRepository.findByUsername(articleFilter.username);
            articles=articlesRepository.findByAuthor(user);
        }
        else{
            articles=articlesRepository.findAll();
        }
        var filteredArticles=articles.stream().filter(article ->{
            if(articleFilter.beforeDate!=null && article.getCreatedAt().after(articleFilter.beforeDate)){
                return false;
            }
            if(articleFilter.afterDate!=null && article.getCreatedAt().before(articleFilter.afterDate)){
                return false;
            }
            return true;
        } ).toList();
        return filteredArticles;
    }



    public static class ArticleFilter {
        String username;
        Date beforeDate;
        Date afterDate;
        static ArticleFilter queryFromParams(String username, Date beforeDate, Date afterDate){
            if(username==null && beforeDate==null && afterDate==null) return null;
            ArticleFilter articleFilter =new ArticleFilter();
            articleFilter.username=username;
            articleFilter.beforeDate=beforeDate;
            return articleFilter;
        }
    }


}
