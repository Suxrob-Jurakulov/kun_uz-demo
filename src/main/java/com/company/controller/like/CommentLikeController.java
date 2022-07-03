package com.company.controller.like;

import com.company.dto.comment.CommentLikeDTO;
import com.company.service.like.CommentLikeService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@Api(tags = "Like comment")
@RestController
@RequestMapping("/comment_like")
public class CommentLikeController {

    @Autowired
    private CommentLikeService commentLikeService;

    @ApiOperation(value = "Like", notes = "Method for like")
    @PostMapping("/like")
    public ResponseEntity<Void> like(@RequestBody @Valid CommentLikeDTO dto,
                                     HttpServletRequest request) {
        log.info("Request for like {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Dislike", notes = "Method for dislike")
    @PostMapping("/dislike")
    public ResponseEntity<Void> dislike(@RequestBody @Valid CommentLikeDTO dto,
                                        HttpServletRequest request) {
        log.info("Request for dislike {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.commentDislike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Remove", notes = "Method for remove")
    @PostMapping("/remove")
    public ResponseEntity<Void> remove(@RequestBody @Valid CommentLikeDTO dto,
                                       HttpServletRequest request) {
        log.info("Request for remove {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        commentLikeService.removeLike(dto.getCommentId(), profileId);
        return ResponseEntity.ok().build();
    }
}
