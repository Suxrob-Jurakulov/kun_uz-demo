package com.company.controller.like;

import com.company.dto.article.ArticleLikeDTO;
import com.company.service.like.ArticleLikeService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RequestMapping("/article_like")
@RestController
public class ArticleLikeController {

    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody ArticleLikeDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.articleLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody ArticleLikeDTO dto,
                                        HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.articleDisLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody ArticleLikeDTO dto,
                                       HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        articleLikeService.removeLike(dto.getArticleId(), profileId);
        return ResponseEntity.ok().build();
    }

}
