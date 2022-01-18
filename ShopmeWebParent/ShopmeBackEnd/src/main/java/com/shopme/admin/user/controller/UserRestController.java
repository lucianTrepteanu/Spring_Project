package com.shopme.admin.user.controller;

import com.shopme.admin.response.ResponseMessage;
import com.shopme.admin.user.UserNotFoundException;
import com.shopme.admin.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@RestController
public class UserRestController {
    @Autowired
    private UserService userService;

    @PostMapping("/users/check_email")
    public String checkDuplicateEmail(@Param("id") Integer id, @Param("email") String email){
        return userService.isEmailUnique(id, email) ? "OK" : "Duplicated";
    }
}
