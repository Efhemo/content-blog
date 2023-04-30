package com.efhem.content.repository;

import com.efhem.content.model.Content;
import com.efhem.content.model.ContentMediaType;
import com.efhem.content.model.ContentStatus;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Repository
public class ContentCollectionRepository {

    private final List<Content> contentList = new ArrayList<>();


    public List<Content> allContent(){
        return contentList;
    }

    public Content content(Integer id){
        return contentList.stream()
                .filter(content -> content.id().equals(id))
                .findFirst().orElseThrow();
    }

    public void save(Content content){
        contentList.removeIf(c -> c.id().equals(content.id()));
        contentList.add(content);
    }

    public void delete(Integer id){
        contentList.removeIf(c -> c.id().equals(id));
    }

    public boolean exist(Integer id){
        return contentList.stream().anyMatch(content -> content.id().equals(id));
    }

    @PostConstruct
    private void init(){
        var content = new Content(
                1, "Jetpack Compose", "new android ui", ContentStatus.IDEA, ContentMediaType.ARTICLE,
                LocalDateTime.now(), null, ""
        );
        contentList.add(content);
    }
}