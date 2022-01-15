package com.shopme.admin.product;

import com.shopme.common.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProductRestController {
    @Autowired
    private ProductService service;

    @PostMapping("/products/check_unique")
    public String checkUnique(Integer id, String name){
        return service.checkUnique(id, name);
    }

    /*@GetMapping("/products/get/{id}")
    public ProductDTO getProductInfo(@PathVariable("id") Integer id) throws Exception{
        Product product = service.get(id);
        return new ProductDTO(product.getName(),product.)
    }*/
}
