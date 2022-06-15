package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ArticleCreateDTO dto, @RequestHeader("Authorization") String token) {
        Integer profileId = JwtUtil.decode(token, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(articleDTO);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@PathVariable("id") String id, @RequestBody ArticleCreateDTO dto, @RequestHeader("Authorization") String token){
        Integer profileId = JwtUtil.decode(token, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.update(id, dto, profileId);
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + articleDTO);
    }

    @GetMapping("/list/cat/{id}")
    public ResponseEntity<?> listByCategory(@PathVariable("id") Integer id){
        List<ArticleDTO> list = articleService.listByCategory(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/type")
    public ResponseEntity<?> listByType(@RequestParam(name = "key") String key){
        List<ArticleDTO> list = articleService.listByType(key);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/mod/{id}")
    public ResponseEntity<?> listByModerator(@PathVariable("id") Integer id, @RequestHeader("Authorization") String token){
        JwtUtil.decode(token, ProfileRole.ADMIN);
        List<ArticleDTO> list = articleService.listByModerator(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAll(){
        List<ArticleDTO> list = articleService.listAll();
        return ResponseEntity.ok().body(list);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") String id, @RequestHeader("Authorization") String token){
        JwtUtil.decode(token, ProfileRole.ADMIN);
        articleService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @PutMapping("/publish")
    public ResponseEntity<?> publish(@RequestHeader("Content-ID") String id, @RequestHeader("Authorization") String token){
        Integer profileId = JwtUtil.decode(token, ProfileRole.PUBLISHER);
        articleService.publish(id, profileId);
        return ResponseEntity.ok().body("Published");
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size){
        PageImpl<ArticleDTO> response = articleService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }


}
