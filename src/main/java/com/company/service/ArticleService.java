package com.company.service;

import com.company.dto.article.ArticleCreateDTO;
import com.company.dto.article.ArticleDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CategoryEntity;
import com.company.entity.ProfileEntity;
import com.company.entity.RegionEntity;
import com.company.enums.ArticleStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
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

    public ArticleDTO create(ArticleCreateDTO dto, Integer profileId) {
        ArticleEntity entity = new ArticleEntity();
        return save(entity, dto, profileId);

    }

    public boolean isExistArticle(String id){
        return articleRepository.existsById(id);
    }

    public ArticleDTO update(String id, ArticleCreateDTO dto, Integer profileId) {
        Optional<ArticleEntity> optional = articleRepository.findById(id);
        if (optional.isEmpty()) {
            throw new ItemNotFoundException("This article not found!");
        }
        ArticleEntity entity = optional.get();
        return save(entity, dto, profileId);
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

        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(entity.getId());
        articleDTO.setTitle(entity.getTitle());
        articleDTO.setContent(entity.getContent());
        articleDTO.setDescription(entity.getDescription());
        articleDTO.setCreatedDate(entity.getCreatedDate());
        articleDTO.setModerator(entity.getModerator());
        articleDTO.setCategory(entity.getCategory());

        return articleDTO;
    }

    public List<ArticleDTO> listByCategory(Integer cId) {
        List<ArticleEntity> entityList = articleRepository.findByStatusAndVisibleAndCategoryId(ArticleStatus.PUBLISHED, true, cId);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Not found!");
        }
        return entityToDtoList(entityList);
    }

    public List<ArticleDTO> listByModerator(Integer mId) {
        List<ArticleEntity> entityList = articleRepository.findByStatusAndVisibleAndModeratorId(ArticleStatus.PUBLISHED, true, mId);
        if (entityList.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        return entityToDtoList(entityList);
    }

    public List<ArticleDTO> listByType(Integer id) {
        List<ArticleEntity> list = articleTypeService.list(id);
        if (list.isEmpty()){
            throw new ItemNotFoundException("Not found");
        }
        return entityToDtoList(list);
    }

    public void delete(String id) {
        boolean exist = articleRepository.existsByIdAndVisible(id, true);
        if (!exist){
            throw new BadRequestException("Not found");
        }
        articleRepository.changeStatus(id);
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
}
