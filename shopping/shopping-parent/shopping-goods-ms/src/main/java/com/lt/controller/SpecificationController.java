package com.lt.controller;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.lt.pojo.RespBean;
import com.lt.pojo.ResultPage;
import com.lt.pojo.TbSpecification;
import com.lt.pojo.TbSpecificationExample;
import com.lt.pojogroup.Specification;
import com.lt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/specification")
public class SpecificationController {
    @Autowired
    private SpecificationService specificationService;

    //根据分页查询Specification
    @GetMapping("/findSpecificationByPage")
    public ResultPage findAllSpecification(@RequestParam(value = "pageNum",defaultValue = "1",required = false)int pageNum ,int pageSize,
                                          @RequestParam(value = "searchCondition",defaultValue = "",required = false) String searchCondition){
        PageHelper.startPage(pageNum,pageSize);
        Page<TbSpecification> pageList = (Page<TbSpecification>) specificationService.findAllSpecification(searchCondition);

        System.out.println("返回的集合大小"+pageList.size());
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(pageList.getResult());
        resultPage.setTotal(pageList.getTotal());
        return resultPage;
    }

//    @GetMapping()
//    public
    @GetMapping("/findSpecificationById/{id}")
    public Specification findSpecificationById(@PathVariable(name = "id") Long id){
        return  specificationService.findSpecificationById(id);
    }

    //添加规格
    @PostMapping("/addSpecification")
    public RespBean addSpecification(@RequestBody Specification specification){
        try {
            specificationService.addSpecification(specification);
            return RespBean.ok("添加成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.ok("添加失败");
        }
    }
    //更新规格
    @PostMapping("/updateSpecification")
    public RespBean updateSpecification(@RequestBody Specification specification){
        try {
            specificationService.updateSpecification(specification);
            return RespBean.ok("更新成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.ok("更新失败");

        }
    }

    @DeleteMapping("/deleteSpecification")
    public RespBean deleteSpecification(Long[] ids){
        try{
            System.out.println("数组的长度+"+ids.length);
            specificationService.deleteById(ids);
            return RespBean.ok("删除成功");
        }catch (Exception e){
            e.printStackTrace();
            return RespBean.ok("删除失败");
        }
    }

    @GetMapping("/selectOptionList")
    public List<Map> selectOptionList(){
        return specificationService.selectOptionList();
    }
}
