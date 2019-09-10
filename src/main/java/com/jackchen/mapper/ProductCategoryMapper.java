package com.jackchen.mapper;

import com.jackchen.pojo.Product;
import com.jackchen.pojo.ProductCategory;
import com.jackchen.pojo.ProductCategoryExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface ProductCategoryMapper {
    int countByExample(ProductCategoryExample example);

    int deleteByExample(ProductCategoryExample example);

    int deleteByPrimaryKey(Long id);

    int insert(ProductCategory record);

    int insertSelective(ProductCategory record);

    List<ProductCategory> selectByExample(ProductCategoryExample example);

    ProductCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByExample(@Param("record") ProductCategory record, @Param("example") ProductCategoryExample example);

    int updateByPrimaryKeySelective(ProductCategory record);

    int updateByPrimaryKey(ProductCategory record);

    //查询一级目录
    List<Map<String,Object>> findParentMenu();

    List<Map<String,Object>> findMenuByParentId(long id);


    //找寻二级目录的商品
    List<ProductCategory> findSecondProduct(@Param("id") Long id,@Param("productName")String productName);
}