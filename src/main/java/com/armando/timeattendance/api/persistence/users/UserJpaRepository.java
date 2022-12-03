package com.armando.timeattendance.api.persistence.users;

import com.armando.timeattendance.api.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**

 */
@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    @Query("SELECT u FROM UserEntity u WHERE lower(trim(u.username)) =  lower(trim(:username))")
    Optional<UserEntity> findByUsername(@Param("username") String username);
    Optional<UserEntity> findByRecoveryToken(String recoveryToken);

    @Modifying
    @Query(nativeQuery = true, value = "update users set active = true, verified = true, recovery_token = null  where recovery_token = ?1")
    void activateAccount(@Param("token") String token);

    @Query(nativeQuery = true, value = "select exists(select recovery_token from users where recovery_token = ?1)")
    boolean tokenExist(@Param("token") String token);

    boolean existsUserEntityByUsername(String username);

    @Modifying
    @Query(nativeQuery = true, value = "update users set registered_count = COALESCE(registered_count,0) + ?2 where id = ?1")
    void increaseRegisteredCount(@Param("userId") Long id, @Param("value") int i);

    List<UserEntity> findAllByPasswordIsNull();
}
