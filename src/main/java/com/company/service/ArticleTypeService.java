package com.company.service;

import com.company.entity.ArticleEntity;
import com.company.entity.ArticleTypeEntity;
import com.company.entity.TypesEntity;
import com.company.repository.ArticleTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleTypeService {
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private TypesService typesService;

    public void create(ArticleEntity article, List<Integer> typesList) {
        for (Integer typesId : typesList){
            ArticleTypeEntity articleTypeEntity = new ArticleTypeEntity();
            articleTypeEntity.setArticle(article);
            articleTypeEntity.setTypes(new TypesEntity(typesId));
            articleTypeRepository.save(articleTypeEntity);
        }
    }

    public List<ArticleEntity> list(String key){
        TypesEntity types = typesService.get(key);
        return articleTypeRepository.getEntity(types);
    }
//    public List<ArticleEntity> getByTypeByLimit(Integer typeId, Integer limit){
//        List<ArticleEntity> list = .getByType(typeId, limit);
//        return list;
//    }
}
