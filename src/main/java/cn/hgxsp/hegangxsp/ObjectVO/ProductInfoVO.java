package cn.hgxsp.hegangxsp.ObjectVO;


import com.fasterxml.jackson.annotation.JsonFormat;
import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * DESC：商品表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 14:40
 */
@Data
public class ProductInfoVO  implements Serializable {

    @Id
    @ApiModelProperty(value = "商品主键ID" , name = "id" )
    private String id ;


    @ApiModelProperty(value = "商品名称" , name = "productname" , example = "雪花牌护发素")
    private String productName ;


    @ApiModelProperty(value = "商品原价" , name = "price" , example = "10.11")
    private String price ;

    @ApiModelProperty(value = "商品库存" , name = "stock" , example = "110")
    private Integer stock ;


    @ApiModelProperty(value = "商品现价" , name = "newPrice" , example = "9.99")
    private String newPrice;


    @ApiModelProperty(value = "商品描述" , name = "productDesc" , example = "这是一个好好漂亮的商品")
    private String productDesc;


    @ApiModelProperty(value = "商品封面图片" , name = "productFace" , example = "/dsadas/dasda.jps")
    private String  productFace ;


    @ApiModelProperty(value = "商品排序" , name = "productOrder" , example = "0")
    private Integer  productOrder ;


    @ApiModelProperty(value = "商品类型" , name = "productType" , example = "0")
    private Integer productType ;

    @ApiModelProperty(value = "商品所属店铺" , name = "shop" )
    private Shop shop;

    @ApiModelProperty(value = "商品老板信息" , name = "user" )
    private User user;

    @ApiModelProperty(value = "商品的所有图片" , name = "productPicList" )
    private List<String> productPicList ;


    @ApiModelProperty(value = "店铺状态:1-正常、2-停用" , name = "actived" , example = "1" )
    private Integer actived = 1 ;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "商品下架时间" , name = "lowerTime" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date lowerTime;


    @ApiModelProperty(value = "商品创建时间" , name = "createTime" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
