package com.shopme.admin.product;

import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
}
