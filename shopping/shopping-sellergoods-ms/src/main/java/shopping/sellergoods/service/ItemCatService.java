package shopping.sellergoods.service;

import com.lt.pojo.TbItemCat;

import java.util.List;
import java.util.function.LongFunction;

public interface ItemCatService {
    void addItemCat(TbItemCat itemCat);
    void deleteItemCat(Long [] ids);
    void updateItemCat(TbItemCat itemCat);
    void findAllItemCat();
    List<TbItemCat> findByParentId(Long parentId);
    TbItemCat findOne(Long id);
}
