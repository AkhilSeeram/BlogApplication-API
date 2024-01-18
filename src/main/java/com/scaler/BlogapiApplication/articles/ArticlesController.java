package com.scaler.BlogapiApplication.articles;

import com.scaler.BlogapiApplication.articles.dto.ArticleCreateDto;
import com.scaler.BlogapiApplication.articles.dto.ArticleResponseDto;
import com.scaler.BlogapiApplication.security.TokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/articles")
public class ArticlesController {
    private final ArticlesService articlesService;
    private final ModelMapper modelMapper;
    private final TokenService tokenService;
    public ArticlesController(ArticlesService articlesService, @Autowired ModelMapper modelMapper, @Autowired TokenService tokenService){
        this.articlesService=articlesService;
        this.modelMapper=modelMapper;
        this.tokenService=tokenService;
    }
    @GetMapping("/users/{userId}")
    ResponseEntity<List<ArticleResponseDto>> getArticlesByUser(@PathVariable("userId")UUID userId){
        List<ArticleEntity> articlesByUser=articlesService.getArticlesByUser(userId);
        List<ArticleResponseDto> articleResponseDtos=new ArrayList<>();
        for(ArticleEntity article:articlesByUser){
            articleResponseDtos.add(modelMapper.map(article, ArticleResponseDto.class));
        }
        return ResponseEntity.accepted().body(articleResponseDtos);
    }
    @GetMapping("/{id}")
    ResponseEntity<ArticleResponseDto> getArticleById(@PathVariable("id") UUID id){
        var article=articlesService.getArticleById(id);
        ArticleResponseDto articleResponseDto=modelMapper.map(article, ArticleResponseDto.class);
        return ResponseEntity.accepted().body(articleResponseDto);
    }
    @GetMapping("")
    ResponseEntity<List<ArticleResponseDto>> getAllArticles(
            @RequestParam(value="username", required = false)String username,
            @RequestParam(value="beforeDate", required = false)Date beforeDate,
            @RequestParam(value = "afterDate",required = false) Date afterDate
            ){
       var articleFilter= ArticlesService.ArticleFilter.queryFromParams(username,beforeDate,afterDate);
        List<ArticleEntity> articles=articlesService.getAllArticles(articleFilter);
        List<ArticleResponseDto> articleResponseDtos=new ArrayList<>();
        for(ArticleEntity article:articles){
            articleResponseDtos.add(modelMapper.map(article, ArticleResponseDto.class));
        }
        return ResponseEntity.accepted().body(articleResponseDtos);
    }


    @PostMapping("/users")
    ResponseEntity<ArticleResponseDto> createArticle(@RequestBody ArticleCreateDto articleCreateDto){
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        String username=(String) authentication.getPrincipal();
        ArticleEntity article=articlesService.createArticle(articleCreateDto.getTitle(),articleCreateDto.getSubtitle(),articleCreateDto.getBody(),username);
        ArticleResponseDto articleResponseDto=modelMapper.map(article,ArticleResponseDto.class);
        return ResponseEntity.accepted().body(articleResponseDto);
    }

    @PatchMapping("/{id}")
    ResponseEntity<Void> updateArticle(){
        return null;
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteArticle(){
        return null;
    }

    @PutMapping("/{id}/like")
    ResponseEntity<Void> likeArticle(){
        return null;
    }

    @DeleteMapping("/{id}/like")
    ResponseEntity<Void> unlikeArticle(){
        return null;
    }
}
