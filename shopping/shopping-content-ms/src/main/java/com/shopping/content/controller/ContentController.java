package com.shopping.content.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lt.pojo.RespBean;
import com.lt.pojo.ResultPage;
import com.lt.pojo.TbContent;
import com.shopping.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/content")
public class ContentController {
    @Autowired
    private ContentService contentService;

    @PostMapping("/addContent")
    public RespBean addContent(@RequestBody TbContent content){
        try{
            contentService.addContent(content);
            return RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    @PostMapping("/updateContent")
    public RespBean updateContent(@RequestBody TbContent content){
        try{
            contentService.updateContent(content);
            return RespBean.ok("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("更新失败");
        }

    }
    @DeleteMapping("/deleteContent")
    public RespBean deleteContent(Long [] ids){
        try{
            System.out.println("数组的长度"+ids.length);
            System.out.println("数组的"+ids.toString());
            contentService.deleteContent(ids);
            return RespBean.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @GetMapping("/findAllContent")
    public ResultPage findAllContent(@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum,int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<TbContent> contentPage = (Page<TbContent>) contentService.findAllContent();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(contentPage.getResult());
        resultPage.setTotal(contentPage.getTotal());
        return resultPage;
    }

    @GetMapping("/findById/{id}")
    public TbContent findById(@PathVariable(value = "id") Long id){
        return contentService.findById(id);
    }
    @GetMapping("/findByCategoryId/{categoryId}")
    public List<TbContent> findByCategoryId(@PathVariable(value = "categoryId") Long categoryId){
        return contentService.findByCategoryId(categoryId);
    }


}
