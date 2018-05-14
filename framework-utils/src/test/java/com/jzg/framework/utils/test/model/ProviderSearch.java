package com.jzg.framework.utils.test.model;

import java.util.List;


public class ProviderSearch {

    private Integer secondCategorySysno;

    private Integer provinceSysno;

    private Integer citySysno;

    private Integer districtSysno;

    private List<SpecificationSearch> specificationSearchs;

    private Integer orderBy;


    public Integer getSecondCategorySysno() {
        return secondCategorySysno;
    }

    public void setSecondCategorySysno(Integer secondCategorySysno) {
        this.secondCategorySysno = secondCategorySysno;
    }

    public Integer getProvinceSysno() {
        return provinceSysno;
    }

    public void setProvinceSysno(Integer provinceSysno) {
        this.provinceSysno = provinceSysno;
    }

    public Integer getCitySysno() {
        return citySysno;
    }

    public void setCitySysno(Integer citySysno) {
        this.citySysno = citySysno;
    }

    public Integer getDistrictSysno() {
        return districtSysno;
    }

    public void setDistrictSysno(Integer districtSysno) {
        this.districtSysno = districtSysno;
    }

    public List<SpecificationSearch> getSpecificationSearchs() {
        return specificationSearchs;
    }

    public void setSpecificationSearchs(List<SpecificationSearch> specificationSearchs) {
        this.specificationSearchs = specificationSearchs;
    }

    public Integer getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(Integer orderBy) {
        this.orderBy = orderBy;
    }
}