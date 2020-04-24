package com.lt.service.impl;

import com.alibaba.fastjson.JSON;
import com.lt.mapper.TbSpecificationOptionMapper;
import com.lt.mapper.TbTypeTemplateMapper;
import com.lt.pojo.TbSpecificationOption;
import com.lt.pojo.TbSpecificationOptionExample;
import com.lt.pojo.TbTypeTemplate;
import com.lt.pojo.TbTypeTemplateExample;
import com.lt.service.TypeTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TypeTemplateServiceImpl implements TypeTemplateService {
    @Autowired
    private TbTypeTemplateMapper templateMapper;
    @Autowired
    private TbSpecificationOptionMapper specificationOptionMapper;
    @Override
    public void addTypeTemplate(TbTypeTemplate typeTemplate) {
        templateMapper.insert(typeTemplate);
    }

    @Override
    public void deleteTypeTemplate(Long[] ids) {
        if (ids.length > 0){
            for (Long id : ids) {
                templateMapper.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public void updateTypeTemplate(TbTypeTemplate typeTemplate) {
        templateMapper.updateByPrimaryKey(typeTemplate);
    }

    @Override
    public List<TbTypeTemplate> findTemplateByPageAndName(String tempName) {
        if (!tempName.equals("undefined") &&tempName!=null && !tempName.equals("")){
            TbTypeTemplateExample example = new TbTypeTemplateExample();
            example.createCriteria().andNameLike("%"+tempName+"%");
            return templateMapper.selectByExample(example);
        }
        return templateMapper.selectByExample(null);
    }

    @Override
    public TbTypeTemplate findById(Long id) {
        return templateMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Map> findSpecList(Long id) {
        //根据ID查询到模板对象
        TbTypeTemplate typeTemplate = templateMapper.selectByPrimaryKey(id);
        // 获得规格的数据spec_ids
        String specIds = typeTemplate.getSpecIds();// [{"id":27,"text":"网络"},{"id":32,"text":"机身内存"}]
        // 将specIds的字符串转成JSON的List<Map>
        List<Map> list = JSON.parseArray(specIds, Map.class);
        // 获得每条记录:
        for (Map map : list) {
            // 根据规格的ID 查询规格选项的数据:
            // 设置查询条件:
            TbSpecificationOptionExample example = new TbSpecificationOptionExample();
            TbSpecificationOptionExample.Criteria criteria = example.createCriteria();
            criteria.andSpecIdEqualTo(new Long((Integer)map.get("id")));

            List<TbSpecificationOption> specOptionList = specificationOptionMapper.selectByExample(example);

            map.put("options", specOptionList);//{"id":27,"text":"网络",options:[{id：xxx,optionName:移动2G}]}
        }
        return list;
    }
}
