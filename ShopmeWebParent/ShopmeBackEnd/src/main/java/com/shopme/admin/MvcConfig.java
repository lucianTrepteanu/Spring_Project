package com.shopme.admin;

import com.shopme.admin.paging.PagingAndSortingArgumentResolver;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Configuration
public class MvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry){
        String dirName = "user-photos";
        Path userPhotoDir = Paths.get(dirName);
        String userPhotosPath = userPhotoDir.toFile().getAbsolutePath();
        registry.addResourceHandler("/"+dirName + "/**").addResourceLocations("file:/"+userPhotosPath + "/");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers){
        resolvers.add(new PagingAndSortingArgumentResolver());
    }
}
