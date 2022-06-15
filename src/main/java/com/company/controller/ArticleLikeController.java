package com.company.controller;

import com.company.service.ArticleLikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/articleLike")
public class ArticleLikeController {
    @Autowired
    private ArticleLikeService articleLikeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam(value = "like", defaultValue = "-1") int mark,
                                  @RequestHeader("Content-ID") String articleId,
                                  @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token);
        articleLikeService.like(profileId, articleId, mark);
        return ResponseEntity.ok().body("Like");
    }
}
