package com.shopping.search.service.impl;

import com.lt.pojo.TbItem;
import com.shopping.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class ItemServiceImpl implements ItemSearchService {
    @Autowired
    private SolrTemplate solrTemplate;
    @Override
    public Map search(Map searchMap) {
        Map map = new HashMap<>();

        Query simpleQuery = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_keywords").is(searchMap.get("keywords"));
        simpleQuery.addCriteria(criteria);

        Page<TbItem> collection1 = solrTemplate.query("collection1", simpleQuery, TbItem.class);
        map.put("rows",collection1.getContent());
        return map;
    }

    @Override
    public void importList(List list) {
        solrTemplate.saveBeans("collection1",list);
        solrTemplate.commit("collection1");
    }
}
