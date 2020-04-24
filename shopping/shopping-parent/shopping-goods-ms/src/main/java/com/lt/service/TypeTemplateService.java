package com.lt.service;

import com.lt.pojo.TbTypeTemplate;

import java.util.List;
import java.util.Map;

public interface TypeTemplateService {
    void addTypeTemplate(TbTypeTemplate typeTemplate);

    void deleteTypeTemplate(Long [] ids);

    void updateTypeTemplate(TbTypeTemplate typeTemplate);

    List<TbTypeTemplate> findTemplateByPageAndName(String tempName);

    TbTypeTemplate findById(Long id);
    public List<Map> findSpecList(Long id);
}
