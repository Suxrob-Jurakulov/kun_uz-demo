package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleLikeEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.LikeStatus;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleLikeRepository;
import com.company.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ArticleLikeService {
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public void articleLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.LIKE);
    }

    public void articleDisLike(String articleId, Integer pId) {
        likeDislike(articleId, pId, LikeStatus.DISLIKE);
    }

    private void likeDislike(String articleId, Integer pId, LikeStatus status) {
        Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        if (optional.isPresent()) {
            ArticleLikeEntity like = optional.get();
            like.setStatus(status);
            articleLikeRepository.save(like);
            return;
        }
        boolean articleExists = articleRepository.existsById(articleId);
        if (!articleExists) {
            throw new ItemNotFoundException("Article NotFound");
        }

        ArticleLikeEntity like = new ArticleLikeEntity();
        like.setArticle(new ArticleEntity(articleId));
        like.setProfile(new ProfileEntity(pId));
        like.setStatus(status);
        articleLikeRepository.save(like);
    }

    public void removeLike(String articleId, Integer pId) {
       /* Optional<ArticleLikeEntity> optional = articleLikeRepository.findExists(articleId, pId);
        optional.ifPresent(articleLikeEntity -> {
            articleLikeRepository.delete(articleLikeEntity);
        });*/
        articleLikeRepository.delete(articleId, pId);
    }
}
