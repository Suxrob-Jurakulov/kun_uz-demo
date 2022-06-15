package com.company.controller;

import com.company.service.CommentLikeService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("comment/like")
public class CommentLikeController {
    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<?> like(@RequestParam(value = "like") int mark,
                                  @RequestHeader("Content-ID") Integer commentId,
                                  @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token);
        commentLikeService.like(mark, commentId, profileId);
        return ResponseEntity.ok().body("Like");
    }
}
