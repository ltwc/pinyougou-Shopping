package com.online.shopping.service.impl;

import com.lt.mapper.TbGoodsDescMapper;
import com.lt.mapper.TbGoodsMapper;
import com.lt.mapper.TbItemCatMapper;
import com.lt.mapper.TbItemMapper;
import com.lt.pojo.*;
import com.online.shopping.service.ItemPageService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfig;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import java.io.FileWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ItemPageServiceImpl implements ItemPageService {
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;

    private String pagedir = "F:\\item\\";

    @Autowired
    private TbGoodsMapper tbGoodsMapper;
    @Autowired
    private TbGoodsDescMapper tbGoodsDescMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Override
    public boolean genItemHtml(Long goodsId) {
        Configuration configuration = freeMarkerConfigurer.getConfiguration();
        try{
            Template template = configuration.getTemplate("item.ftl");
            //创建数据模型
            Map dataModel = new HashMap<>();
            //查询商品主表数据
            TbGoods tbGoods = tbGoodsMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goods",tbGoods);
            //获取商品扩展数据
            TbGoodsDesc tbGoodsDesc = tbGoodsDescMapper.selectByPrimaryKey(goodsId);
            dataModel.put("goodsDesc",tbGoodsDesc);
            //读取商品分类数据
            if (tbGoods.getCategory1Id()!=null){
                String itemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
                dataModel.put("itemCat1",itemCat1==null?"":itemCat1);
            }
            if (tbGoods.getCategory2Id()!=null){
                String itemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
                dataModel.put("itemCat2",itemCat2==null?"":itemCat2);
            }
            if (tbGoods.getCategory3Id()!=null){
                String itemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
                dataModel.put("itemCat3",itemCat3==null?"":itemCat3);
            }

            System.out.println("价格"+tbGoods.getPrice());

            //读取sku的列表
            TbItemExample tbItemExample = new TbItemExample();
            TbItemExample.Criteria criteria = tbItemExample.createCriteria();
            criteria.andGoodsIdEqualTo(goodsId);
            criteria.andStatusEqualTo("1");
            tbItemExample.setOrderByClause("is_default desc");//按照是否降序排序，目的是返回的结果第一条默认SKU

            List<TbItem> itemList = itemMapper.selectByExample(tbItemExample);
            dataModel.put("itemList",itemList);

            FileWriter writer = new FileWriter(pagedir + goodsId + ".html");
            template.process(dataModel,writer);
            writer.close();
            return true;
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteItemHtml(Long[] goodsIds) {
        return false;
    }
}
