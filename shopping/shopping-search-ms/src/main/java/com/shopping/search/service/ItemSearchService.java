package com.shopping.search.service;

import java.util.List;
import java.util.Map;

public interface ItemSearchService {
    Map search(Map searchMap);
    void importList(List list);
}
