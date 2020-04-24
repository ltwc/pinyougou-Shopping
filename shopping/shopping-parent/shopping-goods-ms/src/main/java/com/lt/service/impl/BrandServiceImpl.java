package com.lt.service.impl;

import com.lt.mapper.TbBrandMapper;
import com.lt.pojo.TbBrand;
import com.lt.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BrandServiceImpl implements BrandService {
    @Autowired
    private TbBrandMapper brandMapper;
    @Override
    public List<TbBrand> findAllBrand() {
        return brandMapper.selectByExample(null);
    }

    @Override
    public TbBrand findBrandById(Long id) {
        return brandMapper.selectByPrimaryKey(id);
    }

    @Override
    public int addBrand(TbBrand brand) {
        return brandMapper.insertSelective(brand);
    }

    @Override
    public int updateBrand(TbBrand brand) {
        return brandMapper.updateByPrimaryKeySelective(brand);
    }

    @Override
    public void deleteBrand(Long[] ids) {
        if (ids.length > 0 && ids != null ){
            for (Long id : ids) {
                brandMapper.deleteByPrimaryKey(id);
            }
        }

    }


    @Override
    public List<Map> selectOptionList() {
        return brandMapper.selectOptionList();
    }
}
