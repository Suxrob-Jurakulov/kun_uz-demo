package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.dto.article.ArticleDTO;
import com.company.dto.comment.CommentDTO;
import com.company.entity.ArticleEntity;
import com.company.entity.CommentEntity;
import com.company.entity.ProfileEntity;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleService articleService;
    @Autowired
    private ProfileService profileService;

    public CommentDTO create(CommentDTO dto, String id, Integer profileId) {
        boolean existArticle = articleService.isExistArticle(id);
        if (!existArticle) {
            throw new BadRequestException("Article not found");
        }

        CommentEntity entity = new CommentEntity();
        entity.setContent(dto.getContent());
        entity.setVisible(true);

        ProfileEntity profile = profileService.get(profileId);
        entity.setProfile(profile);

        ArticleEntity article = new ArticleEntity(id);
        entity.setArticle(article);

        commentRepository.save(entity);

        dto.setId(entity.getId());
        dto.setCreatedDate(entity.getCreatedDate());
        return dto;
    }

    public void update(CommentDTO dto, Integer profileId) {
        Optional<CommentEntity> optional = commentRepository.findById(dto.getId());
        if (optional.isEmpty()) {
            throw new BadRequestException("Not found");
        }
        ProfileEntity profile = profileService.get(profileId);

        if (!Objects.equals(profile.getId(), dto.getProfile().getId())) {
            throw new BadRequestException("Mazgi bu sening commenting emas");
        }

        CommentEntity entity = optional.get();
        entity.setContent(dto.getContent());
        entity.setUpdatedDate(LocalDateTime.now());
        commentRepository.save(entity);
    }

    public List<CommentDTO> list(ArticleDTO dto) {
        boolean existArticle = articleService.isExistArticle(dto.getId());
        if (!existArticle) {
            throw new ItemNotFoundException("Article not found");
        }
        ArticleEntity entity = new ArticleEntity(dto.getId());
        List<CommentEntity> commentEntityList = commentRepository.findByArticle(entity);

        return entityToDtoList(commentEntityList);
    }

    private List<CommentDTO> entityToDtoList(List<CommentEntity> entityList) {
        List<CommentDTO> list = new LinkedList<>();
        for (CommentEntity ent : entityList) {
            CommentDTO dto1 = new CommentDTO();
            dto1.setContent(ent.getContent());
            dto1.setCreatedDate(ent.getCreatedDate());

            ProfileDTO profileDTO = new ProfileDTO();
            profileDTO.setName(ent.getProfile().getName());
            profileDTO.setSurname(ent.getProfile().getSurname());
            profileDTO.setEmail(ent.getProfile().getEmail());

            dto1.setProfile(profileDTO);

            ArticleDTO articleDTO = new ArticleDTO();
            articleDTO.setTitle(ent.getArticle().getTitle());
            articleDTO.setContent(ent.getArticle().getContent());
            articleDTO.setDescription(ent.getArticle().getDescription());

            dto1.setArticle(articleDTO);

            list.add(dto1);
        }
        return list;
    }

    public void delete(Integer id) {
        Optional<CommentEntity> optional = commentRepository.findById(id);
        changeVisible(optional);
    }

    public void delete(Integer profileId, Integer id) {
        Optional<CommentEntity> optional = commentRepository.findByIdAndProfileId(id, profileId);
        changeVisible(optional);
    }

    public CommentEntity get(Integer id) {
        return commentRepository.findById(id).orElseThrow(() -> {
            throw new BadRequestException("Comment not found");
        });
    }

    private void changeVisible(Optional<CommentEntity> optional) {
        if (optional.isEmpty()) {
            throw new BadRequestException("Something went wrong");
        }
        CommentEntity entity = optional.get();
        entity.setVisible(false);
        commentRepository.save(entity);
    }

}
