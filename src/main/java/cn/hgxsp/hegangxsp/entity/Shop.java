package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：店铺表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 14:04
 */
@Entity
@Table(name = "shop")
@Data
public class Shop implements Serializable {


    private static final long serialVersionUID = 3762043679926232279L;


    @Id
    @Column(name = "id" ,length = 64)
    @ApiModelProperty(value = "店铺主键ID" , hidden = true)
    private String id ;


    @ApiModelProperty(value = "商铺名称" , name = "shopName" , example = "嘉丽美容美发用品" , required = true)
    @Column(name ="shopname" ,length = 64)
    private String shopName ;

    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    @ApiModelProperty(value = "店主信息" , name = "shopBoss" , example = "123" ,required = true)
    private User shopBoss ;


    @ApiModelProperty(value = "营业时间" , name = "businessTime" , example = "早上9:00 - 晚上20:00")
    @Column(name ="businesstime" , length = 64)
    private String businessTime;


    @ApiModelProperty(value = "商铺地址" , name = "address" , example = "五道街审计四号楼")
    private String address ;


    @ApiModelProperty(value = "商铺电话" , name = "tel" , example = "0468-8927131")
    @Column(length = 20  )
    private String tel ;

    @ApiModelProperty(value = "商铺简介" , name = "shopDesc" , example = "这个是一家美容美发商品的小店")
    @Column(name = "shopdesc")
    private String shopDesc ;


    @ApiModelProperty(value = "商铺封面头像" , name = "shopFace" , example = "/sdasdasd/dsada.jpg")
    @Column(name = "shopface")
    private String shopFace ;


    @ApiModelProperty(value = "店铺类型" , name = "shopType" , example = "1" )
    @Column(length = 10 , name =  "shoptype")
    private Integer shopType ;


    @ApiModelProperty(value = "店铺状态:1-正常、2-停用" , name = "actived" , example = "1" )
    @Column(length = 2)
    private Integer actived = 1 ;


    @ApiModelProperty(value = "店铺显示顺序" , name = "order" , example = "1")
    @Column(name = "shoporder")
    private Integer shopOrder ;


    /*
     * 创建时间
     * */
    @Column(name = "create_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

}
