package com.ebookeria.ecommerce.service;

import com.ebookeria.ecommerce.dto.user.UserCreateDTO;
import com.ebookeria.ecommerce.dto.user.UserDTO;
import com.ebookeria.ecommerce.dto.user.UserUpdateDTO;
import com.ebookeria.ecommerce.entity.Role;
import com.ebookeria.ecommerce.entity.User;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.RoleRepository;
import com.ebookeria.ecommerce.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


    @Override
    public List<UserDTO> findAll() {
        return userRepository.findAll().stream().map((s)->maptoDTO(s)).toList();
    }


    @Override
    public UserDTO findById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: " + id + " not found"));
        return maptoDTO(user);
    }

    @Override
    public UserDTO save(UserCreateDTO userCreateDTO) {
        User user = new User();
        user.setFirstName(userCreateDTO.firstName());
        user.setLastName(userCreateDTO.lastName());
        user.setEmail(userCreateDTO.email());
        user.setPassword(userCreateDTO.password());
        user.setRegistrationDate(LocalDate.now());
        user.setEnabled(true);
        Role role = roleRepository.findByName("ROLE_USER");
        user.addRole(role);

        userRepository.save(user);
        return maptoDTO(user);


    }

    @Override
    public void update(UserUpdateDTO userUpdateDTO) {
        User user = userRepository.findById(userUpdateDTO.id()).orElseThrow(()->new ResourceNotFoundException("User with id: " + userUpdateDTO.id() + " not found"));
        user.setEmail(userUpdateDTO.email());
        user.setFirstName(userUpdateDTO.firstName());
        user.setLastName(userUpdateDTO.lastName());

         userRepository.save(user);
    }

    @Override
    public void deleteById(int id) {
        User user = userRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("User with id: " + id + " not found"));
        userRepository.delete(user);
    }

    //Todo add changing password
    //Todo add adding and removing books

    private UserDTO maptoDTO(User user) {
        return new UserDTO(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
