package com.efhem.demo.controller;


import com.efhem.demo.model.Content;
import com.efhem.demo.repository.ContentCollectionRepository;
import com.efhem.demo.service.ContentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/content")
@CrossOrigin
class ContentController {

    private final ContentService service;

    ContentController(ContentService service) {
        this.service = service;
    }

    @GetMapping("")
    public List<Content> contents (){
        return service.allContent();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Content> content (@PathVariable Integer id){
        return ResponseEntity.ok(service.content(id));
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@Valid @RequestBody Content content){
        service.save(content);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    public void update(@RequestBody Content content, @PathVariable Integer id){
        if(!service.exist(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Content not found");
        }
        service.save(content);
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

}