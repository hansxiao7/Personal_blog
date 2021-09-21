package com.hansxiao7.blog.web;

import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.Tag;
import com.hansxiao7.blog.service.BlogService;
import com.hansxiao7.blog.service.TagService;
import com.hansxiao7.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TagService tagService;

    @GetMapping("/tags/{id}")
    public String types(@PathVariable Long id, @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("tags", tagService.listTag());
        Page<Blog> blogPage = blogService.findBlogByTag(id, pageable);
        model.addAttribute("page", blogPage);
        model.addAttribute("tagId", id);
        return "tags";
    }

}
