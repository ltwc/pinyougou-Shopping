package com.lt.service;

import com.lt.pojo.TbBrand;

import java.util.List;
import java.util.Map;

public interface BrandService {
    List<TbBrand> findAllBrand();

    TbBrand findBrandById(Long id);

    int addBrand(TbBrand brand);

    int updateBrand(TbBrand brand);

    void deleteBrand(Long[] ids);

    List<Map> selectOptionList();

}
