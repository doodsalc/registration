package com.test.registration.repository;

import com.test.registration.entity.UserEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface UserRepository extends CrudRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    @Modifying
    @Query("update UserEntity u set u.deleted = true where u.username = ?1")
    void softDeleteByUsername(String username);

    List<UserEntity> findByDeletedIsFalse();

}
