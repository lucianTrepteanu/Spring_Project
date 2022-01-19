package com.shopme.admin.brand;

import com.shopme.common.entity.Brand;
import com.shopme.common.entity.Category;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
public class BrandServiceTest {
    @Mock
    private BrandRepository repo;
    @InjectMocks
    private BrandService service;

    @Test
    @DisplayName("Brand is Created successfully")
    void createBrand(){
        Category laptops = new Category(6);
        Brand toshiba = new Brand("Toshiba");
        toshiba.getCategories().add(laptops);

        Brand savedBrand = service.save(toshiba);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Get all Brands")
    void testGetBrands(){
        List<Brand> listBrands = new ArrayList<Brand>();

    }
}
