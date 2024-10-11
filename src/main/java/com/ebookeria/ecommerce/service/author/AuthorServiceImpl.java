package com.ebookeria.ecommerce.service.author;

import com.ebookeria.ecommerce.dto.author.AuthorCreateDTO;
import com.ebookeria.ecommerce.dto.author.AuthorDTO;
import com.ebookeria.ecommerce.dto.author.AuthorUpdateDTO;
import com.ebookeria.ecommerce.entity.Author;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorServiceImpl(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Override
    public List<AuthorDTO> findAll() {
        return authorRepository.findAll().stream().map((s)->mapToDTO(s)).toList();
    }


    @Override
    public AuthorDTO findById(int id) {
        Author author = authorRepository.findById(id).orElseThrow(()->new ResourceNotFoundException("Author with id: " + id + " not found"));
        return mapToDTO(author);
    }

    @Override
    public AuthorDTO save(AuthorCreateDTO authorCreateDTO) {
        Author author = new Author();
        author.setFirstName(authorCreateDTO.firstName());
        author.setLastName(authorCreateDTO.lastName());
        authorRepository.save(author);
        return mapToDTO(author);
    }

    @Override
    public void update(AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorRepository.findById(authorUpdateDTO.id()).orElseThrow(()->new ResourceNotFoundException("Author with id: " + authorUpdateDTO.id() + " not found"));
        //TODO Update fields only if they are different

        author.setFirstName(authorUpdateDTO.firstName());
        author.setLastName(authorUpdateDTO.lastName());
        authorRepository.save(author);

    }

    @Override
    public void deleteById(int id) {
        authorRepository.deleteById(id);

    }

    private AuthorDTO mapToDTO(Author s) {
        return new AuthorDTO(s.getId(), s.getFirstName(), s.getLastName());
    }
}
