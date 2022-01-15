package com.shopme.site.category;

import com.shopme.common.entity.Category;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CategoryRepository extends CrudRepository<Category, Integer> {

    @Query("SELECT c FROM Category c ORDER BY c.name ASC")
    public List<Category> findAllEnabled();

    @Query("SELECT c FROM Category c WHERE c.alias = ?1")
    public Category findByAliasEnabled(String alias);
}