package shopping.sellergoods.service.impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lt.mapper.*;
import com.lt.pojo.*;
import com.lt.pojogroup.Goods;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopping.sellergoods.service.GoodsService;

import java.util.*;

@Service
public class GoodsServiceImpl implements GoodsService {
    @Autowired
    private TbGoodsMapper goodsMapper;
    @Autowired
    private TbGoodsDescMapper goodsDescMapper;
    @Autowired
    private TbItemMapper itemMapper;
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Autowired
    private TbBrandMapper brandMapper;
    @Autowired
    private TbSellerMapper sellerMapper;

    @Override
    public void addGoods(Goods goods) {
        goods.getGoods().setAuditStatus("0");//设置审核状态
        goodsMapper.insertSelective(goods.getGoods());//插入商品信息
        goods.getGoodsDesc().setGoodsId(goods.getGoods().getId());//设置商品详细的外键
        goodsDescMapper.insert(goods.getGoodsDesc());//插入商品详细的数据
        setItemList(goods);
    }

    @Override
    public void setItemList(Goods goods) {
        System.out.println("判断是否启用模板"+goods.getGoods().getIsEnableSpec());
        if ("1".equals(goods.getGoods().getIsEnableSpec())){
            for (TbItem item : goods.getItemList()) {
                //设置sku数据
                String title = goods.getGoods().getGoodsName();
                Map<String,String> map = JSON.parseObject(item.getSpec(), Map.class);
                for (String key : map.keySet()) {
                    title +=" "+map.get(key);
                }
                item.setTitle(title);
                setValue(goods,item);
            }
        }else {
            TbItem item = new TbItem();

            item.setTitle(goods.getGoods().getGoodsName());

            item.setPrice(goods.getGoods().getPrice());

            item.setNum(999);

            item.setStatus("0");

            item.setIsDefault("1");

            item.setSpec("{}");

            setValue(goods,item);
            itemMapper.insert(item);

        }
    }
    private void setValue(Goods goods,TbItem item){
        List<Map> imageList = JSON.parseArray(goods.getGoodsDesc().getItemImages(), Map.class);
        if (imageList.size() > 0){
            item.setImage((String) imageList.get(0).get("url"));
        }
        //保存三级分类
        item.setCategoryid(goods.getGoods().getCategory3Id());
        item.setCreateTime(new Date());
        item.setUpdateTime(new Date());
        //设置商品的id
        item.setGoodsId(goods.getGoods().getId());

        item.setSellerId(goods.getGoods().getSellerId());

        TbItemCat itemCat = itemCatMapper.selectByPrimaryKey(goods.getGoods().getCategory3Id());

        item.setCategory(itemCat.getName());

        TbBrand brand = brandMapper.selectByPrimaryKey(goods.getGoods().getBrandId());

        item.setBrand(brand.getName());

//        TbSeller seller = sellerMapper.selectByPrimaryKey(goods.getGoods().getSellerId());
//
//        item.setSeller(seller.getNickName());



    }
    @Override
    public void deleteGoods(Long[] ids) {

    }

    @Override
    public void updateGoods(Goods goods) {

    }

    @Override
    public List<Goods> findAllGoods() {

        return null;
    }

    @Override
    public Goods findOne(Long id) {
        Goods goods = new Goods();
        //知道spu
        //根据id查找到商品
        TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
        goods.setGoods(tbGoods);
        //根据id查找到商品描述
        TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(id);
        goods.setGoodsDesc(tbGoodsDesc);
        //根据商品的id找到具体的sku
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdEqualTo(id);
        List<TbItem> itemList = itemMapper.selectByExample(example);

        goods.setItemList(itemList);

        return goods;
    }

    @Override
    public ResultPage findPage(TbGoods goods, int pageNum, int pageSize) {
        return null;
    }

    @Override
    public void updateStatus(Long[] ids, String status) {
        for (Long id : ids) {
            TbGoods tbGoods = goodsMapper.selectByPrimaryKey(id);
            tbGoods.setAuditStatus(status);
            goodsMapper.updateByPrimaryKey(tbGoods);
        }
    }

    @Override
    public ResultPage findAllGoodsByPage(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        TbGoodsExample goodsExample = new TbGoodsExample();
//        TbGoodsExample.Criteria criteria = goodsExample.createCriteria().andAuditStatusEqualTo("0");
//        List<TbGoods> tbGoodsList =
        Page<TbGoods> tbGoodsPage = (Page<TbGoods>) goodsMapper.selectByExample(goodsExample);
        ArrayList<Map<String,Object>> arrayList = new ArrayList<>();
        for (TbGoods tbGoods : tbGoodsPage) {
            Map<String,Object> map = new HashMap<>();
            map.put("goods",tbGoods);
            TbGoodsDesc tbGoodsDesc = goodsDescMapper.selectByPrimaryKey(tbGoods.getId());
            map.put("goodsDesc",tbGoodsDesc);
            System.out.println("goods一级分类的id；"+tbGoods.getCategory1Id());
            if (tbGoods.getCategory1Id()!=null){
                String itemCat1 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory1Id()).getName();
                System.out.println("goods一级分类的id；"+tbGoods.getCategory1Id());
                String itemCat2 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory2Id()).getName();
                System.out.println("goods一级分类的id；"+tbGoods.getCategory1Id());
                String itemCat3 = itemCatMapper.selectByPrimaryKey(tbGoods.getCategory3Id()).getName();
                map.put("itemCat1",itemCat1);
                map.put("itemCat2",itemCat2);
                map.put("itemCat3",itemCat3);
            }
            arrayList.add(map);

        }
        ResultPage resultPage = new ResultPage();
        resultPage.setRows(arrayList);
        resultPage.setTotal(tbGoodsPage.getTotal());
        return resultPage;
    }

    @Override
    public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status) {
        TbItemExample example = new TbItemExample();
        TbItemExample.Criteria criteria = example.createCriteria();
        criteria.andGoodsIdIn(Arrays.asList(ids));
        criteria.andStatusEqualTo(status);
        List<TbItem> itemList = itemMapper.selectByExample(example);
        return itemList;
    }
}
