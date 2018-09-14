package cn.hgxsp.hegangxsp.entity.jpaRepository;

import cn.hgxsp.hegangxsp.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * DESC：商品jpa处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:57
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, String>, JpaSpecificationExecutor<Product> {



    /**
    *DESC:查询某店铺内所有的商品
    *@author hou.linan
    *@date:  2018/9/2 22:11
    *@param:  [pageable, shopId]
    *@return:  org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
    */
    Page<Product> findByShopId(Pageable pageable, String shopId) ;
    // Specification<Product> specification

    /**
    *DESC: 查询某店铺的所有商品  for 分页
    *@author hou.linan
    *@date:  2018/9/6 17:12
    *@param:  [shopId, specification, pageable]
    *@return:  org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
    */
    Page<Product> findByShopId(String shopId, Specification specification, Pageable pageable) ;

    /**
    *DESC:查询某店铺的所有商品 所有商品
    *@author hou.linan
    *@date:  2018/9/6 17:12
    *@param:  [shopId]
    *@return:  java.util.List<cn.hgxsp.hgxsp.entity.Product>
    */
    List<Product> findByShopId(String shopId);
}
