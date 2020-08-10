package com.elrsaam.news.controller;

import com.elrsaam.news.dto.PostDto;
import com.elrsaam.news.model.Post;
import com.elrsaam.news.repository.PostRepository;
import com.elrsaam.news.services.PostService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/posts/")
public class PostController {

    @Autowired
    private PostService postService;
    

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @PostMapping
    public ResponseEntity createPost(@RequestBody PostDto postDto) {
        postService.createPost(postDto);
        return new ResponseEntity(HttpStatus.OK);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/all")
    public ResponseEntity<List<Post>> showAllPosts() {
        return new ResponseEntity<>(postService.showAllPosts(), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable long id) {
    	postService.deleteanyPost(id);
    }
    
    @PreAuthorize("hasRole('USER')")
    @DeleteMapping("/mypost/{id}")
    public void deleteMyPost(@PathVariable long id) {
    	postService.deleteanyPost(id);
    }
    
    @PreAuthorize("hasRole('USER')")
    @PutMapping("/{id}")
    public ResponseEntity<Object> updatePost(@RequestBody Post post, @PathVariable long id) {

    	Optional<Post> postOptional = Optional.of(postService.readSinglePost(id));

    	if (!postOptional.isPresent())
    		return ResponseEntity.notFound().build();

    	post.setId(id);
    	
    	postService.UpdatePost(id);

    	return ResponseEntity.noContent().build();
    }
    
    @PreAuthorize("hasRole('USER')")
    @PostMapping(value = "/upload/{id}")
    public void postImage(@RequestParam("file") MultipartFile file,@PathVariable Long id) throws IOException {
         System.out.println("received");
    }
    
}
