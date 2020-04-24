package test;

import com.lt.SolrApp;
import com.lt.mapper.TbItemMapper;
import com.lt.pojo.TbItem;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SolrApp.class)
@Transactional
public class Demo {
    @Autowired
    private SolrTemplate solrTemplate;
    @Autowired
    private TbItemMapper itemMapper;
    @Test
    public void testRemoveAll(){
        Query query = new SimpleQuery("*:*");
        solrTemplate.delete("collection1",query);
        solrTemplate.commit("collection1");
        System.out.println("清除所有成功");
    }

    @Test
    public void testAdd(){
        TbItem item = new TbItem();
        item.setId(10L);
        item.setTitle("康师傅牛肉面");
        item.setPrice(5D);
        item.setImage("http://localhost/8080/xsa.jnp");

        solrTemplate.saveBean("collection1",item);
        solrTemplate.commit("collection1");

        System.out.println("添加数据完成");

    }

    @Test
    public void testAddCollection(){
        List<TbItem> list = itemMapper.selectByExample(null);
        solrTemplate.saveBeans("collection1",list);
        solrTemplate.commit("collection1");

        System.out.println("添加数据完成");

    }

    @Test
    public void testQuery(){
        Query query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_title").endsWith("手机");
        query.setRows(1000);
        query.addCriteria(criteria);

        ScoredPage<TbItem> collection1 = solrTemplate.query("collection1", query, TbItem.class);

        List<TbItem> tbItemList = collection1.getContent();
        for (TbItem item : tbItemList) {
            System.out.println(item.getNum()+"***"+item.getBrand()+item.getTitle());
        }

    }
}
