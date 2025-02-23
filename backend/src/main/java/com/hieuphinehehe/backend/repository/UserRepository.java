package com.hieuphinehehe.backend.repository;

import com.hieuphinehehe.backend.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
  @Query("SELECT u FROM User u " +
          "WHERE u.email <> 'admin@mail.com' " +
          "AND (LOWER(u.firstname) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
          "OR LOWER(u.lastname) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
          "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')))")
  Page<User> findByKeyword(@Param("keyword") String keyword, Pageable pageable);

  @Query("SELECT u FROM User u " +
          "WHERE u.email <> 'admin@gmail.com' ")
  Page<User> findAll(Pageable pageable);

  Optional<User> findByEmail(String email);
  boolean existsByEmail(String email);

  @Query("SELECT COUNT(u) FROM User u WHERE FUNCTION('DATE', u.date) = FUNCTION('DATE', CURRENT_DATE)")
  long countUsersCreatedToday();
}
