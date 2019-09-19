package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.ObjectVO.ProductInfoVO;
import cn.hgxsp.hegangxsp.ObjectVO.ProductListVO;
import cn.hgxsp.hegangxsp.ObjectVO.ShopVO;
import cn.hgxsp.hegangxsp.entity.*;
import cn.hgxsp.hegangxsp.entity.jpaRepository.ObjectSearchRepository;
import cn.hgxsp.hegangxsp.service.ProductPictureService;
import cn.hgxsp.hegangxsp.service.ProductService;
import cn.hgxsp.hegangxsp.service.SearchService;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.utils.ObjectConvertVO;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 16:18
 */
@Slf4j
@RestController
@RequestMapping("/search")
@Api(value = "搜索相关API方法", tags = "搜索方法相关API")
public class SearchObjectController extends BaseController {


    @Autowired
    private SearchService searchService;

    @Autowired
    private ProductPictureService productPictureService ;

    @Autowired
    private ShopService shopService ;

    @Autowired
    private ProductService productService ;

    @Autowired
    private ObjectSearchRepository objectSearchRepository ;

    /**
     * DESC: 获取所有热搜关键词， 默认前十
     *
     * @author hou.linan
     * @date: 2018/9/3 14:10
     * @param: []
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @GetMapping("/getHot")
    @ApiOperation(value = "获取热搜词排行的数组", notes = "获取热搜词排行的数组接口")
    public WXJSONResult getHot() {
        return WXJSONResult.ok(searchService.getHot());
    }


    /**
     * DESC: 获取所有商品的productListOV对象的集合  带有分页功能
     *
     * @author hou.linan
     * @date: 2018/9/3 14:11
     * @param: [pageSize, pageIndex]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/getAllProduct")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "15", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
    })
    @ApiOperation(value = "获取全部商品", notes = "获取全部的商品列表，并包含分页")
    public WXJSONResult getAllProduct(@RequestParam(defaultValue = "5") Integer pageSize, Integer pageIndex) {



        Page<ProductListVO> resultPage = searchService.findAllProduct(pageIndex, pageSize);


        return WXJSONResult.ok(resultPage);
    }

    /**
     *DESC: 获取某商店的所有商品列表， 带有分页功能
     *@author hou.linan
     *@date:  2018/9/3 14:13
     *@param:  [pageSize, pageIndex, shopId]
     *@return:  cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/getAllProductInShop")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "15", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "shopId", value = "店铺Id", required = true, defaultValue = "180902G2RZFFF32W", paramType = "query")
    })
    @ApiOperation(value = "获取全部商品", notes = "获取全部的商品列表，并包含分页")
    public WXJSONResult getAllProductInShop(@RequestParam(defaultValue = "5") Integer pageSize
            , Integer pageIndex, String shopId) {

        return WXJSONResult.ok(   searchService.findAllProductInShop(pageIndex, pageSize, shopId));

    }


    /**
     *DESC: 通过商品名称进行模糊查询，并查询所有商品的productListVo对接并返回， 带有分页功能
     *@author hou.linan
     *@date:  2018/9/3 14:13
     *@param:  [pageSize, pageIndex, shopId]
     *@return:  cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/getAllProductBySearchValue")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "15", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "searchValue", value = "模糊查询关键字", required = true, defaultValue = "5", paramType = "query")
    })
    @ApiOperation(value = "获取全部商品", notes = "获取全部的商品列表，并包含分页")
    public WXJSONResult getAllProductBySearchValue(@RequestParam(defaultValue = "5") Integer pageSize
            , Integer pageIndex, String searchValue) {

        return WXJSONResult.ok(searchService.findAllProduct(pageIndex, pageSize, searchValue));

    }


    /**
     *DESC: 通过商品名称进行模糊查询，并查询某商店内商品的productListVo对接并返回， 带有分页功能
     *@author hou.linan
     *@date:  2018/9/3 14:13
     *@param:  [pageSize, pageIndex, shopId]
     *@return:  cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/getAllProductBySearchValueAndShopId")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "15", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "shopId", value = "商店ID", required = true, defaultValue = "180902G2RZFFF32W", paramType = "query"),
            @ApiImplicitParam(name = "searchValue", value = "模糊查询关键字", required = false , defaultValue = "5", paramType = "query")
    })
    @ApiOperation(value = "获取全部商品", notes = "获取全部的商品列表，并包含分页")
    public WXJSONResult getAllProductBySearchValueAndShopId(@RequestParam(defaultValue = "5") Integer pageSize
            , Integer pageIndex, @RequestParam(required = false ) String searchValue , String shopId) {

        Shop shop = shopService.findShopByShopID(shopId);
        if (ObjectUtils.isEmpty(shop)) {
            return WXJSONResult.errorMsg("并没找到您传入的店铺信息") ;
        }

        if(StringUtils.isEmpty(searchValue)){
            return WXJSONResult.ok(searchService.findAllProductInShop(pageIndex, pageSize, shopId));
        }

        return WXJSONResult.ok(searchService.findAllProductInShop(pageIndex, pageSize, shop, searchValue));

    }


    @GetMapping("/getProductInfo")
    @ApiImplicitParam(name = "productId", value = "商品ID", required = true, dataType = "String", paramType = "query")
    @ApiOperation(value = "获取某商品的详情", notes = "获取某商品的详情的接口")
    public WXJSONResult getProductInfo(String productId) {

        if (StringUtils.isEmpty(productId)) {
            return WXJSONResult.errorMsg("传入的商品ID为空，请在正确的页面点击进入该页面！");
        }

        //判断商品Id是否存在
        Product product = productService.findProductById(productId);
        if (ObjectUtils.isEmpty(product)) {
            return WXJSONResult.errorMsg("该商品已经不存在，或者已经被删除或者已经下架，请进行其他操作！");
        }

        Shop shop = product.getShop();
        if(ObjectUtils.isEmpty(shop)){
            return WXJSONResult.errorMsg("操作错误，并没有找到该商品的店铺信息，请点击其他商品查看");
        }
        User user = shop.getShopBoss();
        user.setPassWord("");
        if(ObjectUtils.isEmpty(user)){
            return WXJSONResult.errorMsg("并没有找到该商品的所有者，或者改商品已经停止销售，请稍后重试");
        }

        ProductInfoVO productInfoVO = new ProductInfoVO() ;



        BeanUtils.copyProperties(product , productInfoVO);

        if(!ObjectUtils.isEmpty(product.getPrice())){
            productInfoVO.setPrice(String.valueOf(product.getPrice().setScale(2 , BigDecimal.ROUND_HALF_UP)));
        }

        if(!ObjectUtils.isEmpty(product.getNewPrice())){
            productInfoVO.setNewPrice(String.valueOf(product.getNewPrice().setScale(2 , BigDecimal.ROUND_HALF_UP)));
        }

        shop.setShopBoss(null);
        productInfoVO.setShop(shop);
        productInfoVO.setUser(user);


        List<ProductPicture> productPicList = productPictureService.findProductPicList(productId);


        productInfoVO.setProductPicList( productPicList.stream().map(e -> e.getPath()).collect(Collectors.toList()));


        return WXJSONResult.ok(productInfoVO);
    }


    @PostMapping("/getAllShopToPage")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageSize", value = "每页显示数目", required = false, defaultValue = "15", paramType = "query"),
            @ApiImplicitParam(name = "pageIndex", value = "第几页", required = true, defaultValue = "1", paramType = "query"),
            @ApiImplicitParam(name = "searchValue", value = "模糊查询关键字", required = false , defaultValue = "5", paramType = "query")
    })
    @ApiOperation(value = "获取全部店铺列表", notes = "获取所有店铺列表，并包含分页")
    public WXJSONResult getAllShopToPage(@RequestParam(value = "pageSize" ,defaultValue = "5") Integer pageSize ,
                                         @RequestParam(value ="pageIndex") Integer pageIndex,
                                         @RequestParam(value = "searchValue" ,required = false)String searchValue){


        Page<Shop> allShop = searchService.findAllShop(pageIndex, pageSize, searchValue);

        return WXJSONResult.ok( ObjectConvertVO.pageShop2PageShoplistVO(allShop));
    }


    @GetMapping("/addSearchContent")
    @ApiImplicitParam(name = "content" , value = "搜索内容" , required = true ,paramType = "query")
    @ApiOperation(notes = "将搜索字段添加进数据库的接口" , value = "将搜索字段添加进数据库")
    public void addSearchContent(String content) {
        if(StringUtils.isEmpty(content)) return ;
        ObjectSearch objectSearch = new ObjectSearch() ;
        objectSearch.setContent(content);
        objectSearchRepository.save(objectSearch) ;
    }
}
