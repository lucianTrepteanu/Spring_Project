package com.shopme.admin.category;

import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repo;

    @Test
    public void testCreateRootCategory(){
        Category category = new Category("Electronics");
        Category savedCategory = repo.save(category);

        assertThat(savedCategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateSubCategory(){
        Category parent = new Category(2);
        Category cameras = new Category("Cameras", parent);
        Category smartphones = new Category("Smartphones", parent);

        repo.saveAll(List.of(cameras, smartphones));

        //assertThat(savedSubcategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreteSubCategoryLast(){
        Category parent = new Category(5);
        Category subcategory = new Category("Memory", parent);
        Category savedSubcategory = repo.save(subcategory);

        assertThat(savedSubcategory.getId()).isGreaterThan(0);
    }

    @Test
    public void testGetCategory(){
        Category category = repo.findById(1).get();
        System.out.println(category.getName());

        Set<Category> children = category.getChildren();
        for(Category subcategory : children){
            System.out.println(subcategory.getName());
        }

        assertThat(children.size()).isGreaterThan(0);
    }

    @Test
    public void testPrintHierarchicalCategories(){
        Iterable<Category> categories = repo.findAll();

        for(Category category : categories){
            if(category.getParent() == null){
                System.out.println(category.getName());

                Set<Category> children = category.getChildren();
                for(Category subcategory : children){
                    System.out.println("--" + subcategory.getName());
                    printChildren(subcategory, 1);
                }
            }
        }
    }

    @Test
    private void printChildren(Category parent, int subLevel){
        int newSubLevel = subLevel + 1;
        Set<Category> children = parent.getChildren();
        for(Category subcategory : children){
            for(int i=0; i < newSubLevel; i++){
                System.out.println("--");
            }
            System.out.println(subcategory.getName());
            printChildren(subcategory, newSubLevel);
        }
    }
}
