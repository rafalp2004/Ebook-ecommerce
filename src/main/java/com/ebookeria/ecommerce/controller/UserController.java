package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.user.UserCreateDTO;
import com.ebookeria.ecommerce.dto.user.UserDTO;
import com.ebookeria.ecommerce.dto.user.UserUpdateDTO;
import com.ebookeria.ecommerce.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping(path = "/users")
    public ResponseEntity<List<UserDTO>> findUsers() {
        List<UserDTO> users = userService.findAll();
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(path = "/users/{id}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable int id) {
        UserDTO userDTO = userService.findById(id);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping(path = "/users")
    public ResponseEntity<UserCreateDTO> createUser(@Valid @RequestBody UserCreateDTO userCreateDTO) {
        UserDTO createdUser =  userService.save(userCreateDTO);
        return new ResponseEntity<>(userCreateDTO, HttpStatus.CREATED);

    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<Void> deleteUserById(@PathVariable int id) {
        userService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);

    }

    @PutMapping(path = "/users")
    public ResponseEntity<UserUpdateDTO> updateUser(@Valid @RequestBody UserUpdateDTO userDTO) {
         userService.update(userDTO);
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

}