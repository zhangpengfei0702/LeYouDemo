package com.leyou.item.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.common.vo.PageResult;
import com.leyou.item.mapper.BrandMapper;
import com.leyou.item.pojo.Brand;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    private BrandMapper brandMapper;


    /**
     * 分页条件查询
     *
     * @param page
     * @param rows
     * @param sortBy
     * @param desc
     * @param key
     * @return
     */
    public PageResult<Brand> queryBrandByPage(Integer page, Integer rows, String sortBy, boolean desc, String key) {
        PageHelper.startPage(page, rows);
        Example example = new Example(Brand.class);
        if (StringUtils.isNoneBlank(key)) {
            example.createCriteria().orLike("name", "%" + key + "%").orEqualTo("letter", key.toUpperCase());
        }
        if (StringUtils.isNotBlank(sortBy)) {
            String orderbyclause = sortBy + (desc ? "desc" : "ASC");
            example.setOrderByClause(orderbyclause);
        }
        List<Brand> brands = brandMapper.selectByExample(example);

        if (CollectionUtils.isEmpty(brands)) {
            throw new LyException(ExceptionEnum.BRAND_NOT_FOND);
        }


        PageInfo<Brand> info = new PageInfo<>(brands);

        return new PageResult<>(info.getTotal(), brands);
    }


    /**
     * 新增品牌
     *
     * @param brand
     * @param cids
     */
    @SuppressWarnings("all")
    @Transactional    //添加事务
    public void saveBrand(Brand brand, List<Long> cids) {
        brand.setId(null);
        int count = brandMapper.insert(brand);
        if (count != 1) {
            throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
        }

//        新增中间表
        for (Long cid : cids) {
            count = brandMapper.insertCategoryBrand(cid, brand.getId());
            if (count != 1) {
                throw new LyException(ExceptionEnum.BRAND_SAVE_ERROR);
            }
        }


    }
}
