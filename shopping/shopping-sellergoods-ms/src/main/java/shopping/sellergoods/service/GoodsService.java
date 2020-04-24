package shopping.sellergoods.service;

import com.lt.pojo.ResultPage;
import com.lt.pojo.TbGoods;
import com.lt.pojo.TbItem;
import com.lt.pojogroup.Goods;

import java.util.List;
import java.util.Map;

public interface GoodsService {
    void addGoods(Goods goods);
    void setItemList(Goods goods);
    void deleteGoods(Long[] ids);
    void updateGoods(Goods goods);
    List<Goods> findAllGoods();
    /**
     * 根据ID获取实体
     * @param id
     * @return
     */
    public Goods findOne(Long id);

    public ResultPage findPage(TbGoods goods, int pageNum, int pageSize);

    public void updateStatus(Long[] ids,String status);

    ResultPage findAllGoodsByPage(int pageNum, int pageSize);
    public List<TbItem> findItemListByGoodsIdListAndStatus(Long[] ids, String status);
}
