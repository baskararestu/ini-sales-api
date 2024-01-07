package com.enigma.inisalesapi.repository;

import com.enigma.inisalesapi.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category,String> {
    Optional<Category> findByName(String name);

    @Modifying
    @Query(value = "INSERT INTO m_category (id, name, created_at) " +
            "VALUES (gen_random_uuid(), :name, :createdAt", nativeQuery = true)
    Category insertCategoryNative(@Param("name")String name,
                              @Param("createdAt") LocalDateTime createdAt);
}
