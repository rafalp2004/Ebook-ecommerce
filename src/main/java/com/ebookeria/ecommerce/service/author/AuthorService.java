package com.ebookeria.ecommerce.service.author;

import com.ebookeria.ecommerce.dto.author.AuthorCreateDTO;
import com.ebookeria.ecommerce.dto.author.AuthorDTO;
import com.ebookeria.ecommerce.dto.author.AuthorUpdateDTO;
import com.ebookeria.ecommerce.dto.user.UserCreateDTO;
import com.ebookeria.ecommerce.dto.user.UserDTO;
import com.ebookeria.ecommerce.dto.user.UserUpdateDTO;

import java.util.List;

public interface AuthorService {
    List<AuthorDTO> findAll();

    AuthorDTO findById(int id);

    AuthorDTO save(AuthorCreateDTO authorCreateDTO);

    void update(AuthorUpdateDTO authorUpdateDTO);

    void deleteById(int id);

}
