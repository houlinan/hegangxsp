package cn.hgxsp.hegangxsp.service;


import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.Shop;

import java.util.List;

/**
 * DESC：商品服务层接口
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/30
 * Time : 18:44
 */
public interface ProductService {

    /**
     * DESC: 保存一个商品
     *
     * @author hou.linan
     * @date: 2018/8/30 18:45
     * @param: [product]
     * @return: cn.hgxsp.hgxsp.entity.Product
     */
    Product save(Product product);

    /***
     *DESC: 更新一个商品
     *@author hou.linan
     *@date: 2018/8/30 18:45
     *@param: [product]
     *@return: cn.hgxsp.hgxsp.entity.Product
     */
    Product update(Product product);

    /**
     * DESC:  通过商品ID查询商品
     *
     * @author hou.linan
     * @date: 2018/8/30 18:46
     * @param: [productId]
     * @return: cn.hgxsp.hgxsp.entity.Product
     */
    Product findProductById(String productId);


    /***
     *DESC: 删除一个商品
     *@author hou.linan
     *@date: 2018/8/30 18:47
     *@param: [productId]
     *@return: void
     */
    void delete(Product product);

    /**
    *DESC: 查询某商店内的所有商品
    *@author hou.linan
    *@date:  2018/9/6 17:14
    *@param:  [shop]
    *@return:  java.util.List<cn.hgxsp.hgxsp.entity.Product>
    */
    List<Product> findShopAllProduct(Shop shop) ;
}
