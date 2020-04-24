package com.lt.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lt.pojo.RespBean;
import com.lt.pojo.ResultPage;
import com.lt.pojo.TbTypeTemplate;
import com.lt.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/typeTemplate")
public class TypeTemplateController {
    @Autowired
    private TypeTemplateService typeTemplateService;

    @PostMapping("/addTypeTemplate")
    public RespBean addTypeTemplate(@RequestBody TbTypeTemplate typeTemplate){
        try{
            System.out.println("模板属性"+typeTemplate.getCustomAttributeItems());
            typeTemplateService.addTypeTemplate(typeTemplate);
            return RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }

    @DeleteMapping("/deleteTypeTemplate")
    public RespBean deleteTypeTemplate(Long[] ids){
        System.out.println("删除的id"+ids);
        try {
            typeTemplateService.deleteTypeTemplate(ids);
            return RespBean.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("删除失败");
        }
    }

    @PostMapping("/updateTypeTemplate")
    public RespBean updateTypeTemplate(@RequestBody TbTypeTemplate typeTemplate) {
        System.out.println("进入数据更新了"+typeTemplate.getId());
        try{
            typeTemplateService.updateTypeTemplate(typeTemplate);
            return RespBean.ok("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("更新失败");
        }

    }
    //查询所有模板，有条件按照条件查询
    @GetMapping("/findTemplateByPageAndName")
    public ResultPage findTemplateByPageAndName(Integer pageNum,Integer pageSize,
                             @RequestParam(value = "tempName",defaultValue = "",required = false) String tempName) {
        PageHelper.startPage(pageNum,pageSize);
        Page<TbTypeTemplate> typeTemplatePage = (Page<TbTypeTemplate>) typeTemplateService.findTemplateByPageAndName(tempName);
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(typeTemplatePage);
        resultPage.setTotal(typeTemplatePage.getTotal());
        return resultPage;
    }
       //根据id查询模板
    @GetMapping("/findById/{id}")
    public TbTypeTemplate findById(@PathVariable(value = "id") Long id) {
        return  typeTemplateService.findById(id);
    }

    //根据id查询模板的规格集合
    @GetMapping("/findSpecList/{id}")
    public List<Map> findSpecList(@PathVariable(value = "id") Long id) {
        return  typeTemplateService.findSpecList(id);
    }


}
