package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.service.UserService;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * DESC：商品类Controller类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/30
 * Time : 18:40
 */
@Slf4j
@RestController
@RequestMapping("/product")
@CacheConfig(cacheNames = "product")
@Api(value = "商品相关API方法", tags = "商品相关Controller相关API")
public class ProductController extends BaseController {


    @Autowired
    private ProductService productService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductPictureService productPictureService;


    @PostMapping("/createProduct")
    @ApiOperation(value = "创建一个新的商品", notes = "创建一个新的商品接口")
    @ApiImplicitParam(name = "ShopId", value = "店铺Id", required = true, dataType = "String", paramType = "query")
    public WXJSONResult createProduct(@RequestBody Product product,
                                      @RequestParam("shopId") String shopId,
                                      @RequestParam("userId") String userId) {

        if (StringUtils.isEmpty(shopId)) {
            return WXJSONResult.errorMsg("获取后台的店铺信息为空，请先创建店铺");
        }
        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("获取后台的用户信息为空，请先登陆");
        }


        Shop shop = shopService.findShopByShopID(shopId);
        if (ObjectUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("并没有找到该店铺信息，请先创建店铺");
        }
        User user = userService.findUserById(userId);

        log.info("用户尝试创建一个店铺，用户姓名为：【{}】" , user.getUserName());
        if (ObjectUtils.isEmpty(user)) {
            return WXJSONResult.errorMsg("并没有找到您的用户信息，请先登陆");
        }
        try {
            if (!shop.getShopBoss().getUserName().equals(user.getUserName())) {
                return WXJSONResult.errorMsg("您的店铺信息和用户信息不匹配。请咨询最下方的管理员解决问题！");
            }
        } catch (Exception e) {
            return WXJSONResult.errorMsg("您的店铺信息和用户信息不匹配。请咨询最下方的管理员解决问题！");
        }


        if (ObjectUtils.isEmpty(product)) {
            return WXJSONResult.errorMsg("传入的商品为空");
        }

        if (StringUtils.isEmpty(product.getProductName())) {
            return WXJSONResult.errorMsg("传入的商品名称为空");
        }

        if (StringUtils.isEmpty(product.getProductType()) || product.getProductType() == 0) {
            return WXJSONResult.errorMsg("请设置商品所属类型");
        }

        product.setShop(shop);


        product = productService.save(product);


        return WXJSONResult.ok(product);
    }


    /**
     * DESC:商品上传头像
     *
     * @author hou.linan
     * @date: 2018/8/5 12:29
     * @param: [userId, files]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @PostMapping("/uploadProductFace")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "shopId", value = "店铺ID", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    })

    @ApiOperation(value = "商品上传头像", notes = "商品上传头像接口")
    public WXJSONResult uploadUserFace(String productId, String shopId, String userId,
                                       @RequestParam("files") MultipartFile[] files) throws Exception {

        //判断参数是否为空
        if (StringUtils.isEmpty(productId)) {
            return WXJSONResult.errorMsg("获取后台的商品信息为空，请先创建商品");
        }
        if (StringUtils.isEmpty(shopId)) {
            return WXJSONResult.errorMsg("获取后台的店铺信息为空，请先创建店铺");
        }
        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("获取后台的用户信息为空，请先登陆");
        }


        //判断对象是否为空
        Product product = productService.findProductById(productId);
        if (ObjectUtils.isEmpty(product)) {
            return WXJSONResult.errorMsg("并没有找到该商品信息，请先创建商品");
        }

        Shop shop = shopService.findShopByShopID(shopId);
        if (ObjectUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("并没有找到该店铺信息，请先创建店铺");
        }
        User user = userService.findUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            return WXJSONResult.errorMsg("并没有找到您的用户信息，请先登陆");
        }
        try {
            if (!shop.getShopBoss().getUserName().equals(user.getUserName())) {
                return WXJSONResult.errorMsg("您的店铺信息和用户信息不匹配。请咨询最下方的管理员解决问题！");
            }
        } catch (Exception e) {
            return WXJSONResult.errorMsg("您的店铺信息和用户信息不匹配。请咨询最下方的管理员解决问题！");
        }


        //判断文件是否为空
        if (files.length == 0 || ObjectUtils.isEmpty(files)) {
            return WXJSONResult.errorMsg("请选择正确的头像.并重试");
        }


        String uploadPathDB = "\\" + userId + "\\" + shopId + "\\ProductFace";

        //上传图片
        uploadPathDB = UploadUtils.uploadPic(files[0], uploadPathDB, "店铺：" + shop.getShopName());

        if (StringUtils.isEmpty(uploadPathDB)) {
            return WXJSONResult.errorMsg("上传头像失败");
        }

        product.setProductFace(uploadPathDB);
        Product result = productService.update(product);
        if (ObjectUtils.isEmpty(result)) {
            return WXJSONResult.errorMsg("上传头像失败！！！！");
        }

        return WXJSONResult.ok(result);

    }

    @GetMapping("/deleteProduct")
    @CacheEvict(key = "#productId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "String", paramType = "query")
            , @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")

    })
    @ApiOperation(value = "删除一个商品", notes = "删除一个商品的接口")
    public WXJSONResult deleteProductById(String productId , String userId) {
        
        if(StringUtils.isEmpty(userId)){
            return WXJSONResult.errorMsg("请登录之后才能删除商品");
        }

        if (StringUtils.isEmpty(productId)) {
             return WXJSONResult.errorMsg("传入的商品信息为空");
        }

        //判断商品Id是否存在
        Product product = productService.findProductById(productId);
        if (ObjectUtils.isEmpty(product)) {
            return WXJSONResult.errorMsg("您删除的商品已经不存在");
        }
        //判断删除者和店主是不是一个人
        try {
            String id = product.getShop().getShopBoss().getId();
            if(!StringUtils.isEmpty(id)){
                if(!userId.equals(id)){
                    return WXJSONResult.errorMsg("您没有权限删除该商品");
                }
            }
        }catch(Exception e){
            return WXJSONResult.errorMsg("删除商品失败");
        }

        productService.delete(product);

        return WXJSONResult.ok();
    }


}
