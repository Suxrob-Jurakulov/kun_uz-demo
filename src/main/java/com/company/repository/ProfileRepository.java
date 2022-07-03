package com.company.repository;

import com.company.entity.AttachEntity;
import com.company.entity.ProfileEntity;
import com.company.enums.ProfileRole;
import com.company.enums.ProfileStatus;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

public interface ProfileRepository extends CrudRepository<ProfileEntity, Integer> {

    Optional<ProfileEntity> findByEmail(String email);

    Optional<ProfileEntity> findByEmailAndPassword(String email, String password);

    Optional<ProfileEntity> findByIdAndVisible(Integer id, Boolean visible);

    @Query("from ProfileEntity where status = ?1 and role = ?2")
    List<ProfileEntity> userList(ProfileStatus status, ProfileRole role);

    @Query("from ProfileEntity  where status = ?1 and id = ?2")
    Optional<ProfileEntity> checkDeleted(ProfileStatus status, Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = ?1 where id = ?2")
    void changeStatus(ProfileStatus status, Integer id);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set status = ?2 where phone = ?1")
    void updateStatusByPhone(String phone, ProfileStatus active);

    @Transactional
    @Modifying
    @Query("update ProfileEntity set attach = ?2 where id = ?1")
    void attachSet(Integer id, AttachEntity attach);


}
