package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.service.UserService;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * DESC：商品店铺Controller层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/25
 * Time : 11:56
 */
@RestController
@RequestMapping("/shop")
@Slf4j
@Api(value = "店铺相关API方法", tags = "店铺相关Controller相关API")
public class ShopController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private UserService userService;


    /**
     * DESC:查询店铺名称是否重复 ，是否可用
     *
     * @author hou.linangetShopByShopId
     * @date: 2018/8/30 9:36
     * @param: [shopName]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @ApiOperation(value = "查看店铺名称是否可用", notes = "该接口查询店铺名称是否可以，是否存在")
    @GetMapping("/checkShopName")
    @ApiImplicitParam(name = "shopName", value = "需要验证的店铺名称", required = true, dataType = "String", defaultValue = "hln的小店", paramType = "query")
    public WXJSONResult checkShopName(@RequestParam("shopName") String shopName) {
        if (StringUtils.isEmpty(shopName)) return WXJSONResult.errorMsg("传入的用户名称为空");

        if (shopService.queryShopNameIsExist(shopName)) {
            return WXJSONResult.errorMsg("thisShopNameIsExist");
        }
        return WXJSONResult.ok("该店铺名称可用");
    }


    /**
     * DESC:创建一个店铺
     *
     * @author hou.linan
     * @date: 2018/8/30 9:37
     * @param: [shop, userId]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @ApiOperation(value = "创建店铺", notes = "创建 / 添加一个店铺")
    @PostMapping("/createShop")
    public WXJSONResult createShop(@RequestBody Shop shop, @RequestParam(name = "userId") String userId) {

        if (StringUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("传入的店铺为空");
        }
        if (StringUtils.isEmpty(shop.getShopName())) {
            return WXJSONResult.errorMsg("传入的店铺名称为空");
        }
        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("传入的用户ID为空");
        }
        User currUser = userService.findUserById(userId);

        if (StringUtils.isEmpty(currUser)) {
            return WXJSONResult.errorMsg("请登录");
        }
        WXJSONResult wxjsonResult = userService.countUserHasShop(userId);

        if (wxjsonResult.getStatus() == 200) {
            return WXJSONResult.errorMsg("您已经拥有一个店铺");
        }

        Shop shop1 = shopService.createShop(shop, currUser);

//        //绑定用户表中的店铺信息
//        currUser.setShop(shop1);
//        userService.updateUser(currUser);

        return WXJSONResult.ok(shop1);
    }


    /**
     * DESC:店铺上传头像
     *
     * @author hou.linan
     * @date: 2018/8/5 12:29
     * @param: [userId, files]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @PostMapping("/uploadShopFace")
    @ApiImplicitParam(name = "ShopId", value = "店铺Id", required = true, dataType = "String", paramType = "query")
    @ApiOperation(value = "店铺上传头像", notes = "店铺上传头像接口")
    public WXJSONResult uploadShopFace(String ShopId,
                                       @RequestParam("file") MultipartFile[] files) throws Exception {

        if (StringUtils.isEmpty(ShopId)) {
            return WXJSONResult.errorMsg("传入的店铺ID为空");
        }

        Shop shop = shopService.findShopByShopID(ShopId);
        if (StringUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("没有找到您传入店铺");
        }
        User currShopBoss = shop.getShopBoss();
        if (StringUtils.isEmpty(currShopBoss)) {
            return WXJSONResult.errorMsg("没有找到店主信息");
        }
        String currShopBossId = currShopBoss.getId();
        if (StringUtils.isEmpty(currShopBossId)) {
            return WXJSONResult.errorMsg("没有找到店主用户ID");
        }

        //保存文件的命名空间

        String uploadPathDB = "/" + currShopBossId + "/" + ShopId + "/ShopFace";
        log.info("文件大小为：{}" + files.length);
        uploadPathDB = UploadUtils.uploadPic(files[0], uploadPathDB , "来源店铺:"+shop.getShopName());


        shop.setShopFace(uploadPathDB);
        Shop result = shopService.updateShop(shop);


        return WXJSONResult.ok(result);
    }

    /**
    *DESC:通过用户ID查询店铺信息
    *@author hou.linan
    *@date:  2018/9/5 9:44
    *@param:  [userId]
    *@return:  cn.hgxsp.hgxsp.utils.WXJSONResult
    */
    @GetMapping("/getShopByUserId")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query", defaultValue = "admin")
    @ApiOperation(value = "通过用户ID查询店铺信息", notes = "通过用户Id查询店铺信息")
    public WXJSONResult getShopByUserId(String userId) {
        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("传入的用户ID为空");
        }
        User user = userService.findUserById(userId);
        if (ObjectUtils.isEmpty(user)) {
            return WXJSONResult.errorMsg("并没有找到该用户");
        }
        List<Shop> shop = shopService.findShopByShopBossId(userId);
        if (ObjectUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("您并不拥有店铺，请创建");
        }
        return WXJSONResult.ok(shop);
    }


    /**
    *DESC:通过店铺ID查询店铺
    *@author hou.linan
    *@date:  2018/9/5 9:45
    *@param:  [shopId]
    *@return:  cn.hgxsp.hgxsp.utils.WXJSONResult
    */
    @GetMapping("/getShopByShopId")
//    @Cacheable(cacheNames = "shop" , key = "#shopId" , unless = "#result.status != 200")
    @ApiImplicitParam(name = "shopId", value = "店铺Id", required = true, dataType = "String", paramType = "query", defaultValue = "1809056F7KM61968")
    @ApiOperation(value = "通过店铺ID获取店铺信息", notes = "通过店铺ID获取店铺信息接口")
    public WXJSONResult getShopByShopId(String shopId) throws Exception{

        if(StringUtils.isEmpty(shopId)){
            return WXJSONResult.errorMsg("传入的店铺信息为空") ;
        }
        Shop shop = shopService.findShopByShopID(shopId);
        if(ObjectUtils.isEmpty(shop)){
            return WXJSONResult.errorMsg("您查询的店铺已经不存在") ;
        }
        return WXJSONResult.ok(shop);
    }



}
