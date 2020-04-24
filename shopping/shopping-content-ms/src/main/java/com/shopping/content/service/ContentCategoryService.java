package com.shopping.content.service;

import com.lt.pojo.TbContent;
import com.lt.pojo.TbContentCategory;

import java.util.List;

public interface ContentCategoryService {
    void addContentCategory(TbContentCategory contentCategory);

    void deleteContentCategory(Long[] ids);

    void updateContentCategory(TbContentCategory contentCategory);

    List<TbContentCategory> findAllContentCategory();

    TbContentCategory findById(Long id);

    List<TbContentCategory> selectOptionList();
}
