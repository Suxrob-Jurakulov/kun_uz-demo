package com.company.controller;

import com.company.dto.TypesDTO;
import com.company.enums.LangEnum;
import com.company.enums.ProfileRole;
import com.company.service.TypesService;
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
@Api(tags = "Type")
@RestController
@RequestMapping("/types")
public class TypesController {
    @Autowired
    private TypesService typesService;

    @ApiOperation(value = "Create", notes = "Method for create type")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid TypesDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for create {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        TypesDTO articleTypeDto = typesService.create(dto);
        return ResponseEntity.ok().body(articleTypeDto);
    }

    @ApiOperation(value = "List", notes = "Method for get types")
    @GetMapping("/adm/list")
    public ResponseEntity<?> list(HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<TypesDTO> list = typesService.list();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Update", notes = "Method for update type")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid TypesDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for update {}", dto);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.update(dto);
        return ResponseEntity.ok().body("Successfully updated");
    }

    @ApiOperation(value = "Delete", notes = "Method for delete type")
    @DeleteMapping("/delete/{key}")
    public ResponseEntity<?> delete(@PathVariable("key") String key,
                                    HttpServletRequest request) {
        log.info("Request for delete {}", key);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        typesService.delete(key);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @ApiOperation(value = "List", notes = "Method for get type by language")
    @GetMapping("/list/public")
//    public ResponseEntity<?> getArticleList(@RequestParam(value = "lang", defaultValue = "uz")LangEnum lang){
    public ResponseEntity<?> getArticleList(@RequestHeader(value = "Accept-Language", defaultValue = "uz") LangEnum lang) {
        log.info("Request for list {}", lang);
        List<TypesDTO> list = typesService.getList(lang);
        return ResponseEntity.ok().body(list);
    }
}
