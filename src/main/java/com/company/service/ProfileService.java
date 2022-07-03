package com.company.service;

import com.company.dto.AttachDTO;
import com.company.dto.ProfileDTO;
import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;
    @Autowired
    private AttachService attachService;

    public ProfileDTO create(ProfileDTO dto) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }
        ProfileRole role = checkRole(dto.getRole().name());

        ProfileEntity entity = new ProfileEntity();
        entity.setRole(role);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());
        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(true);

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void update(Integer pId, ProfileDTO dto) {

        Optional<ProfileEntity> profile = profileRepository.findById(pId);

        if (profile.isEmpty()) {
            throw new ItemNotFoundException("Profile Not Found ");
        }

//        isValidUpdate(dto);

        ProfileEntity entity = profile.get();


        // 1st photo
        // bor edi yangisiga almashtiradi
        // null

        if (entity.getPhoto() == null && dto.getPhotoId() != null) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        } else if (entity.getPhoto() != null && dto.getPhotoId() == null) {

            attachService.delete(entity.getPhoto().getId());
            entity.setPhoto(null);

        } else if (entity.getPhoto() != null && dto.getPhotoId() != null && entity.getPhoto().getId().equals(dto.getPhotoId())) {

            entity.setPhoto(new AttachEntity(dto.getPhotoId()));

        }

        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        profileRepository.save(entity);

    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findByIdAndVisible(id, true).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found");
        });
    }

    public List<ProfileDTO> list(String role) {
        Iterable<ProfileEntity> all;
        if (role.equals("all")) {
            all = profileRepository.findAll();
        } else {
            all = profileRepository.userList(ProfileStatus.ACTIVE, checkRole(role));
        }
       return entityToDtoList(all);
    }

    private List<ProfileDTO> entityToDtoList(Iterable<ProfileEntity> entityList){
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : entityList) {
            ProfileDTO dto = new ProfileDTO();
            dto.setId(entity.getId());
            dto.setName(entity.getName());
            dto.setSurname(entity.getSurname());
            dto.setEmail(entity.getEmail());
            dto.setCreatedDate(entity.getCreatedDate());
            dtoList.add(dto);
        }
        return dtoList;
    }

    private ProfileRole checkRole(String role) {
        try {
            return ProfileRole.valueOf(role);
        }catch (RuntimeException e){
           throw new BadRequestException("Role is wrong");
        }
    }

    public void delete(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.checkDeleted(ProfileStatus.ACTIVE, id);
        if (optional.isEmpty()) {
            throw new BadRequestException("This user not found or already deleted!");
        }
        profileRepository.changeStatus(ProfileStatus.NOT_ACTIVE, id);
    }

    public String setImage(Integer pId, MultipartFile file) {
        AttachDTO attachDTO = attachService.saveToSystem(file);
        String attachId = attachDTO.getId();
        AttachEntity attachEntity = new AttachEntity(attachId);

        profileRepository.attachSet(pId, attachEntity);
        return "Successfully!";
    }
}
