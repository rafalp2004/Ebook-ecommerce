package com.ebookeria.ecommerce.service.user;

import com.ebookeria.ecommerce.dto.user.*;
import com.ebookeria.ecommerce.entity.User;


public interface UserService {

    UserResponse findAll(int pageNo, int pageSize, String sortField,String sortDirection);

    UserDTO findById(int id);

    UserDTO save(UserCreateDTO userCreateDTO);

    void update(UserUpdateDTO userUpdateDTO);

    void deleteById(int id);

    void changePassword(UserChangePasswordDTO userChangePasswordDTO);

    String verify(LoginUserDTO userDTO);

    User getCurrentUser();

    boolean isCurrentUserAdmin();
}
