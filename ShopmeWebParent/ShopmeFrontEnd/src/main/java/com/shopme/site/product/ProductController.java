package com.shopme.site.product;

import com.shopme.common.entity.Category;
import com.shopme.common.entity.Customer;
import com.shopme.common.entity.Product;
import com.shopme.site.ControllerHelper;
import com.shopme.site.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ControllerHelper controllerHelper;

    @GetMapping("/c/{category_alias}")
    public String viewCategoryFirstPage(@PathVariable("category_alias") String alias,
                                        Model model) throws Exception{
        return viewCategoryByPage(alias, 1, model);
    }

    @GetMapping("/c/{category_alias}/page/{pageNum}")
    public String viewCategoryByPage(@PathVariable("category_alias") String alias,
                                     @PathVariable("pageNum") int pageNum,
                                     Model model) throws Exception{
        try {
            Category category = categoryService.getCategory(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(category);

            Page<Product> pageProducts = productService.listByCategory(pageNum, category.getId());
            List<Product> listProducts = pageProducts.getContent();

            long startCount = (pageNum - 1) * ProductService.PRODUCTS_PER_PAGE + 1;
            long endCount = startCount + ProductService.PRODUCTS_PER_PAGE - 1;
            if (endCount > pageProducts.getTotalElements()) {
                endCount = pageProducts.getTotalElements();
            }


            model.addAttribute("currentPage", pageNum);
            model.addAttribute("totalPages", pageProducts.getTotalPages());
            model.addAttribute("startCount", startCount);
            model.addAttribute("endCount", endCount);
            model.addAttribute("totalItems", pageProducts.getTotalElements());
            model.addAttribute("pageTitle", category.getName());
            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("listProducts", listProducts);
            model.addAttribute("category", category);

            return "product/products_by_category";
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new Exception("Cannot find category with alias " + alias);
        }
    }

    @GetMapping("/p/{product_alias}")
    public String viewProductDetail(@PathVariable("product_alias") String alias, Model model,
                                    HttpServletRequest request) throws Exception{

        try {
            Product product = productService.getProduct(alias);
            List<Category> listCategoryParents = categoryService.getCategoryParents(product.getCategory());

            Customer customer = controllerHelper.getAuthenticatedCustomer(request);

            model.addAttribute("listCategoryParents", listCategoryParents);
            model.addAttribute("product", product);
            model.addAttribute("pageTitle", product.getName());

            return "product/product_detail";
        } catch (Exception e) {
            throw new Exception("Product not found!");
        }
    }
}
