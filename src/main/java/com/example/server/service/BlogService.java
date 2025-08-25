package com.example.server.service;

import com.example.server.model.Blog;
import com.example.server.model.Res;

public interface BlogService {

    Res getBlogs();

    Res addBlogs(Blog blogData);

    Res deleteBlog(Long id);

    Res updateBlog(Long id, Blog blogData);

}
