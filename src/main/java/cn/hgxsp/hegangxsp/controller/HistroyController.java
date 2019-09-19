package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.entity.History;
import cn.hgxsp.hegangxsp.entity.Product;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.service.HistoryService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * DESC： 浏览历史Controller层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/21
 * Time : 10:01
 */

@RestController
@RequestMapping("/histroy")
@Slf4j
@Api(value = "历史相关Api", tags = "历史相关Controller的Api")
public class HistroyController extends BaseController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private HistoryService historyService;


    @GetMapping("/add")
    @ApiOperation(value = "添加一个浏览历史", notes = "添加一个浏览历史的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "objectId", value = "浏览Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "type", value = "浏览Id", required = true, dataType = "Integer", paramType = "query")
    })
    public void add(String objectId, String userId, Integer type) {
        History history = new History();
        if (StringUtils.isEmpty(objectId) || StringUtils.isEmpty(userId) || StringUtils.isEmpty(type)) return;
        //搜索类型 1：商品、2：店铺
        if (type == 1) {
            Product product = productService.findProductById(objectId);
            if(ObjectUtils.isEmpty(product) ) return  ;
            history.setProduct(product);
        } else if (type == 2) {
            Shop shop = shopService.findShopByShopID(objectId);
            if(ObjectUtils.isEmpty(shop) ) return  ;
            history.setShop(shop);
        } else {
            return;
        }
        history.setUserId(userId);
        history.setType(type);
        historyService.updateHistroy(history);
    }

    @PostMapping("/findUserHistroy")
    @ApiOperation(value = "查找一个用户所有的浏览历史", notes = "查找一个用户所有的浏览历史的接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "5", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
    })
    public WXJSONResult findUserHistroy(String userId, Integer pageIndex, Integer pageSize) {

        if (StringUtils.isEmpty(userId)) return WXJSONResult.errorMsg("传入的用户信息为空");
        if (StringUtils.isEmpty(pageIndex)) return WXJSONResult.errorMsg("传入的页数为空");

        return WXJSONResult.ok(historyService.findAllByUserId(userId, pageIndex, pageSize));
    }


    @PostMapping("/deleteByUserId")
    @ApiOperation(value = "删除一个用户的所有历史数据", notes = "删除一个用户的所有历史数据的接口")
    @ApiImplicitParam(name = "userId", value = "用户Id", required = true, dataType = "String", paramType = "query")
    public void deleteByUserId(String userId) {

        historyService.deleteAllHistory(userId);
    }


}


