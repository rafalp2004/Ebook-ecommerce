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
import jakarta.persistence.criteria.CriteriaBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashSet;
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
                    ebook.addAuthor(author);
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
        Ebook ebook = ebookRepository.findById(ebookUpdateDTO.id())
                .orElseThrow(() -> new ResourceNotFoundException("Ebook with id: " + ebookUpdateDTO.id() + " not found"));


        // Update fields only if they are present in DTO and different
        if (ebookUpdateDTO.title() != null && !ebook.getTitle().equals(ebookUpdateDTO.title())) {
            ebook.setTitle(ebookUpdateDTO.title());
        }

        if (ebookUpdateDTO.description() != null && !ebook.getDescription().equals(ebookUpdateDTO.description())) {
            ebook.setDescription(ebookUpdateDTO.description());
        }

        if (ebookUpdateDTO.publishedYear() != null && !ebook.getPublishedYear().equals(ebookUpdateDTO.publishedYear())) {
            ebook.setPublishedYear(ebookUpdateDTO.publishedYear());
        }
        if (ebookUpdateDTO.price() != 0.0 && Double.compare(ebook.getPrice(), ebookUpdateDTO.price()) != 0) {
            ebook.setPrice(ebookUpdateDTO.price());
        }

        if (ebookUpdateDTO.downloadUrl() != null && !ebook.getDownloadUrl().equals(ebookUpdateDTO.downloadUrl())) {
            ebook.setDownloadUrl(ebookUpdateDTO.downloadUrl());
        }

        if (ebookUpdateDTO.category() != null) {
            Category category = categoryRepository.findByName(ebookUpdateDTO.category());
            if (category == null) {
                throw new ResourceNotFoundException("Category with name: " + ebookUpdateDTO.category() + " not found");
            }
            if (!ebook.getCategory().equals(category)) {
                ebook.setCategory(category);
            }
        }

        if (ebookUpdateDTO.authorsId() != null) {
            List<Integer> currentAuthorIds = ebook.getAuthors().stream().map(Author::getId).toList();
            List<Integer> newAuthorIds = ebookUpdateDTO.authorsId();

            newAuthorIds.stream()
                    .filter(id -> !currentAuthorIds.contains(id))
                    .forEach(id -> {
                        Author author = authorRepository.findById(id)
                                .orElseThrow(() -> new ResourceNotFoundException("Author with id: " + id + " not found"));
                        ebook.addAuthor(author);
                    });

            ebook.getAuthors().removeIf(author -> {
                boolean toRemove = !newAuthorIds.contains(author.getId());
                if (toRemove) {
                    author.getEbooks().remove(ebook);
                }
                return toRemove;
            });
        }

        if (ebookUpdateDTO.imageUrls() != null) {
            List<String> newImageUrls = ebookUpdateDTO.imageUrls();
            ebook.getImages().removeIf(image -> !newImageUrls.contains(image.getUrl()));

            newImageUrls.stream()
                    .filter(url -> ebook.getImages().stream().noneMatch(image -> image.getUrl().equals(url)))
                    .forEach(url -> {
                        Image newImage = new Image();
                        newImage.setUrl(url);
                        ebook.addImage(newImage);
                    });
        }
        ebookRepository.save(ebook);

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

