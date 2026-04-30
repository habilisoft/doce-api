package com.habilisoft.doce.api.persistence.users;

import com.habilisoft.doce.api.persistence.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

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

    @Query("SELECT u FROM UserEntity u " +
            "WHERE lower(trim(u.username)) <> 'superadmin' " +
            "AND (:term IS NULL OR trim(:term) = '' " +
            "OR lower(trim(u.username)) LIKE lower(concat('%', trim(:term), '%')) " +
            "OR lower(trim(u.name)) LIKE lower(concat('%', trim(:term), '%')))")
    Page<UserEntity> search(@Param("term") String term, Pageable pageable);

    long countByUsernameNotIgnoreCase(String username);

    long countByUsernameNotIgnoreCaseAndLastLoginIsNotNull(String username);

    List<UserEntity> findTop5ByUsernameNotIgnoreCaseAndLastLoginIsNotNullOrderByLastLoginDesc(String username);
}
