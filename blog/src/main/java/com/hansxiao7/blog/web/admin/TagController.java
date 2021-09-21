package com.hansxiao7.blog.web.admin;

import com.hansxiao7.blog.po.Tag;
import com.hansxiao7.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/tags")
    public String tags(@PageableDefault(size  = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, Model model){
        Page<Tag> page = tagService.listTag(pageable);
        model.addAttribute("page", page);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String tagInput(){
        return "admin/tag-input";
    }

    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tag-input";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id){
        tagService.deleteTag(id);
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags")
    public String addTag(Tag tag, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null){
            attributes.addFlashAttribute("message", "Add Fail");
            attributes.addFlashAttribute("messageType", false);
        } else {
            Tag t = tagService.saveTag(tag);
            attributes.addFlashAttribute("message", "Add Successfully");
            attributes.addFlashAttribute("messageType", true);
        }
        return "redirect:/admin/tags";
    }

    @PostMapping("/tags/{id}")
    public String editTag(Tag tag, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag1 = tagService.getTagByName(tag.getName());

        if (tag1 != null){
            attributes.addFlashAttribute("message", "Edit Fail");
            attributes.addFlashAttribute("messageType", false);
        } else {
            tagService.updateTag(id, tag);
            attributes.addFlashAttribute("message", "Edit Success");
            attributes.addFlashAttribute("messageType", true);
        }
        return "redirect:/admin/tags";
    }


}
