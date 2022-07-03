package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.dto.comment.CommentDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "Comment")
@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @ApiOperation(value = "Create", notes = "Method for create comment")
    @PostMapping("/adm/create/{id}")
    public ResponseEntity<?> create(@RequestBody @Valid CommentDTO dto,
                                    @PathVariable("id") String id,
                                    HttpServletRequest request) {
        log.info("Request for create comment {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        CommentDTO commentDTO = commentService.create(dto, id, profileId);
        return ResponseEntity.ok().body(commentDTO);
    }

    @ApiOperation(value = "Update", notes = "Method for update comment")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for update comment {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.update(dto, profileId);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @ApiOperation(value = "List", notes = "Method for get comment list by article")
    @GetMapping("/list/byArticle")
    public ResponseEntity<?> listByArticle(@RequestBody ArticleDTO dto){
        log.info("Request for comment list {}", dto);
        List<CommentDTO> list = commentService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Delete", notes = "Method for delete comment by admin")
    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> deleteByAdmin(@RequestHeader("Content-ID") Integer id,
                                           HttpServletRequest request){
        log.info("Request for delete comment {}", id);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        commentService.delete(id);
        return ResponseEntity.ok().body("Success");
    }

    @ApiOperation(value = "Delete by user", notes = "Method for delete comment by user")
    @DeleteMapping("/adm/delete/byUser")
    public ResponseEntity<?> deleteByUser(@RequestHeader("Content-ID") Integer id,
                                          HttpServletRequest request){
        log.info("Request for delete comment by user {}", id);
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.delete(profileId, id);
        return ResponseEntity.ok().body("Deleted");
    }
}
