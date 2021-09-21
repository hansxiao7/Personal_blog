package com.hansxiao7.blog.web;

import com.hansxiao7.blog.NotFoundException;
import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.Comment;
import com.hansxiao7.blog.service.BlogService;
import com.hansxiao7.blog.service.TagService;
import com.hansxiao7.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class indexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;


    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(10));
        model.addAttribute("recommendBlogs", blogService.listRecommendBlogTop(6));
        return "index";
    }



    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable, Model model, @RequestParam String query){
        model.addAttribute("page", blogService.listBlog(pageable, "%"+query+"%"));
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/tags")
    public String tag(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("tags", tagService.listTag());
        model.addAttribute("tagId", -1);
        return "tags";
    }

    @GetMapping("/types")
    public String types(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC)Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listType());
        model.addAttribute("typeId", -1);
        return "categories";
    }

    @GetMapping("/about")
    public String about(Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("flags", blogService.listFlags());
        return "about";
    }

}

