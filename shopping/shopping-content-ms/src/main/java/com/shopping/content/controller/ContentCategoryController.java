package com.shopping.content.controller;

import com.lt.pojo.TbContentCategory;
import com.shopping.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contentCategory")
public class ContentCategoryController {
    @Autowired
    private ContentCategoryService categoryService;
    @GetMapping("/selectOptionList")
    public List<TbContentCategory> selectOptionList(){
       return categoryService.selectOptionList();
    }
}
