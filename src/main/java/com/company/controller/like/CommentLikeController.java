package com.company.controller.like;

import com.company.dto.comment.CommentLikeDTO;
import com.company.service.like.CommentLikeService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody CommentLikeDTO dto,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody CommentLikeDTO dto,
                                        HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentDislike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody CommentLikeDTO dto,
                                       HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.removeLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }
}
