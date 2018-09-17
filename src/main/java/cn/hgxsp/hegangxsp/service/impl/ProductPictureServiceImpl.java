package cn.hgxsp.hegangxsp.service.impl;


import cn.hgxsp.hegangxsp.entity.ProductPicture;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ProductPictureRepository;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * DESC：商品/店铺图片相关Service层实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/31
 * Time : 19:31
 */
@Service
public class ProductPictureServiceImpl implements ProductPictureService {

    @Autowired
    private ProductPictureRepository productPictureRepository;


    @Override
    public void savePicture(ProductPicture productPicture) {
        productPicture.setCreateTime(new Date());

        productPictureRepository.save(productPicture);
    }

    @Override
    public void deletePicture(Long productPictureId) {

        productPictureRepository.deleteById(productPictureId);
    }

    @Override
    public List<ProductPicture> findProductPicList(String objectId) {


        ProductPicture pr = new ProductPicture() ;
        pr.setFromProduct(objectId);

        Example<ProductPicture> example = Example.of(pr) ;
        return productPictureRepository.findAll(example);


    }

}
