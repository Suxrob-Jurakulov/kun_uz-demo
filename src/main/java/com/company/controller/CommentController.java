package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.dto.comment.CommentDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto, @PathVariable("id") String id, @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token);
        CommentDTO commentDTO = commentService.create(dto, id, profileId);
        return ResponseEntity.ok().body(commentDTO);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto, @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token);
        commentService.update(dto, profileId);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @GetMapping("/list/byArticle")
    public ResponseEntity<?> listByArticle(@RequestBody ArticleDTO dto){
        List<CommentDTO> list = commentService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteByAdmin(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token){
        JwtUtil.decode(token, ProfileRole.ADMIN);
        commentService.delete(id);
        return ResponseEntity.ok().body("Success");
    }

    @DeleteMapping("/delete/byUser/{id}")
    public ResponseEntity<?> deleteByUser(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token){
        Integer profileId = JwtUtil.decode(token);
        commentService.delete(profileId, id);
        return ResponseEntity.ok().body("Deleted");
    }
}
