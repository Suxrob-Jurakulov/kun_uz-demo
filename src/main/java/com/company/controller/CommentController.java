package com.company.controller;

import com.company.dto.comment.CommentDTO;
import com.company.service.CommentService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/create/{id}")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto, @PathVariable("id") String id) {
        CommentDTO commentDTO = commentService.create(dto, id);
        return ResponseEntity.ok().body(commentDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") Integer id, @RequestBody CommentDTO dto, @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token);
        commentService.update(id, dto, profileId);
        return ResponseEntity.ok().body("Successfully updated");
    }
}
