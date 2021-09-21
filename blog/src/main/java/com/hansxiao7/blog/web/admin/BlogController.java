package com.hansxiao7.blog.web.admin;

import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.User;
import com.hansxiao7.blog.service.BlogService;
import com.hansxiao7.blog.service.TagService;
import com.hansxiao7.blog.service.TypeService;
import com.hansxiao7.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;


@Controller
@RequestMapping("/admin")
public class BlogController {

    private final String INPUT = "admin/post";
    private final String LIST = "/admin/blogs";
    private final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return LIST;
    }

    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model){
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        return INPUT;
    }

    @GetMapping("/blogs/{id}/input")
    public String edit(@PathVariable Long id, Model model){
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
        Blog b = blogService.getBlog(id);
        b.init();
        model.addAttribute("blog", b);
        return INPUT;
    }

    @GetMapping("/blogs/{id}/delete")
    public String edit(@PathVariable Long id, RedirectAttributes attributes){
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "Success");
        return REDIRECT_LIST;
    }


    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session){
        if (blog.getId() != null){
            Blog oldB = blogService.getBlog(blog.getId());
            blog.setCreateTime(oldB.getCreateTime());
        }

        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b = blogService.saveBlog(blog);

        if (b == null){
            attributes.addFlashAttribute("message", "Fail");
        } else{
            attributes.addFlashAttribute("message", "Success");
        }
        return REDIRECT_LIST;
    }

}
