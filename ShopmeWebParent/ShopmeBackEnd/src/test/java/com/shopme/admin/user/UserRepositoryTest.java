package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class UserRepositoryTest {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole(){
        Role roleAdmin = entityManager.find(Role.class,1);
        User userLucian = new User("lucian.trepteanu@gmail.com","password","Lucian","Trepteanu");
        userLucian.addRole(roleAdmin);

        User savedUser = repo.save(userLucian);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles(){
        User userNarcis = new User("narci6ix@gmail.com","password","Narcis","Trepteanu");
        Role roleEditor = new Role(3);
        Role roleAssistant = new Role(5);
        userNarcis.addRole(roleEditor);
        userNarcis.addRole(roleAssistant);

        User savedUser = repo.save(userNarcis);
        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers(){
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById(){
        User user = repo.findById(1).get();
        System.out.println(user);
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdateUserDetails(){
        User user = repo.findById(1).get();
        user.setEnabled(true);
        user.setEmail("lucian.trepteanu@gmail.com");

        repo.save(user);
    }

    @Test
    public void testUpdateUserRoles(){
        User userNarcis = repo.findById(2).get();
        Role roleEditor = new Role(3);
        Role roleSalesperson = new Role(2);

        userNarcis.getRoles().remove(roleEditor);
        userNarcis.getRoles().add(roleSalesperson);

        repo.save(userNarcis);
    }

    @Test
    public void testDeleteUser(){
        Integer userId = 2;
        repo.deleteById(userId);
    }

    @Test
    public void testGetUserByEmail(){
        String email = "narci6ix@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById(){
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser(){
        Integer id = 1;
        repo.updateEnabledStatus(id, false);
    }
    @Test
    public void testEnableUser(){
        Integer id = 2;
        repo.updateEnabledStatus(id, true);
    }
}
