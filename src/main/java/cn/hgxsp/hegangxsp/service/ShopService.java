package cn.hgxsp.hegangxsp.service;


import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;

import java.util.List;

/**
 * DESC：商品店铺service接口类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/25
 * Time : 11:32
 */
public interface ShopService {


    /*
    *DESC:通过店铺名称查询店铺信息
    *@author hou.linan
    *@date:  2018/8/25 11:34
    *@param:  [shopName]
    *@return:  cn.hgxsp.hgxsp.entity.Shop
    */
   Shop findShopByShopName(String shopName);


   /*
   *DESC: 查看该店铺名称是否存在
   *@author hou.linan
   *@date:  2018/8/25 11:35
   *@param:  [shopName]
   *@return:  boolean
   */
   boolean queryShopNameIsExist(String shopName);


   /*
   *DESC:创建一个店铺
   *@author hou.linan
   *@date:  2018/8/25 11:35
   *@param:  [shop]
   *@return:  cn.hgxsp.hgxsp.entity.Shop
   */
   Shop createShop(Shop shop, User user) ;


   /*
   *DESC:修改店铺信息
   *@author hou.linan
   *@date:  2018/8/25 11:36
   *@param:  [shop]
   *@return:  cn.hgxsp.hgxsp.entity.Shop
   */
   Shop updateShop(Shop shop) ;

    /**
    *DESC: 通过店铺iD查询店铺信息
    *@author hou.linan
    *@date:  2018/8/25 11:43
    *@param:  [shopID]
    *@return:  cn.hgxsp.hgxsp.entity.Shop
    */
    Shop findShopByShopID(String shopID);


    /**
    *DESC: 通过用户查询用户所拥有店铺
    *@author hou.linan
    *@date:  2018/8/31 9:41
    *@param:  [user]
    *@return:  java.util.List<cn.hgxsp.hgxsp.entity.Shop>
    */
    List<Shop> findShopByShopBossId(String userId) ;

    /**
    *DESC: 删除一个商店
    *@author hou.linan
    *@date:  2018/9/6 17:39
    *@param:  [shop]
    *@return:  void
    */
    void deleteShop(Shop shop);

}
