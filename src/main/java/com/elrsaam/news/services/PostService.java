package com.elrsaam.news.services;

import com.elrsaam.news.dto.PostDto;
import com.elrsaam.news.exception.PostNotFoundException;
import com.elrsaam.news.model.Post;
import com.elrsaam.news.repository.PostRepository;
import com.elrsaam.news.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import javax.naming.NoPermissionException;

import static java.util.stream.Collectors.toList;

import java.time.Instant;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private AuthService authService;
    

    @Transactional
    public List<Post> showAllPosts() {
        List<Post> posts = postRepository.findAll();
        return posts.stream().collect(toList());
    }

    @Transactional
    public void createPost(PostDto postDto) {
        Post post = mapFromDtoToPost(postDto);
        postRepository.save(post);
    }
    
    @Transactional
    public void deleteanyPost(Long id) {
    	 Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
         postRepository.delete(post);
    }
    
    @Transactional
    public void deleteMyPost(Long id) throws NoPermissionException {
    	 Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
         String name = SecurityContextHolder.getContext().getAuthentication().getName();
    	 
         if(!name.equals(post.getUsername())){
             throw new NoPermissionException();
         }
         postRepository.delete(post);
    }
    
    @Transactional
    public void UpdatePost(Long id) {
    	 Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
         postRepository.save(post);
    }

    @Transactional
    public Post readSinglePost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(() -> new PostNotFoundException("For id " + id));
        return post;
    }


    private PostDto mapFromPostToDto(Post post) {
        PostDto postDto = new PostDto();
        postDto.setId(post.getId());
        postDto.setTitle(post.getTitle());
        postDto.setContent(post.getContent());
        postDto.setUsername(post.getUsername());
        return postDto;
    }

    private Post mapFromDtoToPost(PostDto postDto) {
        Post post = new Post();
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        User loggedInUser = authService.getCurrentUser().orElseThrow(() -> new IllegalArgumentException("User Not Found"));
        post.setCreatedOn(Instant.now());
        post.setUsername(loggedInUser.getUsername());
        post.setUpdatedOn(Instant.now());
        return post;
    }
}
