package com.shopping.content.service.impl;

import com.lt.mapper.TbContentMapper;
import com.lt.pojo.TbContent;
import com.lt.pojo.TbContentExample;
import com.shopping.content.service.ContentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ContentServiceImpl implements ContentService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private TbContentMapper contentMapper;

    @Override
    public void addContent(TbContent content) {
        contentMapper.insert(content);
        redisTemplate.delete(content.getCategoryId());
    }

    @Override
    public void deleteContent(Long[] ids) {
        if (ids.length > 0 ){
            for (Long id : ids) {
                TbContent content = contentMapper.selectByPrimaryKey(id);
                contentMapper.deleteByPrimaryKey(id);
                redisTemplate.delete(content.getCategoryId());
            }
        }

    }

    @Override
    public void updateContent(TbContent content) {
        contentMapper.updateByPrimaryKey(content);
        redisTemplate.delete(content.getCategoryId());
    }

    @Override
    public List<TbContent> findAllContent() {
        return contentMapper.selectByExample(null);
    }

    @Override
    public TbContent findById(Long id) {
        return contentMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<TbContent> findByCategoryId(Long categoryId) {
        List<TbContent> contentList = (List<TbContent>) redisTemplate.boundHashOps("content").get(categoryId);

        if (contentList==null){
            System.out.println("查询数据");
            TbContentExample example = new TbContentExample();
            TbContentExample.Criteria criteria = example.createCriteria().andCategoryIdEqualTo(categoryId);
            example.setOrderByClause("sort_order");
            contentList =contentMapper.selectByExample(example);
            redisTemplate.boundHashOps("content").put(categoryId,contentList);
        }else {
            System.out.println("从缓存中读取数据");
        }
        return contentList;
    }
}
