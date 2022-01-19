package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(true)
public class RoleRepositoryTest {
    @Autowired
    private RoleRepository repo;

    @Test
    public void testCreateFirstRole(){
        Role roleAdmin = new Role("Admin", "manage everything");
        Role savedRole = repo.save(roleAdmin);
        assertThat(savedRole.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateRestRoles(){
        Role roleSalesperson = new Role("Salesperson", "manage product price, customers, shipping, reports");
        Role roleEditor = new Role("Editor", "manage categories, brands, products, articles");
        Role roleShipper = new Role("Shipper", "View products, view orders, update orders");
        Role roleAssistant = new Role("Assistant", "manage questions and reviews");

        repo.saveAll(List.of(roleSalesperson, roleEditor,roleShipper,roleAssistant));
    }
}
