package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody CategoryDTO dto, HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody CategoryDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String key,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @GetMapping("/list/public")
    public ResponseEntity<?> getArticleList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        List<CategoryDTO> list = categoryService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}
