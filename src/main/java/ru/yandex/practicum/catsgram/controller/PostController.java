package ru.yandex.practicum.catsgram.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.catsgram.enumeration.SortOrder;
import ru.yandex.practicum.catsgram.model.Post;
import ru.yandex.practicum.catsgram.service.PostService;
import jakarta.validation.constraints.Positive;

import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired  //не обязательна на единственном конструкторе
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping
    public Collection<Post> findAll(@RequestParam(value = "from" , defaultValue = "1") int from,
                                    @RequestParam(value = "size" , defaultValue = "10") @Positive int size,
                                    @RequestParam(value = "sort" , defaultValue = "desc") String sort) {
        return postService.findAll(from, size, SortOrder.from(sort));
    }

    @GetMapping("/{id}")
    public Optional<Post> findById(@PathVariable("id") long id) {
        return postService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Post create(@RequestBody Post post) {
        return postService.create(post);
    }

    @PutMapping
    public Post update(@RequestBody Post newPost) {
        return postService.update(newPost);
    }
}