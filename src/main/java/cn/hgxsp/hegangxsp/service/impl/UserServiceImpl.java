package cn.hgxsp.hegangxsp.service.impl;


import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.ProductPicture;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ShopRepository;
import cn.hgxsp.hegangxsp.entity.jpaRepository.UserRepository;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.service.UserService;
import cn.hgxsp.hegangxsp.utils.UpdataObjectUtil;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * DESC：用户接口实现类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 16:06
 */
@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShopRepository shopRepository;

    @Autowired
    private ProductService productService ;

    @Autowired
    private ProductPictureService productPictureService ;

    @Autowired
    private ShopService shopService;


    @Override
    public User addUser(User user) {

        return user == null ? null : userRepository.save(user);
    }

    @Override
    public boolean queryUserNameIsExist(String userName) {
        return userRepository.findUserByUserName(userName) == null ? false : true;

    }

    @Override
    public User findUserByUserName(String userName) {
        return userRepository.findUserByUserName(userName);
    }

    @Override
    public User updateUser(User user) {
        String userName = user.getUserName();
        User currUser = findUserByUserName(userName);
        UpdataObjectUtil.copyNullProperties(currUser, user);
        return userRepository.save(user);
    }

    @Override
    public User findUserById(String userId) {
        Optional<User> result = userRepository.findById(userId);
        if (result.isPresent()) {
            return result.get();
        }
        return null;

    }

    @Override
    public WXJSONResult removeUserShop(User user, Shop shop) {
        //TODO：11
        return null;
    }

    @Override
    public WXJSONResult countUserHasShop(String userId) {
        long result = shopRepository.countByShopBossId(userId);
        return result > 0 ? WXJSONResult.ok() : WXJSONResult.errorMsg("userNotHasShop");
    }

    @Override
    public void deleteUser(User user) {

        String userId = user.getId();

        //删除本地文件
        UploadUtils.deleteUser(user);

        List<ProductPicture> productPictureList = new ArrayList<>();

        List<Shop> shops = shopService.findShopByShopBossId(userId);
        if(!ObjectUtils.isEmpty(shops)){
            for(Shop currShop : shops){
                //如果用户存在店铺
                if (!ObjectUtils.isEmpty(currShop)) {
                    List<Product> shopAllProduct = productService.findShopAllProduct(currShop);
                    for(Product product :shopAllProduct){
                        productService.delete(product);
                        List<ProductPicture> productPicList = productPictureService.findProductPicList(product.getId());
                        productPictureList.addAll(productPicList) ;
                    }

                }
                List<ProductPicture> shopPicList = productPictureService.findProductPicList(currShop.getId());

                shopPicList.forEach(t ->productPictureList.add(t) );

                shopService.deleteShop(currShop);
            }
        }
        productPictureList.forEach(t -> productPictureService.deletePicture(t.getId())) ;

        userRepository.delete(user) ;
    }






}
