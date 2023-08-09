package com.store.repository;

import com.store.entity.Role;
import com.store.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u WHERE u.username = :username")
    public User getUserByUsername(@Param("username") String username);

    @Modifying
    @Query("UPDATE User u SET u.roles = ?2 WHERE u.username = ?1")
    public void updateAuthenticationType(String username, Role authType);
}
