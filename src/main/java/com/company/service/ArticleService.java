package com.company.service;

import com.company.dto.article.ArticleFilterDTO;
import com.company.dto.CategoryDTO;
import com.company.dto.ProfileDTO;
import com.company.dto.RegionDTO;
import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleListDTO;
import com.company.dto.article.ArticleResponseDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;
import com.company.enums.LikeStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.mapper.ArticleShortInfo;
import com.company.repository.ArticleLikeRepository;
import com.company.repository.ArticleRepository;
import com.company.repository.custome.CustomArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArticleService {
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private RegionService regionService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ArticleTypeService articleTypeService;
    @Autowired
    private ArticleTagService articleTagService;
    @Autowired
    private ArticleLikeRepository articleLikeRepository;
    @Autowired
    private CustomArticleRepository customArticleRepository;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (User) authentication.getPrincipal();
    }

    public ArticleDTO create(ArticleCreateDTO dto) {
        ArticleEntity entity = new ArticleEntity();
        User user = getCurrentUser();
        Integer pId = Integer.parseInt(user.getUsername());
        return save(entity, dto, pId);

    }

    public ArticleDTO update(String id, ArticleCreateDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("This article not found!");
        }
        ArticleEntity entity = optional.get();
        Integer pId = entity.getModerator().getId();
        if (!Objects.equals(pId, profileId)) {
            throw new BadRequestException("You don't have this article");
        }
        ArticleStatus status = entity.getStatus();
        if (status.equals(ArticleStatus.PUBLISHED)) {
            throw new BadRequestException("Sorry this article already published. You can not edit");
        }
        return save(entity, dto, profileId);
    }

    public List<ArticleDTO> listByCategory(Integer cId) {
        List<ArticleEntity> entityList = articleRepository.findByStatusAndVisibleAndCategoryId(ArticleStatus.PUBLISHED, true, cId);
        if (entityList.isEmpty()) {
            throw new ItemNotFoundException("Not found!");
        }
        return entityToDtoList(entityList);
    }

    public List<ArticleDTO> listByModerator(Integer mId) {
        List<ArticleEntity> entityList = articleRepository.findByStatusAndVisibleAndModeratorId(ArticleStatus.PUBLISHED, true, mId);
        if (entityList.isEmpty()) {
            throw new ItemNotFoundException("Not found");
        }
        return entityToDtoList(entityList);
    }

    public List<ArticleDTO> listByType(String key) {
        List<ArticleEntity> list = articleTypeService.list(key);
        if (list.isEmpty()) {
            throw new ItemNotFoundException("Not found");
        }
        return entityToDtoList(list);
    }

    public List<ArticleDTO> listAll() {
        List<ArticleEntity> entityList = articleRepository.findAllByVisibleAndStatus(true, ArticleStatus.PUBLISHED);
        return entityToDtoList(entityList);
    }

    public void delete(String id) {
        boolean exist = articleRepository.existsByIdAndVisible(id, true);
        if (!exist) {
            throw new BadRequestException("Not found");
        }
        articleRepository.changeStatus(id);
    }

    public void publish(String id, Integer profileId) {
        publishRemove(id, profileId, ArticleStatus.PUBLISHED);
    }

    public void remove(String id, Integer profileId) {
        publishRemove(id, profileId, ArticleStatus.NOT_PUBLISHED);
    }

    public ArticleEntity get(String id) {
        return articleRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Article not found");
        });
    }

    public boolean isExistArticle(String id) {
        return articleRepository.existsById(id);
    }

    public PageImpl<ArticleDTO> pagination(int page, int size) {
        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<ArticleEntity> all = articleRepository.findAll(pageable);

        List<ArticleEntity> entityList = all.getContent();
        List<ArticleDTO> dtoList = entityToDtoList(entityList);

        return new PageImpl<>(dtoList, pageable, all.getTotalElements());
    }

    public ArticleDTO entityToDto(ArticleEntity entity) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getId());
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setContent(entity.getContent());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setCreatedDate(entity.getCreatedDate());

        ProfileEntity moder = entity.getModerator();
        ProfileDTO profileDTO = new ProfileDTO();
        profileDTO.setName(moder.getName());
        profileDTO.setSurname(moder.getSurname());
        profileDTO.setEmail(moder.getEmail());
        articleDTO.setModerator(profileDTO);

        CategoryEntity categoryEntity = entity.getCategory();
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setKey(categoryEntity.getKey());
        categoryDTO.setNameUz(categoryEntity.getNameUz());
        categoryDTO.setNameEn(categoryEntity.getNameEn());
        categoryDTO.setNameRu(categoryEntity.getNameRu());
        articleDTO.setCategory(categoryDTO);

        return articleDTO;
    }

    public List<ArticleDTO> getLast8ArticleNotIn(ArticleListDTO dto) {
        List<ArticleShortInfo> articleList = articleRepository.getLast8ArticleByNotInIdList(dto.getIdList());
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getListByType(String type, int limit) {
        List<ArticleShortInfo> articleList = articleRepository.getByType(type, limit);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getListByType(ArticleListDTO dto, int limit) {
        List<ArticleShortInfo> articleList = articleRepository.getLastArticleByTypeByLimitNotInId(dto.getKey(),
                limit, dto.getIdList());
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getLast5ArticleByCategory2(String categoryKey) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articlePage = articleRepository.findLast5ByCategory(
                categoryKey, ArticleStatus.PUBLISHED, pageable);
//        int n = articlePage.getTotalPages();

        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    private List<ArticleDTO> entityToDtoList(List<ArticleEntity> entityList) {
        List<ArticleDTO> list = new LinkedList<>();
        for (ArticleEntity entity : entityList) {
            ArticleDTO dto = new ArticleDTO();
            dto.setTitle(entity.getTitle());
            dto.setContent(entity.getContent());
            dto.setDescription(entity.getDescription());
            dto.setPublishDate(entity.getPublishDate());
            dto.setSharedCount(entity.getSharedCount());
            dto.setViewCount(entity.getViewCount());
            list.add(dto);
        }
        return list;
    }

    private ArticleDTO save(ArticleEntity entity, ArticleCreateDTO dto, Integer pId) {
        entity.setTitle(dto.getTitle());
        entity.setContent(dto.getContent());
        entity.setDescription(dto.getDescription());

        RegionEntity region = regionService.get(dto.getRegionId());
        entity.setRegion(region);

        CategoryEntity category = categoryService.get(dto.getCategoryId());
        entity.setCategory(category);

        ProfileEntity moderator = new ProfileEntity();
        moderator.setId(pId);
        entity.setModerator(moderator);
        entity.setStatus(ArticleStatus.NOT_PUBLISHED);

        articleRepository.save(entity);

        articleTypeService.create(entity, dto.getTypesList());  // type

        articleTagService.create(entity, dto.getTagList());  // tag
        return entityToDto(entity);
    }

    private void publishRemove(String articleId, Integer pId, ArticleStatus status) {
        ArticleEntity entity = get(articleId);
        if (!entity.getVisible()) {
            throw new ItemNotFoundException("This article was deleted");
        }
        ProfileEntity profile = new ProfileEntity(pId);
        entity.setPublisher(profile);
        entity.setPublishDate(LocalDateTime.now());
        entity.setStatus(status);
        articleRepository.changeStatus(status, profile, LocalDateTime.now(), articleId);
    }

    public ArticleDTO shortDTOInfo(ArticleEntity entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishDate(entity.getPublishDate());
        // TODO image
        return dto;
    }

    private ArticleDTO shortDTOInfo(ArticleShortInfo entity) {
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setPublishDate(entity.getPublishDate());
        // TODO image
        return dto;
    }


    public List<ArticleDTO> sortByName() {
        Sort sort = Sort.by(Sort.Direction.DESC, "name");
        Iterable<ArticleEntity> iterable = articleRepository.findAll(sort); // order by id, name
        iterable.forEach(typesEntity -> {

        });
        return null;
    }

    public List<ArticleDTO> getLast5ArticleByType(String typeKey) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articlePage = articleRepository.findLast5ByType(
                typeKey, pageable);

        List<ArticleDTO> dtoList = new LinkedList<>();
        articlePage.getContent().forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getLast5ArticleByCategory(String categoryKey) {
        CategoryEntity category = categoryService.get(categoryKey);

        List<ArticleEntity> articleList = articleRepository.findTop5ByCategoryAndStatusAndVisibleTrueOrderByCreatedDateDesc(
                category, ArticleStatus.PUBLISHED);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getLast5ArticleByCategory3(String categoryKey) {
        List<ArticleShortInfo> articleList = articleRepository.findTop5ByArticleByCategory2(categoryKey);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    private List<ArticleResponseDTO> responseDTOList(List<ArticleEntity> entityList) {
        List<ArticleResponseDTO> list = new LinkedList<>();
        entityList.forEach(entity -> {
            ArticleResponseDTO dto = new ArticleResponseDTO();
            dto.setTitle(entity.getTitle());
            dto.setDescription(entity.getDescription());
            list.add(dto);
        });
        return list;
    }

    public List<ArticleDTO> get4MostArticle(Integer limit) {
        List<ArticleShortInfo> articleList = articleRepository.get4MostArticle(limit);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getLast4ArticleByTag(String key) {
        Pageable pageable = PageRequest.of(0, 4);
        Page<ArticleEntity> articleList = articleRepository.getLast4ArticleByTag(key, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getLast5ByTagByRegion(String type, String region) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articleList = articleRepository.getLast4ByTypeByRegion(type, region, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(article -> dtoList.add(shortDTOInfo(article)));
        return dtoList;
    }

    public List<ArticleDTO> getListByRegionByPagination(String region, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> articleList = articleRepository.getListByByRegionByPagination(region, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(entity -> dtoList.add(shortDTOInfo(entity)));
        return dtoList;
    }

    public List<ArticleDTO> getLast5ByCategory(String key) {
        Pageable pageable = PageRequest.of(0, 5);
        Page<ArticleEntity> articleList = articleRepository.getListByByCategory(key, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(entity -> dtoList.add(shortDTOInfo(entity)));
        return dtoList;
    }

    public List<ArticleDTO> getListByCategoryByPaging(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ArticleEntity> articleList = articleRepository.getListByByCategory(key, pageable);
        List<ArticleDTO> dtoList = new LinkedList<>();
        articleList.forEach(entity -> dtoList.add(shortDTOInfo(entity)));
        return dtoList;
    }

    public void increaseViewCount(String articleId) {
        ArticleEntity article = get(articleId);
        if (!article.getVisible() && article.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            throw new BadRequestException("This article not found");
        }
        articleRepository.increaseViewCount(articleId);

    }

    public ArticleDTO getFullInfoById(String id, String lang) {
        ArticleEntity entity = get(id);
        if (!entity.getVisible() && entity.getStatus().equals(ArticleStatus.NOT_PUBLISHED)) {
            throw new ItemNotFoundException("This article not found");
        }
        return fullDTOInfo(entity, lang);
    }

    private ArticleDTO fullDTOInfo(ArticleEntity entity, String lang){
        ArticleDTO dto = new ArticleDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setContent(entity.getContent());
        dto.setPublishDate(entity.getPublishDate());
        dto.setSharedCount(entity.getSharedCount());

        RegionDTO regionDTO = new RegionDTO();
        CategoryDTO categoryDTO = new CategoryDTO();

        switch (lang) {
            case "uz" -> {
                regionDTO.setName(entity.getRegion().getNameUz());
                categoryDTO.setName(entity.getCategory().getNameUz());
            }
            case "ru" -> {
                regionDTO.setName(entity.getRegion().getNameRu());
                categoryDTO.setName(entity.getCategory().getNameRu());
            }
            case "en" -> {
                regionDTO.setName(entity.getRegion().getNameEn());
                categoryDTO.setName(entity.getCategory().getNameEn());
            }
        }
        regionDTO.setKey(entity.getRegion().getKey());
        dto.setRegion(regionDTO);

        categoryDTO.setKey(entity.getCategory().getKey());
        dto.setCategory(categoryDTO);

        dto.setViewCount(entity.getViewCount());

        int likeCount = articleLikeRepository.countAllByArticleIdAndStatus(entity.getId(), LikeStatus.LIKE);
        dto.setLikeCount(likeCount);

        List<String> tagList = articleTagService.getTagListByArticle(entity.getId());
        dto.setTagList(tagList);
        return dto;
    }

    public void filter(ArticleFilterDTO dto){
        customArticleRepository.filter(dto);
    }
}
