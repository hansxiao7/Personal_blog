package com.hansxiao7.blog.web.admin;

import com.hansxiao7.blog.po.Type;
import com.hansxiao7.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/types")
    public String types(@PageableDefault(size  = 10, sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, Model model){
        Page<Type> typePage = typeService.listType(pageable);
        model.addAttribute("typePage", typePage);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String typeInput(){
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/type-input";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id){
        typeService.deleteType(id);
        return "redirect:/admin/types";
    }

    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){
            attributes.addFlashAttribute("message", "Add Fail");
            attributes.addFlashAttribute("messageType", false);
        } else {
            Type t = typeService.saveType(type);
            attributes.addFlashAttribute("message", "Add Successfully");
            attributes.addFlashAttribute("messageType", true);
        }
        return "redirect:/admin/types";
    }

    @PostMapping("/types/{id}")
    public String editType(Type type, @PathVariable Long id, RedirectAttributes attributes){
        Type type1 = typeService.getTypeByName(type.getName());

        if (type1 != null){
            attributes.addFlashAttribute("message", "Edit Fail");
            attributes.addFlashAttribute("messageType", false);
        } else {
            typeService.updateType(id, type);
            attributes.addFlashAttribute("message", "Edit Success");
            attributes.addFlashAttribute("messageType", true);
        }
        return "redirect:/admin/types";
    }

}
