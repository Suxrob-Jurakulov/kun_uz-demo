package com.company.service;

import com.company.dto.ProfileDTO;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import com.company.exp.BadRequestException;
import com.company.exp.ItemNotFoundException;
import com.company.repository.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {
    @Autowired
    private ProfileRepository profileRepository;

    public ProfileDTO create(ProfileDTO dto, String role) {
        Optional<ProfileEntity> optional = profileRepository.findByEmail(dto.getEmail());
        if (optional.isPresent()) {
            throw new BadRequestException("User already exists");
        }

        ProfileEntity entity = new ProfileEntity();
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setEmail(dto.getEmail());
        entity.setPassword(dto.getPassword());

        entity.setStatus(ProfileStatus.ACTIVE);
        entity.setVisible(true);

        if (role.equals("USER")) {
            entity.setRole(ProfileRole.USER);
        } else if (role.equals("MODERATOR")) {
            entity.setRole(ProfileRole.MODERATOR);
        } else {
            throw new BadRequestException("This role not found");
        }

        profileRepository.save(entity);
        dto.setId(entity.getId());
        return dto;
    }

    public void update(Integer id, ProfileDTO dto) {
        ProfileEntity entity = get(id);
        entity.setName(dto.getName());
        entity.setSurname(dto.getSurname());
        entity.setPassword(dto.getPassword());
        profileRepository.save(entity);
    }

    public ProfileEntity get(Integer id) {
        return profileRepository.findById(id).orElseThrow(() -> {
            throw new ItemNotFoundException("Profile not found");
        });
    }

    public List<ProfileDTO> list(String role) {
        ProfileRole who = null;
        if (role.equals(ProfileRole.USER.name())) {
            who = ProfileRole.USER;
        } else if (role.equals(ProfileRole.MODERATOR.name())) {
            who = ProfileRole.MODERATOR;
        } else {
            throw new BadRequestException("This role not found");
        }
        Iterable<ProfileEntity> all = profileRepository.userList(ProfileStatus.ACTIVE, who);
        List<ProfileDTO> dtoList = new LinkedList<>();
        for (ProfileEntity entity : all) {
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

    public void delete(Integer id) {
        Optional<ProfileEntity> optional = profileRepository.checkDeleted(ProfileStatus.ACTIVE, id);
        if (optional.isEmpty()) {
            throw new BadRequestException("This user not found or already deleted!");
        }
        profileRepository.changeStatus(ProfileStatus.NOT_ACTIVE, id);
    }
}
