package cn.hgxsp.hegangxsp.ObjectVO;

import cn.hgxsp.hegangxsp.entity.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：店铺表VO类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 14:04
 */
@Data
public class ShopVO implements Serializable {


    private static final long serialVersionUID = 467588432268588814L;


    @Id
    @ApiModelProperty(value = "店铺主键ID" , hidden = true)
    private String id ;


    @ApiModelProperty(value = "商铺名称" , name = "shopName" , example = "嘉丽美容美发用品" , required = true)
    private String shopName ;


    @ApiModelProperty(value = "店主信息" , name = "shopBoss" , example = "123" ,required = true)
    private User shopBoss ;


    @ApiModelProperty(value = "商铺简介" , name = "shopDesc" , example = "这个是一家美容美发商品的小店")
    private String shopDesc ;


    @ApiModelProperty(value = "商铺封面头像" , name = "shopFace" , example = "/sdasdasd/dsada.jpg")
    private String shopFace ;


    @ApiModelProperty(value = "店铺状态:1-正常、2-停用" , name = "actived" , example = "1" )
    private Integer actived = 1 ;


    @ApiModelProperty(value = "店铺显示顺序" , name = "order" , example = "1")
    private Integer shopOrder ;




}
