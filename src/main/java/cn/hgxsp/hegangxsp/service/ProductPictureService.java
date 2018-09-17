package cn.hgxsp.hegangxsp.service;


import cn.hgxsp.hegangxsp.entity.ProductPicture;

import java.util.List;

/**
 * DESC：商店/店铺所属图片处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/31
 * Time : 19:27
 */
public interface ProductPictureService {

    /**
    *DESC: 保存图片
    *@author hou.linan
    *@date:  2018/8/31 19:39
    *@param:  [productPicture]
    *@return:  void
    */
    void savePicture(ProductPicture productPicture);


    /**
    *DESC: 删除图片
    *@author hou.linan
    *@date:  2018/8/31 19:39
    *@param:  [productPictureId]
    *@return:  void
    */
    void deletePicture(Long productPictureId) ;

    /**
    *DESC: 查询某商品/店铺的所有图片
    *@author hou.linan
    *@date:  2018/8/31 19:40
    *@param:  [product]
    *@return:  java.util.List<cn.hgxsp.hgxsp.entity.ProductPicture>
    */
    List<ProductPicture> findProductPicList(String objectId) ;


}
