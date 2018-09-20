package cn.hgxsp.hegangxsp.entity.jpaRepository;


import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * DESC：店铺jpa处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:57
 */
@Repository
public interface ShopRepository extends JpaRepository<Shop, String> , JpaSpecificationExecutor<Shop> {

    /*
    *DESC:查询该店铺名称的数量
    *@author hou.linan
    *@date:  2018/8/25 11:41
    *@param:  [shopName]
    *@return:  java.lang.Long
    */
    Long countByShopName(String shopName);


    /*
    *DESC:通过店铺名称查询店铺名称
    *@author hou.linan
    *@date:  2018/8/25 11:42
    *@param:  [shopName]
    *@return:  cn.hgxsp.hgxsp.entity.Shop
    */
    Shop findShopByShopName(String shopName);


    /*
    *DESC: 查询店铺ID通过店铺名称
    *@author hou.linan
    *@date:  2018/8/25 11:48
    *@param:  [shopName]
    *@return:  java.lang.String
    */
    String findIdByShopName(String shopName) ;

    /**
    *DESC: 通过用户id查询用户拥有多少店铺
    *@author hou.linan
    *@date:  2018/8/29 13:51
    *@param:  [userId]
    *@return:  java.lang.Long
    */
    Long countByShopBossId(String userId) ;


    List<Shop> findShopByShopBossId(String userId);

}
