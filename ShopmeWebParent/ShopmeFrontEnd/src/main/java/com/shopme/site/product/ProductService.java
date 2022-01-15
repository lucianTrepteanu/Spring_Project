package com.shopme.site.product;

import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 10;
    @Autowired
    private ProductRepository repo;

    public Page<Product> listByCategory(int pageNum, Integer categoryId) {
        String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
        Pageable pageable = PageRequest.of(pageNum - 1, PRODUCTS_PER_PAGE);

        return repo.listByCategory(categoryId, categoryIdMatch, pageable);
    }

    public Product getProduct(Integer id) throws Exception {
        try {
            Product product = repo.findById(id).get();
            return product;
        } catch (Exception ex) {
            throw new Exception("Could not find any product with ID " + id);
        }
    }

    public Product getProduct(String alias) throws Exception {
        Product product = repo.findByAlias(alias);
        if (product == null) {
            throw new Exception("Could not find any product with alias " + alias);
        }

        return product;
    }
}