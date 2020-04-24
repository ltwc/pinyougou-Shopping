package com.shopping.content.service.impl;

import com.lt.mapper.TbContentCategoryMapper;
import com.lt.pojo.TbContentCategory;
import com.shopping.content.service.ContentCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentCategoryImpl implements ContentCategoryService {
    @Autowired
    private TbContentCategoryMapper categoryMapper;
    @Override
    public void addContentCategory(TbContentCategory contentCategory) {
        categoryMapper.insert(contentCategory);
    }

    @Override
    public void deleteContentCategory(Long[] ids) {
        if (ids.length>0){
            for (Long id : ids) {
                categoryMapper.deleteByPrimaryKey(id);
            }
        }
    }

    @Override
    public void updateContentCategory(TbContentCategory contentCategory) {
        categoryMapper.updateByPrimaryKey(contentCategory);
    }

    @Override
    public List<TbContentCategory> findAllContentCategory() {
        return categoryMapper.selectByExample(null);
    }

    @Override
    public TbContentCategory findById(Long id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TbContentCategory> selectOptionList() {
        return categoryMapper.selectByExample(null);
    }
}
