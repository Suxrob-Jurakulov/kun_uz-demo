package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.repository.ArticleLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleService articleService;

    public void like(Integer profileId, String articleId, int mark) {
        ArticleEntity article = articleService.get(articleId);
        ProfileEntity profile = new ProfileEntity(profileId);

        ArticleLikeEntity entity = new ArticleLikeEntity();
        switch (mark) {
            case 1 -> entity.setStatus(true);
            case 0 -> entity.setStatus(false);
            default -> entity.setStatus(null);
        }
        entity.setArticle(article);
        entity.setProfile(profile);
        articleLikeRepository.save(entity);
    }
}
