package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleListDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.HttpHeaderUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Slf4j
@Api(tags = "Article")
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;
//
//    @ApiOperation(value = "Article create", notes = "Method for create")
//    @PostMapping("/adm/create")
//    public ResponseEntity<?> create(@RequestBody @Valid ArticleCreateDTO dto,
//                                    HttpServletRequest request) {
//        log.info("Request for create {}", dto);
//        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
//        ArticleDTO articleDTO = articleService.create(dto, profileId);
//        return ResponseEntity.ok().body(articleDTO);
//    }

    @ApiOperation(value = "Article create", notes = "Method for create")
    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody @Valid ArticleCreateDTO dto) {
        log.info("Request for create {}", dto);
        ArticleDTO articleDTO = articleService.create(dto);
        return ResponseEntity.ok().body(articleDTO);
    }

    @ApiOperation(value = "Article update", notes = "Method for update")
    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody @Valid ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        log.info("Request for update {}", dto);
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.update(dto.getId(), dto, profileId);
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + articleDTO);
    }

    @ApiOperation(value = "Article list", notes = "Method for list by category")
    @GetMapping("/list/cat")
    public ResponseEntity<?> listByCategory(@RequestParam(name = "category") Integer id) {
        log.info("Request for listByCategory {}", id);
        List<ArticleDTO> list = articleService.listByCategory(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for list by type")
    @GetMapping("/list/type")
    public ResponseEntity<?> listByType(@RequestParam(name = "key") String key) {
        log.info("Request for listByType {}", key);
        List<ArticleDTO> list = articleService.listByType(key);
        return ResponseEntity.ok().body(list);
    }
    @ApiOperation(value = "Article list", notes = "Method for list by moderator")
    @GetMapping("/adm/list")
    public ResponseEntity<?> listByModerator(@RequestParam(name = "mod") Integer id,
                                             HttpServletRequest request) {
        log.info("Request for listByModerator {}", id);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ArticleDTO> list = articleService.listByModerator(id);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for list all")
    @GetMapping("/public/list")
    public ResponseEntity<?> listAll() {
        List<ArticleDTO> list = articleService.listAll();
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article delete", notes = "Method for delete")
    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String id,
                                    HttpServletRequest request) {
        log.info("Request for delete {}", id);
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        articleService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @ApiOperation(value = "Article publish", notes = "Method for publish")
    @PutMapping("/adm/publish")
    public ResponseEntity<?> publish(@RequestHeader("Content-ID") String id,
                                     HttpServletRequest request) {
        log.info("Request for publish {}", id);
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        articleService.publish(id, profileId);
        return ResponseEntity.ok().body("Published");
    }

    @ApiOperation(value = "Article not_publish", notes = "Method for not_publish")
    @PutMapping("/adm/remove")
    public ResponseEntity<?> remove(@RequestHeader("Content-ID") String id,
                                    HttpServletRequest request) {
        log.info("Request for not_publish {}", id);
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        articleService.remove(id, profileId);
        return ResponseEntity.ok().body("Removed");
    }

    @ApiOperation(value = "Article pagination", notes = "Method for pagination")
    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        log.info("Request for pagination {},{}", page, size);
        PageImpl<ArticleDTO> response = articleService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 5 articles by type")
    @GetMapping("/list/5")
    public ResponseEntity<?> getLast5ArticleByType(@RequestParam(value = "type") String type) {
        log.info("Request for getLast5ArticleByType{}", type);
        List<ArticleDTO> list = articleService.getListByType(type, 5);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 3 articles by type")
    @GetMapping("/public/list/3")
    public ResponseEntity<?> getLast3ArticleByType(@RequestParam(value = "type") String type) {
        log.info("Request for getLast3ArticleByType{}", type);
        List<ArticleDTO> list = articleService.getListByType(type, 3);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 5 articles by type but not in some articles")
    @GetMapping("/list/8")
    public ResponseEntity<?> getLast8ArticleByTypeNotInId(@RequestBody ArticleListDTO dto) {
        log.info("Request for getLast8ArticleByTypeNotInId{}", dto);
        List<ArticleDTO> list = articleService.getLast8ArticleNotIn(dto);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 4 articles by type but not in some")
    @GetMapping("/listByType/4")
    public ResponseEntity<?> getLast4ArticleByTypeNotInId(@RequestBody ArticleListDTO dto) {
        log.info("Request for getLast4ArticleByTypeNotInId{}", dto);
        List<ArticleDTO> list = articleService.getListByType(dto, 4);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for get most 4 articles")
    @GetMapping("/list4_most")
    public ResponseEntity<?> get4MostReadArticle() {
        List<ArticleDTO> list = articleService.get4MostArticle(4);
        return ResponseEntity.ok().body(list);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 5 articles by category")
    @GetMapping("/public/category/{categoryKey}")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByCategory(@PathVariable("categoryKey") String categoryKey) {
        log.info("Request for getLast5ArticleByCategory{}", categoryKey);
        List<ArticleDTO> response = articleService.getLast5ArticleByCategory2(categoryKey);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 4 articles by tag")
    // Talablar : Article-11
    @GetMapping("/listByTag/{tag}")
    public ResponseEntity<?> getLast4ArticleByTag(@PathVariable("tag") String key) {
        log.info("Request for getLast4ArticleByTag{}", key);
        List<ArticleDTO> response = articleService.getLast4ArticleByTag(key);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 5 articles by type and region")
    // Talablar : Article-12
    @GetMapping("/listByTypeByRegion/{type}/{region}")
    public ResponseEntity<?> getLast5eByTypeByRegion(@PathVariable("type") String type, @PathVariable("region") String region) {
        log.info("Request for getLast5eByTypeByRegion{},{}", type, region);
        List<ArticleDTO> response = articleService.getLast5ByTagByRegion(type, region);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get articles by region and Pagination")
    // Talablar : Article-13
    @GetMapping("listByRegionByPage")
    public ResponseEntity<?> getListByRegionByPagination(@RequestParam(value = "region") String region,
                                                         @RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "size") Integer size) {
        log.info("Request for getListByRegionByPagination{},{},{}", region, page, size);
        List<ArticleDTO> response = articleService.getListByRegionByPagination(region, page, size);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get last 5 articles by category")
    // Talablar : Article-14
    @GetMapping("listByCategory")
    public ResponseEntity<?> getLast5ByCategory(@RequestParam("category") String key) {
        log.info("Request for getLast5ByCategory{}", key);
        List<ArticleDTO> response = articleService.getLast5ByCategory(key);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article list", notes = "Method for get articles by category and PAGINATION")
    // Talablar : Article-15
    @GetMapping("listByCategoryByPagination")
    public ResponseEntity<?> getListByCategoryByPaging(@RequestParam("category") String key,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        log.info("Request for getListByCategoryByPaging{},{},{}", key, page, size);
        List<ArticleDTO> response = articleService.getListByCategoryByPaging(key, page, size);
        return ResponseEntity.ok().body(response);
    }

    @ApiOperation(value = "Article view_count", notes = "Method for view_count")
    // Talablar : Article-19
    @PutMapping("/adm/view_count")
    public ResponseEntity<?> increaseViewCount(@RequestHeader("Content-ID") String articleId,
                                               HttpServletRequest request) {
        log.info("Request for increaseViewCount{}", articleId);
        HttpHeaderUtil.getId(request);
        articleService.increaseViewCount(articleId);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Full Article", notes = "Method for get full Article info")
    @GetMapping("/listFullInfo")
    public ResponseEntity<?> fullInfo(@RequestHeader(value = "Accept-Language", defaultValue = "uz") String lang,
                                      @RequestHeader("Content-ID") String id) {
        log.info("Request for fullInfo{},{}", lang, id);
        ArticleDTO response = articleService.getFullInfoById(id, lang);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("")
    public String str(){
        return "Salom";
    }
}
