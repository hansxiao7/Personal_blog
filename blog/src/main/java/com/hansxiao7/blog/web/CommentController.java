package com.hansxiao7.blog.web;

import com.hansxiao7.blog.po.Blog;
import com.hansxiao7.blog.po.Comment;
import com.hansxiao7.blog.po.User;
import com.hansxiao7.blog.service.BlogService;
import com.hansxiao7.blog.service.CommentService;
import com.hansxiao7.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Random;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService userService;

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        blogService.updateViews(id);
        Blog b = blogService.getAndConvert(id);
        model.addAttribute("blog", b);
        model.addAttribute("comments", commentService.listCommentByBlogId(id));
        return "blog";
    }

    @PostMapping("/blog/{id}")
    public String comment(@PathVariable Long id, Comment comment, Model model){
        comment.setBlog(blogService.getBlog(id));
        Random random = new Random();
        int avatarId = random.ints(1, 14).findFirst().getAsInt();
        comment.setAvatar("/images/avatar/avatar-" + avatarId + ".png");

        User admin = userService.findByUsernameAndEmail(comment.getNickname(), comment.getEmail());
        if (admin != null){
            comment.setAdminComment(true);
        }

        commentService.saveComment(comment);
        return "redirect:/blog/" + id;
    }
}
