package com.example.server.service.imp;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.server.model.Blog;
import com.example.server.model.Res;
import com.example.server.repository.BlogRepositoty;
import com.example.server.service.BlogService;

@Service
public class BlogServiceImp implements BlogService {

    public BlogRepositoty blogRepositoty;

    public BlogServiceImp(BlogRepositoty blogRepositoty) {
        this.blogRepositoty = blogRepositoty;
    }

    @Override
    public Res getBlogs() {
        List<Blog> blogs = blogRepositoty.findAll();
        Res res = new Res(false, null, null);
        res.setStatus(true);
        res.setData(blogs);
        res.setMessage(Optional.of("Blog Fetch successfully"));
        return res;
    }

    @Override
    public Res addBlogs(Blog blogData) {
        Blog newAdded = blogRepositoty.save(blogData);
        Res res = new Res(false, null, null);
        res.setStatus(true);
        res.setData(newAdded);
        res.setMessage(Optional.of("Blog Added successfully"));
        return res;
    }

    @Override
    public Res deleteBlog(Long id) {
        blogRepositoty.deleteById(id);
        Res res = new Res(false, null, null);
        res.setStatus(true);
        res.setData(id);
        res.setMessage(Optional.of("Blog Delete successfully"));
        return res;
    }

    @Override
    public Res updateBlog(Long id, Blog blogData) {
        Res res = new Res(false, null, null);

        Optional<Blog> blogOptional = blogRepositoty.findById(id);

        if (blogOptional.isEmpty()) {
            res.setErrors(Optional.of("Blog not found with id: " + id));
            return res;
        }

        Blog existingBlog = blogOptional.get();

        // ✅ Update fields you want (don’t overwrite ID!)
        existingBlog.setTitle(blogData.getTitle());
        existingBlog.setCategory(blogData.getCategory());
        existingBlog.setIsPublished(blogData.getIsPublished());
        existingBlog.setTags(blogData.getTags());
        existingBlog.setImageUrl(blogData.getImageUrl());
        existingBlog.setContent(blogData.getContent());
        // existingBlog.setContent(blogData.getContent());
        // existingBlog.setAuthor(blogData.getAuthor());
        // add other fields as needed

        // ✅ Save updated blog
        Blog updatedBlog = blogRepositoty.save(existingBlog);

        res.setStatus(true);
        res.setMessage(Optional.of("Blog updated successfully"));
        res.setData(updatedBlog);

        return res;
    }

}
