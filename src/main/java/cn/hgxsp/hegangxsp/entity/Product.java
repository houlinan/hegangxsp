package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * DESC：商品表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 14:40
 */
@Entity
@Data
@Table(name = "product")
public class Product implements Serializable {


    private static final long serialVersionUID = -4954906206689735498L;
    @Id
    @Column(length = 64)
    @ApiModelProperty(value = "商品主键ID" , name = "id" )
    private String id ;


    @ApiModelProperty(value = "商品名称" , name = "productname" , example = "雪花牌护发素")
    @Column(name = "productname" , length =20)
    private String productName ;


    @ApiModelProperty(value = "商品原价" , name = "price" , example = "10.11")
    private BigDecimal price ;

    @ApiModelProperty(value = "商品库存" , name = "stock" , example = "110")
    private Integer stock ;


    @ApiModelProperty(value = "商品现价" , name = "newPrice" , example = "9.99")
    private BigDecimal newPrice;


    @ApiModelProperty(value = "商品描述" , name = "productDesc" , example = "这是一个好好漂亮的商品")
    @Column(name = "productdesc")
    private String productDesc;


    @ApiModelProperty(value = "商品封面图片" , name = "productFace" , example = "/dsadas/dasda.jps")
    @Column(name = "productface")
    private String  productFace ;


    @ApiModelProperty(value = "商品排序" , name = "productOrder" , example = "0")
//    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name = "productorder")
    private Integer  productOrder ;


    @ApiModelProperty(value = "商品类型" , name = "productType" , example = "0")
    @Column(name = "producttype")
    private Integer productType ;

    @ApiModelProperty(value = "商品所属店铺" , name = "shop" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Shop shop ;

    @ApiModelProperty(value = "店铺状态:1-正常、2-停用" , name = "actived" , example = "1" )
    @Column(length = 2)
    private Integer actived = 1 ;

    @Column(name = "lowertime")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @ApiModelProperty(value = "商品下架时间" , name = "lowerTime" )
    @Temporal(TemporalType.TIMESTAMP)
    private Date lowerTime;


    @Column(name = "create_time")
    @ApiModelProperty(value = "商品创建时间" , name = "createTime" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
