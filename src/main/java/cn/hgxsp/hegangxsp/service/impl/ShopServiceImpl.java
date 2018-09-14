package cn.hgxsp.hegangxsp.service.impl;


import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ShopRepository;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.utils.org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * DESC：商品店铺实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/25
 * Time : 11:38
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopRepository shopRepository;


    @Autowired
    private Sid sid ;


    @Override
    public Shop findShopByShopName(String shopName) {
        return shopRepository.findShopByShopName(shopName);
    }

    @Override
    public boolean queryShopNameIsExist(String shopName) {
        return shopRepository.countByShopName(shopName) > 0 ? true : false;
    }

    @Override
    public Shop createShop(Shop shop, User user) {
       String shopId = sid.nextShort();
       shop.setId(shopId);
       shop.setCreateTime(new Date());
       shop.setShopBoss(user);

       return shopRepository.save(shop) ;

    }

    @Override
    public Shop updateShop(Shop shop) {
        return shopRepository.save(shop);
    }

    @Override
    public Shop findShopByShopID(String shopID) {
        Shop shop = new Shop();
        shop.setId(shopID);
        Example<Shop> example = Example.of(shop);
        Optional<Shop> one = shopRepository.findOne(example);
        if(one.isPresent()){
            return one.get();
        }
        return null;
    }

    @Override
    public List<Shop> findShopByShopBossId(String userID) {
        List<Shop> shopByUser = shopRepository.findShopByShopBossId(userID);
        if(ObjectUtils.isEmpty(shopByUser)){
            return  null ;
        }
        return shopByUser ;
    }

    @Override
    public void deleteShop(Shop shop) {
        shopRepository.delete(shop);
    }


}
