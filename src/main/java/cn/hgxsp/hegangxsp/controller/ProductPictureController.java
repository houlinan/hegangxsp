package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.ProductPicture;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * DESC：商品/店铺图片处理的Controller层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/31
 * Time : 23:01
 */
@RequestMapping("/picture")
@Slf4j
@Api(value = "商品/店铺图片相关处理的controller", tags = "商品/店铺图片相关处理的controller")
@RestController
public class ProductPictureController extends BaseController {

    @Autowired
    private ProductPictureService productPictureService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @PostMapping("/savePic")
    @ApiOperation(value = "保存一张图片至图片数据库", notes = "保存商品/店铺图片接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "fromType", value = "商品/店铺", required = true, dataType = "String", paramType = "query", example = "pro / shop"),
            @ApiImplicitParam(name = "ObjectId", value = "商品/店铺ID", required = true, dataType = "String", paramType = "query")
    })
    public WXJSONResult savePic(String fromType, String ObjectId, @RequestParam("files") MultipartFile[] files) {

        log.info("上传了{}张图片" ,files.length);

        if (StringUtils.isEmpty(ObjectId)) {
            return WXJSONResult.errorMsg("传入的条件ID为空，请重新输入");
        }
        if (ObjectUtils.isEmpty(files)) {
            return WXJSONResult.errorMsg("您没有传入图片，请重新上传");
        }

        String uploadPathDB ;
        String shopBossName ;
        String shopName  ;

        if ("pro".equals(fromType)) {
            Product product = productService.findProductById(ObjectId);
            if(ObjectUtils.isEmpty(product)){
                return WXJSONResult.errorMsg("没有找到您的商品信息") ;
            }
            Shop shop = product.getShop();
            if(ObjectUtils.isEmpty(shop)){
                return WXJSONResult.errorMsg("没有找到商品所属店铺，请重新输入") ;
            }
            String shopID = shop.getId() ;
            if(StringUtils.isEmpty(shopID)){
                return WXJSONResult.errorMsg("没有找到商品所属店铺，请重新输入") ;
            }
            User user = shop.getShopBoss();
            if(ObjectUtils.isEmpty(user)){
                return WXJSONResult.errorMsg("没有找到该用户的相关信息，请您登陆重试！") ;
            }
            String userId =user .getId() ;

            if(StringUtils.isEmpty(userId)){
                return WXJSONResult.errorMsg("没有找到商品所属店铺，请重新输入") ;
            }

            uploadPathDB = "\\" + userId + "\\" + shopID + "\\" +  ObjectId + "\\" + "ProductPic" ;
            //处理水印

            shopBossName = user.getUserName() ;
            shopName = shop.getShopName() ;

        } else if ("shop".equals(fromType)) {

            Shop shop = shopService.findShopByShopID(ObjectId);

            if(ObjectUtils.isEmpty(shop)){
                return WXJSONResult.errorMsg("没有找到商品所属店铺，请重新输入") ;
            }

            User user = shop.getShopBoss();

            if(ObjectUtils.isEmpty(user)){
                return WXJSONResult.errorMsg("没有找到该用户的相关信息，请您登陆重试！") ;
            }
            String userId =user .getId() ;

            if(StringUtils.isEmpty(userId)){
                return WXJSONResult.errorMsg("没有找到商品所属店铺，请重新输入") ;
            }

            uploadPathDB = "\\" + userId + "\\" +  ObjectId + "\\ShopPic" ;
            //处理水印
            shopBossName = user.getUserName() ;
            shopName = shop.getShopName() ;

        } else {
            return WXJSONResult.errorMsg("没有找到所属物品类型,请重新输入");
        }


        ProductPicture productPicture = null;

        for (int a = 0; a < files.length; a++) {
            MultipartFile file = files[a];

            uploadPathDB = UploadUtils.uploadPic(file, uploadPathDB , "@"+shopBossName+" \n来源店铺:"+shopName);

            if(!StringUtils.isEmpty(uploadPathDB)){
                productPicture = new ProductPicture();
                productPicture.setFromProduct(ObjectId);
                productPicture.setPath(uploadPathDB);

                productPictureService.savePicture(productPicture);
            }
        }

        return WXJSONResult.ok();
    }


}
