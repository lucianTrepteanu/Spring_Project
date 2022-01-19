package com.shopme.admin.product;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import com.shopme.common.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class ProductRepositoryTest {
    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct(){
        Brand brand = entityManager.find(Brand.class, 2);
        Category category = entityManager.find(Category.class, 4);

        Product product = new Product();
        product.setName("MacBook Air 14");
        product.setAlias("macbook_air_14");
        product.setShortDescription("A notebook made by Apple");
        product.setFullDescription("Some overpriced laptop made by Apple");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(1000);
        product.setCost(800);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProducts(){
        Iterable<Product> iterableProducts = repo.findAll();
        iterableProducts.forEach(System.out::println);
    }

    @Test
    public void testGetProduct(){
        Integer id = 2;
        Product product = repo.findById(id).get();
        System.out.println(product);

        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct(){
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(490);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);
        assertThat(updatedProduct.getPrice()).isEqualTo(490);
    }
}
