package com.shopme.admin.product;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class ProductService {
    public static final int PRODUCTS_PER_PAGE = 5;

    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper, Integer categoryId) {
        Pageable pageable = helper.createPageable(PRODUCTS_PER_PAGE, pageNum);
        Page<Product> page = null;

        if (categoryId != null && categoryId > 0) {
            String categoryIdMatch = "-" + String.valueOf(categoryId) + "-";
            page = repo.findAllInCategory(categoryId, categoryIdMatch, pageable);
        } else {
            page = repo.findAll(pageable);
        }

        helper.updateModelAttributes(pageNum, page);
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setCreatedTime(new Date());
        }

        if (product.getAlias() == null || product.getAlias().isEmpty()) {
            String defaultAlias = product.getName().replaceAll(" ", "-");
            product.setAlias(defaultAlias);
        } else {
            product.setAlias(product.getAlias().replaceAll(" ", "-"));
        }

        product.setUpdatedTime(new Date());

        Product updatedProduct = repo.save(product);

        return updatedProduct;
    }

    public void saveProductPrice(Product productInForm) {
        Product productInDB = repo.findById(productInForm.getId()).get();
        productInDB.setCost(productInForm.getCost());
        productInDB.setPrice(productInForm.getPrice());
        productInDB.setDiscountPercent(productInForm.getDiscountPercent());

        repo.save(productInDB);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Product productByName = repo.findByName(name);

        if (isCreatingNew) {
            if (productByName != null) return "Duplicate";
        } else {
            if (productByName != null && productByName.getId() != id) {
                return "Duplicate";
            }
        }

        return "OK";
    }

    public void updateProductEnabledStatus(Integer id, boolean enabled) {
        repo.updateEnabledStatus(id, enabled);
    }

    public void delete(Integer id) throws Exception {
        Long countById = repo.countById(id);

        if (countById == null || countById == 0) {
            throw new Exception("Could not find any product with ID " + id);
        }

        repo.deleteById(id);
    }

    public Product get(Integer id) throws Exception {
        try {
            return repo.findById(id).get();
        } catch (Exception ex) {
            throw new Exception("Could not find any product with ID " + id);
        }
    }
}
