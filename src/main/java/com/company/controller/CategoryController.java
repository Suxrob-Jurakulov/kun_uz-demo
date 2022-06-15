package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.dto.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(@RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/update/{key}")
    public ResponseEntity<?> update(@PathVariable("key") String key, @RequestBody CategoryDTO dto, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        categoryService.update(dto, key);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key, @RequestHeader("Authorization") String token) {
        JwtUtil.decode(token, ProfileRole.ADMIN);
        categoryService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @GetMapping("/list/public")
    public ResponseEntity<?> getArticleList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang){
        List<CategoryDTO> list = categoryService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}
