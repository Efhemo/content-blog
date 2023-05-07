package com.efhem.content.content;


import com.efhem.content.content.model.Content;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/content")
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
    public ResponseEntity<Content> save(@Valid @RequestBody Content content){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.save(content));
    }
    @PutMapping("/{id}")
    public ResponseEntity<Content> update(@RequestBody Content content, @PathVariable Integer id){
        return ResponseEntity.status(HttpStatus.CREATED).body(service.update(content));
    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Integer id){
        service.delete(id);
    }

}