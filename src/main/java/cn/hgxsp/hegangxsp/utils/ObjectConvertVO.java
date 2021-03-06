package cn.hgxsp.hegangxsp.utils;

import cn.hgxsp.hegangxsp.ObjectVO.HistoryVO;
import cn.hgxsp.hegangxsp.ObjectVO.ShopVO;
import cn.hgxsp.hegangxsp.entity.History;
import cn.hgxsp.hegangxsp.entity.Shop;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * DESC：实体对象转换成Vo对象
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 9:26
 */
public class ObjectConvertVO {


    /**
     * DESC: Entity对象 --> VO对象
     *
     * @author hou.linan
     * @date: 2018/9/20 9:27
     * @param: [object, currClass]
     * @return: java.lang.Object
     */
    public static Object Object2ClassObject(Object object, Class currClass) {
        try {
            Object result = currClass.forName(currClass.getName()).newInstance();
            BeanUtils.copyProperties(object, result);
            return result;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * DESC: 将pageShop对象转成ShopVo对象
     *
     * @author hou.linan
     * @date: 2018/9/20 9:47
     * @param: [result]
     * @return: org.springframework.data.domain.Page<cn.hgxsp.hegangxsp.ObjectVO.ShopVO>
     */
    public static Page<ShopVO> pageShop2PageShoplistVO(Page<Shop> result) {

        PageRequest page = new PageRequest(result.getPageable().getPageNumber(), result.getPageable().getPageSize());

        List<ShopVO> shopVOSResult = result.getContent().stream()
                .map(shop -> (ShopVO) ObjectConvertVO.Object2ClassObject(shop, ShopVO.class))
                .filter(e -> e != null)
                .collect(Collectors.toList());

        List<ShopVO> collect = shopVOSResult.stream().map(e -> shop2ShopVO(e)).collect(Collectors.toList());

        return new PageImpl<>(collect, page, result.getTotalElements());

    }

    /**
     * DESC:将historyList转成HistoryVO对象
     *
     * @author hou.linan
     * @date: 2018/9/21 9:22
     * @param: [historyList]
     * @return: java.util.List<cn.hgxsp.hegangxsp.ObjectVO.HistoryVO>
     */
    public static List<HistoryVO> history2HistoryVO(List<History> historyList) {

        if (ObjectUtils.isEmpty(historyList)) return null;
        try {
            return historyList.stream().map(item -> history2HistroyVO(item)).filter( item -> item != null ).collect(Collectors.toList());
        } catch (Exception e) {
            return null;
        }
    }
    /**
    *DESC:将history 对象转成VO对象
    *@author hou.linan
    *@date:  2018/9/21 9:56
    *@param:  [history]
    *@return:  cn.hgxsp.hegangxsp.ObjectVO.HistoryVO
    */
    public static HistoryVO history2HistroyVO(History history) {
        HistoryVO hisVo = new HistoryVO();
        //存入的是店铺的信息 (搜索类型 1：商品、2：店铺)
        try {
            if (ObjectUtils.isEmpty(history.getProduct()) && !ObjectUtils.isEmpty(history.getShop())) {
                hisVo.setObjectName(history.getShop().getShopName());
                hisVo.setFacePic(history.getShop().getShopFace());
                hisVo.setId(history.getShop().getId());
                hisVo.setType(2);
                hisVo.setTime(TimeAgoUtils.format(history.getShop().getCreateTime()));

                //存入的是商品的信息
            } else if (!ObjectUtils.isEmpty(history.getProduct()) && ObjectUtils.isEmpty(history.getShop())) {
                hisVo.setObjectName(history.getProduct().getProductName());
                hisVo.setFacePic(history.getProduct().getProductFace());
                hisVo.setId(history.getProduct().getId());
                hisVo.setType(1);
                hisVo.setTime(TimeAgoUtils.format(history.getCreateTime()));

            }
        } catch (Exception e) {
            return null ;
        }

        return hisVo;
    }


    /**
     * DESC:将shop --> shopVo
     *
     * @author hou.linan
     * @date: 2018/9/20 9:56
     * @param: [shop]
     * @return: cn.hgxsp.hegangxsp.ObjectVO.ShopVO
     */
    public static ShopVO shop2ShopVO(ShopVO shopVO) {

        shopVO.getShopBoss().setPassWord("");
        shopVO.getShopBoss().setTrueName("");
        shopVO.getShopBoss().setWxNumber("");
        shopVO.getShopBoss().setAddress("");
        shopVO.getShopBoss().setNikeName("");
        return shopVO;
    }


    public static void main(String[] args) {

        List<Shop> list = new ArrayList<>();
        for (int a = 0; a < 10; a++) {
            Shop shop = new Shop();
            shop.setId(UUID.randomUUID().toString());
            shop.setShopName("店铺 + " + a);
            shop.setCreateTime(new Date());
            shop.setShopFace("d:\\sasda");
            shop.setActived(1);
            shop.setShopDesc("dsadsadas");
            list.add(shop);
        }
        list.stream().forEach(e -> System.out.println(Object2ClassObject(e, ShopVO.class).toString()));

    }
}
