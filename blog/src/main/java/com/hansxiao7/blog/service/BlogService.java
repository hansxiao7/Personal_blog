package com.hansxiao7.blog.service;

import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.Tag;
import com.hansxiao7.blog.po.Type;
import com.hansxiao7.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Map;


public interface BlogService{

    Blog getBlog(Long id);
    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);
    Page<Blog> listBlog(Pageable pageable);
    List<Blog> listBlog();
    Blog saveBlog(Blog blog);
    Blog updateBlog(Long id, Blog blog);
    void deleteBlog(Long id);
    List<Blog> listRecommendBlogTop(Integer size);
    Page<Blog> listBlog(Pageable pageable, String query);

    Blog getAndConvert(Long id);
    void updateViews(Long id);
    Page<Blog> listBlogByType(Pageable pageable, Long typeId);
    Page<Blog> findBlogByTag(Long tagId, Pageable pageable);

    Map<String, List<Blog>> archiveBlog();

    List<String> listFlags();
}
