package com.lt.pojogroup;

import com.lt.pojo.TbSpecification;
import com.lt.pojo.TbSpecificationOption;

import java.io.Serializable;
import java.util.List;

public class Specification implements Serializable {
    public TbSpecification getSpecification() {
        return specification;
    }

    public void setSpecification(TbSpecification specification) {
        this.specification = specification;
    }

    public List<TbSpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<TbSpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

    private TbSpecification specification;
    private List<TbSpecificationOption> specificationOptionList;
}
