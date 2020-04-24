package shopping.sellergoods.controller;

import com.lt.pojo.TbItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.sellergoods.service.ItemCatService;

import java.util.List;

@RestController
@RequestMapping("/itemCat")
public class ItemCatController {
    @Autowired
    private ItemCatService itemCatService;
    @GetMapping("/findByParentId/{parentId}")
    public List<TbItemCat> findByParentId(@PathVariable("parentId")Long parentId){
        return itemCatService.findByParentId(parentId);
    }

    @GetMapping("/findOne/{id}")
    public TbItemCat findOne(@PathVariable("id")Long id){
        return itemCatService.findOne(id);
    }
}
