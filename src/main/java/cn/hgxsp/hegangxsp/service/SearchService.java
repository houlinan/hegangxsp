package cn.hgxsp.hegangxsp.service;



import cn.hgxsp.hegangxsp.ObjectVO.ProductListVO;
import cn.hgxsp.hegangxsp.ObjectVO.ShopVO;
import cn.hgxsp.hegangxsp.entity.Shop;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DESC：搜索类服务层接口类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 16:14
 */
public interface SearchService {


    /**
     * DESC: 获取排行前几的热搜词集合
     *
     * @author hou.linan
     * @date: 2018/9/2 16:15
     * @param: []
     * @return: java.util.List<java.lang.String>
     */
    List<String> getHot();

    /**
     * DESC: 查询所有商品，用于主页上显示
     *
     * @author hou.linan
     * @date: 2018/9/2 22:02
     * @param: [pageable]
     * @return: org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
     */
    Page<ProductListVO> findAllProduct(Integer index, Integer pageSize);

    /**
    *DESC:根据搜索关键词搜索所有符合的商品
    *@author hou.linan
    *@date:  2018/9/2 22:07
    *@param:  [pageable, searchValue]
    *@return:  org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
    */
    Page<ProductListVO> findAllProduct(Integer index, Integer pageSize, String searchValue);

    /**
     * DESC:查询某店铺内所有的商品
     *
     * @author hou.linan
     * @date: 2018/9/2 22:03
     * @param: [pageable, shopId]
     * @return: org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
     */
    Page<ProductListVO> findAllProductInShop(Integer index, Integer pageSize, String shopId);


    /**
    *DESC:根据搜索关键词搜索所有某店铺内的商品
    *@author hou.linan
    *@date:  2018/9/2 22:07
    *@param:  [pageable, shopId, searchValue]
    *@return:  org.springframework.data.domain.Page<cn.hgxsp.hgxsp.entity.Product>
    */
    Page<ProductListVO> findAllProductInShop(Integer index, Integer pageSize, Shop shop, String searchValue);


    /**
    *DESC:  获取所有店铺的分页
    *@author hou.linan
    *@date:  2018/9/19 16:01
    *@param:  [index, pageSize, searchValue]
    *@return:  org.springframework.data.domain.Page<cn.hgxsp.hegangxsp.ObjectVO.ShopVO>
    */
    Page<Shop> findAllShop(Integer index, Integer pageSize,  String searchValue) ;

}
