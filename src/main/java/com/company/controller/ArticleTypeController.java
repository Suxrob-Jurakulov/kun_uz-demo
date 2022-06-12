package com.company.controller;

import com.company.dto.TypesDTO;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("article_type")
public class ArticleTypeController {
    @Autowired
    private TypesService typesService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TypesDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        TypesDTO articleTypeDto = typesService.create(dto);
        return ResponseEntity.ok().body(articleTypeDto);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/update/{key}")
    public ResponseEntity<?> update(@PathVariable("key") String key, @RequestBody TypesDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        typesService.update(dto, key);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        typesService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }
}
