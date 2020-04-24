package com.lt.service;

import com.lt.pojo.TbSpecification;
import com.lt.pojo.TbSpecificationOption;
import com.lt.pojogroup.Specification;

import java.util.List;
import java.util.Map;

public interface SpecificationService {
    Specification findSpecificationById(Long id);

    List<TbSpecification> findAllSpecification(String searchCondition);

    List<TbSpecificationOption> findBySpecificationId(Long id);

    void addSpecification(Specification specification);

    void updateSpecification(Specification specification);

    void deleteById(Long []  ids);

    List<Map> selectOptionList();
}
