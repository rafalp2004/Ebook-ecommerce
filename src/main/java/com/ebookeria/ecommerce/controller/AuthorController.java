package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.author.AuthorCreateDTO;
import com.ebookeria.ecommerce.dto.author.AuthorDTO;
import com.ebookeria.ecommerce.dto.author.AuthorUpdateDTO;

import com.ebookeria.ecommerce.service.author.AuthorService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }


    @GetMapping(path = "/authors")
    public ResponseEntity<List<AuthorDTO>> findAuthors(){
        List<AuthorDTO> authors = authorService.findAll();
        if(authors.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(authors, HttpStatus.OK);
    }
    @GetMapping(path="/authors/{id}")
    public ResponseEntity<AuthorDTO> findAuthorById(@PathVariable int id){
        AuthorDTO authorDTO = authorService.findById(id);
        return new ResponseEntity<>(authorDTO, HttpStatus.OK);
    }
    @PostMapping(path = "/authors")
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorCreateDTO authorCreateDTO) {
        AuthorDTO createdAuthor =  authorService.save(authorCreateDTO);
        return new ResponseEntity<>(createdAuthor, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/authors/{id}")
    public ResponseEntity<Void> deleteAuthorById(@PathVariable int id) {
        authorService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/authors")
    public ResponseEntity<AuthorUpdateDTO> updateUser(@Valid @RequestBody AuthorUpdateDTO authorUpdateDTO) {
        authorService.update(authorUpdateDTO);
        return new ResponseEntity<>(authorUpdateDTO, HttpStatus.OK);
    }

}
