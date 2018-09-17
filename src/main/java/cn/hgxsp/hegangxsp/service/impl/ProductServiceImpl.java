package cn.hgxsp.hegangxsp.service.impl;


import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.ProductPicture;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ProductRepository;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DESC：商品服务层实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/30
 * Time : 18:50
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductPictureService productPictureService;

    @Autowired
    private Sid sid;

    @Override
    public Product save(Product product) {

        product.setId(sid.nextShort());
//        Date data = new Date() ;
        product.setCreateTime(new Date());

        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {

        return productRepository.save(product);
    }

    @Override
    public Product findProductById(String productId) {
        Optional<Product> product = productRepository.findById(productId);
        if (product.isPresent()) {
            return product.get();
        }
        return null;
    }

    @Override
    public void delete(Product product) {
        String productId = product.getId();
//删除封面
        UploadUtils.deleteObjectFile(product.getProductFace());

        List<ProductPicture> productPicList = productPictureService.findProductPicList(productId);

        for (ProductPicture p : productPicList) {

            //删除文件的文件夹
            UploadUtils.deleteProductFileDirectory(p.getPath());

            //删除数据库记录
            productPictureService.deletePicture(p.getId());
        }

        productRepository.delete(product);


    }

    @Override
    public List<Product> findShopAllProduct(Shop shop) {
        return productRepository.findByShopId(shop.getId());
    }
}
