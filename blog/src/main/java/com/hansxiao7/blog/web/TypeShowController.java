package com.hansxiao7.blog.web;

import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.service.BlogService;
import com.hansxiao7.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TypeShowController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @GetMapping("/types/{id}")
    public String types(@PathVariable Long id, @PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page", blogService.listBlogByType(pageable, id));
        model.addAttribute("types", typeService.listType());
        model.addAttribute("typeId", id);
        return "categories";
    }
}
