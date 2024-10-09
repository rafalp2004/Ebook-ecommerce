package com.ebookeria.ecommerce.service.ebook;

import com.ebookeria.ecommerce.dto.ebook.EbookCreationDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookUpdateDTO;
import com.ebookeria.ecommerce.entity.*;
import com.ebookeria.ecommerce.exception.ResourceNotFoundException;
import com.ebookeria.ecommerce.repository.AuthorRepository;
import com.ebookeria.ecommerce.repository.CategoryRepository;
import com.ebookeria.ecommerce.repository.EbookRepository;
import com.ebookeria.ecommerce.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class EbookServiceImpl implements EbookService {

    private static final Logger log = LoggerFactory.getLogger(EbookServiceImpl.class);
    private final EbookRepository ebookRepository;
    private final CategoryRepository categoryRepository;
    private final AuthorRepository authorRepository;
    private final UserRepository userRepository;

    public EbookServiceImpl(EbookRepository ebookRepository, CategoryRepository categoryRepository, AuthorRepository authorRepository, UserRepository userRepository) {
        this.ebookRepository = ebookRepository;
        this.categoryRepository = categoryRepository;
        this.authorRepository = authorRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<EbookDTO> findAll() {
        return ebookRepository.findAll().stream().map(this::mapToDTO).toList();
    }

    @Override
    public EbookDTO findById(int id) {
        Ebook ebook = ebookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ebook with id: " + id + " not found"));
        return mapToDTO(ebook);
    }

    @Override
    public EbookDTO save(EbookCreationDTO ebookCreationDTO) {
        Ebook ebook = new Ebook();
        ebook.setTitle(ebookCreationDTO.title());
        ebook.setDescription(ebookCreationDTO.description());
        ebook.setPublishedYear(ebookCreationDTO.publishedYear());
        ebook.setPrice(ebookCreationDTO.price());
        ebook.setDownloadUrl(ebookCreationDTO.downloadUrl());

        //TODO add category by id in dto - not by name.
        Category category = categoryRepository.findByName(ebookCreationDTO.category());

        List<Author> authors = ebookCreationDTO.authorsId()
                .stream()
                .map(id -> {
                    Author author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author with id: " + id + " not found. Please add author first to author's base"));
                    author.addEbook(ebook);
                    return author;
                })
                .toList();

        //TODO AFTER SPRING SECURITY THIS USER SHOULD BE GET FROM SESSION
        int id = 1;
        User user = userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User with id: " + id + " not found"));

        List<Image> images = ebookCreationDTO.imageUrls().
                stream().
                map(url -> {
                    Image image = new Image();
                    image.setUrl(url);
                    image.setEbook(ebook);
                    return image;
                })
                .toList();


        ebook.setCategory(category);
        ebook.setAuthors(authors);
        ebook.setUser(user);
        ebook.setImages(images);

        ebookRepository.save(ebook);
        return mapToDTO(ebook);
    }


    @Override
    public void update(EbookUpdateDTO ebookUpdateDTO) {

    }

    @Override
    public void deleteById(int id) {
        //TODO Add exceptions to this one and above methods
        ebookRepository.deleteById(id);
    }

    private EbookDTO mapToDTO(Ebook s) {
        return new EbookDTO(
                s.getId(),
                s.getTitle(),
                s.getDescription(),
                s.getPublishedYear(),
                s.getCategory().getName(),
                s.getPrice(),
                s.getAuthors().stream().map(Author::getFullName).toList(),
                s.getImages().stream().map(Image::getUrl).toList()
        );
    }
}

