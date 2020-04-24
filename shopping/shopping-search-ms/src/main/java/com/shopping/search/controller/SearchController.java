package com.shopping.search.controller;

import com.shopping.search.service.ItemSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/itemsearch-ms")
public class SearchController {
    @Autowired
    private ItemSearchService itemSearchService;
    @PostMapping("/search")
    public Map searchItems(@RequestBody Map map){
       return itemSearchService.search(map);
    }
}
