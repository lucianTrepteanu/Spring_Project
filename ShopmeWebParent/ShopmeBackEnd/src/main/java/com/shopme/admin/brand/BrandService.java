package com.shopme.admin.brand;

import com.shopme.admin.category.CategoryPageInfo;
import com.shopme.admin.paging.PagingAndSortingHelper;
import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BrandService {
    public static final int BRANDS_PER_PAGE = 4;

    @Autowired
    private BrandRepository repo;

    public List<Brand> listAll() {
        return (List<Brand>) repo.findAll();
    }

    public void listByPage(int pageNum, PagingAndSortingHelper helper) {
        helper.listEntities(pageNum, BRANDS_PER_PAGE, repo);
    }

    public Brand save(Brand brand) {
        return repo.save(brand);
    }

    public Brand get(Integer id) throws Exception {
        try {
            return repo.findById(id).get();
        } catch (Exception ex) {
            throw new Exception("Could not find any brand with ID " + id);
        }
    }

    public void delete(Integer id) throws Exception {
        Long countById = repo.countById(id);

        if (countById == null || countById == 0) {
            throw new Exception("Could not find any brand with ID " + id);
        }

        repo.deleteById(id);
    }

    public String checkUnique(Integer id, String name) {
        boolean isCreatingNew = (id == null || id == 0);
        Brand brandByName = repo.findByName(name);

        if (isCreatingNew) {
            if (brandByName != null) return "Duplicate";
        } else {
            if (brandByName != null && brandByName.getId() != id) {
                return "Duplicate";
            }
        }

        return "OK";
    }
}
