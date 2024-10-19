package com.ebookeria.ecommerce.controller;

import com.ebookeria.ecommerce.dto.ebook.EbookCreationDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookDTO;
import com.ebookeria.ecommerce.dto.ebook.EbookResponse;
import com.ebookeria.ecommerce.dto.ebook.EbookUpdateDTO;
import com.ebookeria.ecommerce.service.ebook.EbookService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class EbookController {
    private final EbookService ebookService;

    public EbookController(EbookService ebookService) {
        this.ebookService = ebookService;
    }

    @GetMapping(path="/ebooks")
    public ResponseEntity<EbookResponse> findEbooks(
            @RequestParam(value="pageNo", defaultValue = "0", required = false) int pageNo,
            @RequestParam(value="pageSize", defaultValue = "10", required = false) int pageSize,
              @RequestParam(value="sortField", defaultValue = "title", required = false) String sortField,
            @RequestParam(value="sortDirection", defaultValue = "asc", required = false) String sortDirection
            ){
        EbookResponse ebookResponse = ebookService.findAll(pageNo,pageSize, sortField, sortDirection);
        if(ebookResponse.content().isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(ebookResponse, HttpStatus.OK);
    }

    @GetMapping(path = "/ebooks/{id}")
    public ResponseEntity<EbookDTO> findEbookById(@PathVariable int id){
        EbookDTO ebookDTO = ebookService.findById(id);
        return new ResponseEntity<>(ebookDTO, HttpStatus.OK);
    }

    @PostMapping(path ="/ebooks")
    public ResponseEntity<Void> saveEbook(@Valid @RequestBody EbookCreationDTO ebookCreationDTO){
        ebookService.save(ebookCreationDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    @PutMapping(path="/ebooks")
    public ResponseEntity<Void>  updateEbook(@Valid @RequestBody EbookUpdateDTO ebookUpdateDTO){
        ebookService.update(ebookUpdateDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping(path="/ebooks/{id}")
    public ResponseEntity<Void> deleteEbook(@PathVariable int id){
        ebookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



}
