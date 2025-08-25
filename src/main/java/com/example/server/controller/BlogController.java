package com.example.server.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.server.model.Blog;
import com.example.server.model.Res;
import com.example.server.model.User;
import com.example.server.service.BlogService;

@RestController
@RequestMapping("/blog")
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService userService) {
        this.blogService = userService;
    }

    @GetMapping()
    Res getBlogs() {
        return blogService.getBlogs();
    }

    @PostMapping("add-blog")
    Res addBlog(@RequestBody Blog blog) {
        return blogService.addBlogs(blog);
    }

    @DeleteMapping("/delete-blog/{id}")
    public Res deleteBlog(@PathVariable Long id) {
        return blogService.deleteBlog(id);
    }

    @PutMapping("/update-blog/{id}")
    public Res updateBlog(@RequestBody Blog blog, @PathVariable Long id) {
        return blogService.updateBlog(id, blog);
    }

}
