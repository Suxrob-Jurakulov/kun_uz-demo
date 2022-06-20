package com.company.controller;

import com.company.dto.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
import com.company.util.HttpHeaderUtil;
import com.company.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/types")
public class TypesController {
    @Autowired
    private TypesService typesService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody TypesDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        TypesDTO articleTypeDto = typesService.create(dto);
        return ResponseEntity.ok().body(articleTypeDto);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.list();
        return ResponseEntity.ok().body(list);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody TypesDTO dto,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @GetMapping("/list/public")
//    public ResponseEntity<?> getArticleList(@RequestParam(value = "lang", defaultValue = "uz")LangEnum lang){
    public ResponseEntity<?> getArticleList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        List<TypesDTO> list = typesService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}
