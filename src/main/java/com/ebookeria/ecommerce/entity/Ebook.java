package com.ebookeria.ecommerce.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ebook")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"category", "user","images","authors"})
public class Ebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "published_year")
    private Date publishedYear;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToMany
    @JoinTable(
            name="ebooks_authors",
            joinColumns = @JoinColumn(name="ebook_id"),
            inverseJoinColumns = @JoinColumn(name="author_id")
    )
    private List<Author> authors;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "ebook")
    private List<Image> images;


    public void addImage(Image image) {
        if (images == null) {
            images = new ArrayList<>();
        }
        images.add(image);
    }

    public void addAuthor(Author author) {
        if (authors == null) {
            authors = new ArrayList<>();
        }
        authors.add(author);
    }

}
