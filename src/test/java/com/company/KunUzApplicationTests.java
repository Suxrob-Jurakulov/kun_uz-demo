package com.company;

import com.company.dto.article.ArticleDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileStatus;
import com.company.repository.ProfileRepository;
import com.company.service.ArticleService;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
class KunUzApplicationTests {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private ArticleService articleService;

    @Test
    void contextLoads() {
        List<ArticleDTO> list = articleService.listByType(1);
        System.out.println(list);

    }
}
