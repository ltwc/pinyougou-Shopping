package com.lt.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lt.pojo.RespBean;
import com.lt.pojo.ResultPage;
import com.lt.pojo.TbBrand;
import com.lt.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/brand")
public class BrandController {
    @Autowired
    private BrandService brandService;
    @GetMapping("/findAllBrand")
    public List<TbBrand> findAllBrand(){
        return brandService.findAllBrand();
    }
    @GetMapping("/findByPage")
    public ResultPage findByPage(@RequestParam(value = "pageNum",defaultValue = "1",required = false) int pageNum, int pageSize){
        PageHelper.startPage(pageNum,pageSize);
        Page<TbBrand> brands  = (Page<TbBrand>) brandService.findAllBrand();
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(brands);
        resultPage.setTotal(brands.getTotal());
        return resultPage;
    }

    @GetMapping("/findBrandById/{brandId}")
    public TbBrand findBrandById(@PathVariable(value = "brandId") Long brandId){
        return brandService.findBrandById(brandId);
    }
    @PostMapping("/addBrand")
    public RespBean addBrand(@RequestBody TbBrand brand){
        try{
            brandService.addBrand(brand);
            return  RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("添加失败");
        }
    }
    @PostMapping("/updateBrand")
    public RespBean updateBrand(@RequestBody TbBrand brand){
        try{
            brandService.updateBrand(brand);
            return  RespBean.ok("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.error("更新失败");
        }
    }
    @DeleteMapping("/deleteBrand")
    public RespBean deleteBrand(Long[] ids){
        try{
            brandService.deleteBrand(ids);
            return RespBean.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.ok("删除失败");
        }
    }

    @GetMapping("/selectOptionList")
    public List<Map> selectOptionList(){
       return brandService.selectOptionList();
    }

}
