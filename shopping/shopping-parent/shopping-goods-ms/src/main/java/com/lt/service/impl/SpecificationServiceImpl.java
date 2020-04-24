package com.lt.service.impl;

import com.lt.mapper.TbSpecificationMapper;
import com.lt.mapper.TbSpecificationOptionMapper;
import com.lt.pojo.TbSpecification;
import com.lt.pojo.TbSpecificationExample;
import com.lt.pojo.TbSpecificationOption;
import com.lt.pojo.TbSpecificationOptionExample;
import com.lt.pojogroup.Specification;
import com.lt.service.SpecificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class SpecificationServiceImpl implements SpecificationService {
    @Autowired
    private TbSpecificationMapper specificationMapper;

    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;

    @Override
    public Specification findSpecificationById(Long id) {
        TbSpecification tbSpecification = specificationMapper.selectByPrimaryKey(id);
        TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria();
        criteria.andSpecIdEqualTo(id);
        List<TbSpecificationOption> tbSpecificationOptions = specificationOptionMapper.selectByExample(optionExample);
        Specification specification = new Specification();
        specification.setSpecification(tbSpecification);
        specification.setSpecificationOptionList(tbSpecificationOptions);
        return specification;
    }

    //查询所有规格
    @Override
    public List<TbSpecification> findAllSpecification(String searchCondition) {
        if (searchCondition!=null && !searchCondition.equals("undefined")){
            System.out.println("进去了模糊查询");
            TbSpecificationExample example = new TbSpecificationExample();
            TbSpecificationExample.Criteria criteria = example.createCriteria().andSpecNameLike("%" + searchCondition + "%");
            return specificationMapper.selectByExample(example);
        }

        return specificationMapper.selectByExample(null);
    }
    //根据规格id查询规格项
    @Override
    public List<TbSpecificationOption> findBySpecificationId(Long id) {
        return null;
    }

    //添加规格
    @Override
    public void addSpecification(Specification specification) {
        specificationMapper.insertSelective(specification.getSpecification());
        for (TbSpecificationOption option : specification.getSpecificationOptionList()) {
            option.setSpecId(specification.getSpecification().getId());
            specificationOptionMapper.insert(option);
        }
    }
    //更新规格
    @Override
    public void updateSpecification(Specification specification) {
        specificationMapper.updateByPrimaryKey(specification.getSpecification());
        TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
        TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria().andSpecIdEqualTo(specification.getSpecification().getId());
        specificationOptionMapper.deleteByExample(optionExample);
        for (TbSpecificationOption specificationOption : specification.getSpecificationOptionList()) {
            //设置规格的id
            specificationOption.setSpecId(specification.getSpecification().getId());
            //添加规格项
            specificationOptionMapper.insert(specificationOption);
        }
    }

    //循环id数组格局ID删除规格
    @Override
    public void deleteById(Long[] ids) {
       if (ids.length > 0){
           for (Long id : ids) {
               TbSpecificationOptionExample optionExample = new TbSpecificationOptionExample();
               TbSpecificationOptionExample.Criteria criteria = optionExample.createCriteria().andSpecIdEqualTo(id);
               specificationOptionMapper.deleteByExample(optionExample);
               specificationMapper.deleteByPrimaryKey(id);
           }
       }


    }

    @Override
    public List<Map> selectOptionList() {
        return specificationMapper.selectOptionList();
    }
}
