package com.company;

import com.company.dto.article.ArticleDTO;
import com.company.dto.article.ArticleResponseDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.TypesEntity;
import com.company.enums.ArticleStatus;
import com.company.enums.LikeStatus;
import com.company.repository.ArticleLikeRepository;
import com.company.repository.ArticleRepository;
import com.company.repository.ArticleTypeRepository;
import com.company.repository.ProfileRepository;
import com.company.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class KunUzApplicationTests {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ArticleTypeRepository articleTypeRepository;
    @Autowired
    private ArticleRepository articleRepository;
    @Autowired
    private ArticleLikeRepository articleLikeRepository;


    @Test
    void contextLoads() {
//
//        int i = articleLikeRepository.countAllByArticleIdAndStatus("8a8a851381677274018167740d740000", LikeStatus.LIKE);
//        System.out.println(i);
    }
}
