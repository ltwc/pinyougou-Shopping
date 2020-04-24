package com.shopping.content.service;

import com.lt.pojo.TbContent;

import java.util.List;

public interface ContentService {
    void addContent(TbContent content);

    void deleteContent(Long[] ids);

    void updateContent(TbContent content);

    List<TbContent> findAllContent();

    TbContent findById(Long id);

    List<TbContent> findByCategoryId(Long categoryId);

}
