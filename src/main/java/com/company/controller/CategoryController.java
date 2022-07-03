package com.company.controller;

import com.company.dto.CategoryDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.CategoryService;
import com.company.util.HttpHeaderUtil;
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
@Api(tags = "Category")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "Create", notes = "Method for create Category")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid CategoryDTO dto, HttpServletRequest request) {
        log.info("Request for create {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        CategoryDTO categoryDTO = categoryService.create(dto);
        return ResponseEntity.ok().body(categoryDTO);
    }

    @ApiOperation(value = "Category list", notes = "Method for get Categories")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<CategoryDTO> list = categoryService.list();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Update", notes = "Method for update Category")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid CategoryDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @ApiOperation(value = "Delete", notes = "Method for delete Category")
    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String key,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        categoryService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @ApiOperation(value = "List", notes = "Method for get Categories for all")
    @GetMapping("/list/public")
    public ResponseEntity<?> getCategoryList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        List<CategoryDTO> list = categoryService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}
