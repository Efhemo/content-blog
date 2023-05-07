package com.efhem.content.content;

import com.efhem.content.content.model.Content;
import com.efhem.content.error.exception.DemoNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    private final ContentRepository repository;

    public ContentService(ContentRepository repository) {
        this.repository = repository;
    }

    public List<Content> allContent(){
        return repository.findAll().stream().map(this::toContent).toList();
    }

    public Content content(Integer id){
        return toContent(repository.findById(id).orElseThrow());
    }

    public Content save(Content content){
        var contentEntity =  repository.save(toContentEntity(content));
        return toContent(contentEntity);
    }

    public Content update(Content content){
        if(!exist(content.id())){
            throw new DemoNotFoundException("Content not found");
        }
        var contentEntity =  repository.save(toContentEntity(content));
        return toContent(contentEntity);
    }

    public void delete(Integer id){
        repository.deleteById(id);
    }

    public boolean exist(Integer id){
        return repository.existsById(id);
    }


//    @PostConstruct
//    private void init(){
//        var content = new Content(
//                1, "Jetpack Compose", "new android ui", ContentStatus.IDEA, ContentMediaType.ARTICLE,
//                LocalDateTime.now(), null, ""
//        );
//        repository.save(toContentEntity(content));
//    }

    Content toContent(ContentEntity content){
        return new Content(
                content.getId(),
                content.getTitle(),
                content.getDescription(),
                content.getStatus(),
                content.getType(),
                content.getCreatedAt(),
                content.getUpdatedAt(),
                content.getUrl()
        );
    }

    ContentEntity toContentEntity(Content content){
        var contentEntity =  new ContentEntity();
        contentEntity.setId(content.id());
        contentEntity.setTitle(content.title());
        contentEntity.setDescription(content.desc());
        contentEntity.setStatus(content.status());
        contentEntity.setType(content.type());
        contentEntity.setCreatedAt(content.createdAt());
        contentEntity.setUpdatedAt(content.updatedAt());
        contentEntity.setUrl(content.url());
        return contentEntity;
    }
}
