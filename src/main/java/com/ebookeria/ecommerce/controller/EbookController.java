package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.ebook.EbookCreationDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookUpdateDTO;
import com.ebookeria.ecommerce.service.ebook.EbookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class EbookController {
    private final EbookService ebookService;

    public EbookController(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    @GetMapping(path="/ebooks")
    public ResponseEntity<List<EbookDTO>> findEbooks(){
        List<EbookDTO> ebookDTOs = ebookService.findAll();
        if(ebookDTOs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ebookDTOs, HttpStatus.OK);
    }

    @GetMapping(path = "/ebooks/{id}")
    public ResponseEntity<EbookDTO> findEbookById(@PathVariable int id){
        EbookDTO ebookDTO = ebookService.findById(id);
        return new ResponseEntity<>(ebookDTO, HttpStatus.OK);
    }

    @PostMapping(path ="/ebooks")
    public ResponseEntity<Void> saveEbook(@RequestBody EbookCreationDTO ebookCreationDTO){
        ebookService.save(ebookCreationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path="/ebooks")
    public ResponseEntity<Void>  updateEbook(@RequestBody EbookUpdateDTO ebookUpdateDTO){
        ebookService.update(ebookUpdateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path="/ebooks/{id}")
    public ResponseEntity<Void> deleteEbook(@PathVariable int id){
        ebookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
