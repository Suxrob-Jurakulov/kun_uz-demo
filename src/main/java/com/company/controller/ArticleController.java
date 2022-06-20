package com.company.controller;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleListDTO;
import com.company.enums.ProfileRole;
import com.company.service.ArticleService;
import com.company.util.HttpHeaderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    @PostMapping("/adm/create")
    public ResponseEntity<?> create(@RequestBody ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.create(dto, profileId);
        return ResponseEntity.ok().body(articleDTO);
    }

    @PutMapping("/adm/update")
    public ResponseEntity<?> update(@RequestBody ArticleCreateDTO dto,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.MODERATOR);
        ArticleDTO articleDTO = articleService.update(dto.getId(), dto, profileId);
        return ResponseEntity.ok().body("\tSuccessfully updated \n\n" + articleDTO);
    }

    @GetMapping("/list/cat")
    public ResponseEntity<?> listByCategory(@RequestParam(name = "category") Integer id) {
        List<ArticleDTO> list = articleService.listByCategory(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/type")
    public ResponseEntity<?> listByType(@RequestParam(name = "key") String key) {
        List<ArticleDTO> list = articleService.listByType(key);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/adm/list")
    public ResponseEntity<?> listByModerator(@RequestParam(name = "mod") Integer id,
                                             HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        List<ArticleDTO> list = articleService.listByModerator(id);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list")
    public ResponseEntity<?> listAll() {
        List<ArticleDTO> list = articleService.listAll();
        return ResponseEntity.ok().body(list);
    }

    @DeleteMapping("/adm/delete")
    public ResponseEntity<?> delete(@RequestHeader("Content-ID") String id,
                                    HttpServletRequest request) {
        HttpHeaderUtil.getId(request, ProfileRole.ADMIN);
        articleService.delete(id);
        return ResponseEntity.ok().body("Successfully deleted");
    }

    @PutMapping("/adm/publish")
    public ResponseEntity<?> publish(@RequestHeader("Content-ID") String id,
                                     HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        articleService.publish(id, profileId);
        return ResponseEntity.ok().body("Published");
    }

    @PutMapping("/adm/remove")
    public ResponseEntity<?> remove(@RequestHeader("Content-ID") String id,
                                    HttpServletRequest request) {
        Integer profileId = HttpHeaderUtil.getId(request, ProfileRole.PUBLISHER);
        articleService.remove(id, profileId);
        return ResponseEntity.ok().body("Removed");
    }

    @GetMapping("/pagination")
    public ResponseEntity<?> getPagination(@RequestParam(value = "page", defaultValue = "0") int page,
                                           @RequestParam(value = "size", defaultValue = "2") int size) {
        PageImpl<ArticleDTO> response = articleService.pagination(page, size);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/list/5")
    public ResponseEntity<?> getLast5ArticleByType(@RequestParam(value = "type") String type) {
        List<ArticleDTO> list = articleService.getListByType(type, 5);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/3")
    public ResponseEntity<?> getLast3ArticleByType(@RequestParam(value = "type") String type) {
        List<ArticleDTO> list = articleService.getListByType(type, 3);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list/8")
    public ResponseEntity<?> getLast8ArticleByTypeNotInId(@RequestBody ArticleListDTO dto) {
        List<ArticleDTO> list = articleService.getLast8ArticleNotIn(dto);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/listByType/4")
    public ResponseEntity<?> getLast4ArticleByTypeNotInId(@RequestBody ArticleListDTO dto) {
        List<ArticleDTO> list = articleService.getListByType(dto, 4);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/list4_most")
    public ResponseEntity<?> get4MostReadArticle() {
        List<ArticleDTO> list = articleService.get4MostArticle(4);
        return ResponseEntity.ok().body(list);
    }

    @GetMapping("/category/{categoryKey}")
    public ResponseEntity<List<ArticleDTO>> getLast5ArticleByCategory(@PathVariable("categoryKey") String categoryKey) {
        List<ArticleDTO> response = articleService.getLast5ArticleByCategory2(categoryKey);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-11
    @GetMapping("/listByTag/{tag}")
    public ResponseEntity<?> getLast4ArticleByTag(@PathVariable("tag") String key) {
        List<ArticleDTO> response = articleService.getLast4ArticleByTag(key);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-12
    @GetMapping("/listByTypeByRegion/{type}/{region}")
    public ResponseEntity<?> getLast5eByTypeByRegion(@PathVariable("type") String type, @PathVariable("region") String region) {
        List<ArticleDTO> response = articleService.getLast5ByTagByRegion(type, region);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-13
    @GetMapping("listByRegionByPage")
    public ResponseEntity<?> getListByRegionByPagination(@RequestParam(value = "region") String region,
                                                         @RequestParam(value = "page") Integer page,
                                                         @RequestParam(value = "size") Integer size) {
        List<ArticleDTO> response = articleService.getListByRegionByPagination(region, page, size);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-14
    @GetMapping("listByCategory")
    public ResponseEntity<?> getLast5ByCategory(@RequestParam("category") String key) {
        List<ArticleDTO> response = articleService.getLast5ByCategory(key);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-15
    @GetMapping("listByCategoryByPagination")
    public ResponseEntity<?> getListByCategoryByPaging(@RequestParam("category") String key,
                                                       @RequestParam("page") int page,
                                                       @RequestParam("size") int size) {
        List<ArticleDTO> response = articleService.getListByCategoryByPaging(key, page, size);
        return ResponseEntity.ok().body(response);
    }

    // Talablar : Article-19
    @PutMapping("/adm/view_count")
    public ResponseEntity<?> increaseViewCount(@RequestHeader("Content-ID") String articleId,
                                               HttpServletRequest request) {
        HttpHeaderUtil.getId(request);
        articleService.increaseViewCount(articleId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/listFullInfo")
    public ResponseEntity<?> fullInfo(@RequestHeader(value = "Accept-Language" , defaultValue = "uz") String lang,
                                      @RequestHeader("Content-ID") String id) {
        ArticleDTO response = articleService.getFullInfoById(id, lang);
        return ResponseEntity.ok().body(response);
    }
}
