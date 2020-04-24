package shopping.sellergoods.service.impl;

import com.lt.mapper.TbItemCatMapper;
import com.lt.pojo.TbItemCat;
import com.lt.pojo.TbItemCatExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shopping.sellergoods.service.ItemCatService;

import java.util.List;

@Service
public class ItemCatServiceImpl implements ItemCatService {
    @Autowired
    private TbItemCatMapper itemCatMapper;
    @Override
    public void addItemCat(TbItemCat itemCat) {

    }

    @Override
    public void deleteItemCat(Long[] ids) {

    }

    @Override
    public void updateItemCat(TbItemCat itemCat) {

    }

    @Override
    public void findAllItemCat() {

    }

    @Override
    public List<TbItemCat> findByParentId(Long parentId) {
        TbItemCatExample example = new TbItemCatExample();
        TbItemCatExample.Criteria criteria = example.createCriteria().andParentIdEqualTo(parentId);

        return itemCatMapper.selectByExample(example);
    }

    @Override
    public TbItemCat findOne(Long id) {
        return itemCatMapper.selectByPrimaryKey(id);
    }
}
