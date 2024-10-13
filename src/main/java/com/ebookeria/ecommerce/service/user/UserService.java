package com.ebookeria.ecommerce.service.user;

import com.ebookeria.ecommerce.dto.user.LoginUserDTO;
import com.ebookeria.ecommerce.dto.user.UserCreateDTO;
import com.ebookeria.ecommerce.dto.user.UserDTO;
import com.ebookeria.ecommerce.dto.user.UserUpdateDTO;
import com.ebookeria.ecommerce.entity.User;

import java.util.List;


public interface UserService {

    List<UserDTO> findAll();

    UserDTO findById(int id);

    UserDTO save(UserCreateDTO userCreateDTO);

   void update(UserUpdateDTO userUpdateDTO);

    void deleteById(int id);


    String verify(LoginUserDTO userDTO);

    User getCurrentUser();
    boolean isCurrentUserAdmin();
}
