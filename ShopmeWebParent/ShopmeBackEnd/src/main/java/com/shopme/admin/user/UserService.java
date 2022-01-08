package com.shopme.admin.user;

import com.shopme.common.entity.Role;
import com.shopme.common.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@Transactional
public class UserService {
    public static final int USERS_PER_PAGE=4;
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private RoleRepository roleRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getByEmail(String email){
        return userRepo.getUserByEmail(email);
    }

    public List<User> listAll(){
        return (List<User>) userRepo.findAll();
    }

    public List<Role> listRoles(){
        return (List<Role>) roleRepo.findAll();
    }

    public Page<User> listByPage(int pageNum, String sortField, String sortDir){
        Sort sort = Sort.by(sortField);
        sort = sortDir.equals("asc") ? sort.ascending() : sort.descending();
        Pageable pageable = PageRequest.of(pageNum-1, USERS_PER_PAGE, sort);
        return userRepo.findAll(pageable);
    }

    public User save(User user){
        boolean isUpdatingUser = (user.getId()!=null);
        if(isUpdatingUser){
            User existingUser = userRepo.findById(user.getId()).get();
            if(user.getPassword().isEmpty()){
                user.setPassword(existingUser.getPassword());
            } else {
                encodePassword(user);
            }
        } else {
            encodePassword(user);
        }
        return userRepo.save(user);
    }

    public User updateAccount(User userInForm){
        User userInDB = userRepo.findById(userInForm.getId()).get();
        if(!userInForm.getPassword().isEmpty()){
            userInDB.setPassword(userInForm.getPassword());
            encodePassword(userInDB);
        }
        //if(userInForm.getPhotos() != null){
          //  userInDB.setPhotos(userInForm.getPhotos());
        //}

        userInDB.setFirstName(userInForm.getFirstName());
        userInDB.setLastName(userInForm.getLastName());

        return userRepo.save(userInDB);
    }

    private void encodePassword(User user){
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
    }

    public boolean isEmailUnique(Integer id, String email){
        User userByEmail = userRepo.getUserByEmail(email);
        if(userByEmail==null) return true;
        boolean isCreatingNew = (id==null);
        if(isCreatingNew){
            if(userByEmail!=null) return false;
        } else {
            if(userByEmail.getId() != id){
                return false;
            }
        }
        return true;
    }

    public User get(Integer id) throws UserNotFoundException {
        try{
            return userRepo.findById(id).get();
        } catch (NoSuchElementException ex){
            throw new UserNotFoundException("Could not find user with id " +id);
        }
    }

    public void delete(Integer id) throws UserNotFoundException {
        Long countById = userRepo.countById(id);
        if(countById == null || countById == 0){
            throw new UserNotFoundException("Could not find user with id "+id);
        }
        userRepo.deleteById(id);
    }

    public void updateUserEnabledStatus(Integer id, boolean enabled){
        userRepo.updateEnabledStatus(id, enabled);
    }
}
