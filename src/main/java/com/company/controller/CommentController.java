package com.company.controller;

import com.company.dto.article.ArticleDTO;
import com.company.dto.comment.CommentDTO;
import com.company.enums.ProfileRole;
import com.company.service.CommentService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/adm/create/{id}")
    public ResponseEntity<?> create(@RequestBody CommentDTO dto,
                                    @PathVariable("id") String id,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        CommentDTO commentDTO = commentService.create(dto, id, profileId);
        return ResponseEntity.ok().body(commentDTO);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody CommentDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.update(dto, profileId);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @GetMapping("/list/byArticle")
    public ResponseEntity<?> listByArticle(@RequestBody ArticleDTO dto){
        List<CommentDTO> list = commentService.list(dto);
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> deleteByAdmin(@RequestHeader("Content-ID") Integer id,
                                           HttpServletRequest request){
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        commentService.delete(id);
        return ResponseEntity.ok().body("Success");
    }

    @DeleteMapping("/adm/delete/byUser")
    public ResponseEntity<?> deleteByUser(@RequestHeader("Content-ID") Integer id,
                                          HttpServletRequest request){
        Integer profileId = HttpHeaderUtil.getId(request);
        commentService.delete(profileId, id);
        return ResponseEntity.ok().body("Deleted");
    }
}
