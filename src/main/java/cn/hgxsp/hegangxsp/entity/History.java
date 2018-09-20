package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：浏览历史表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:06
 */
@Data
@Entity
@Table(name = "history")
public class History implements Serializable {


    private static final long serialVersionUID = -3078058006123396593L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id ;

    @ApiModelProperty(value = "用户ID" ,name = "userId"  ,example = "180913A3T5D7BFY8")
    @Column(name = "userid")
    private String userId ;

    @Column(name ="type")
    @ApiModelProperty(value = "搜索类型 1：商品、2：店铺" , name = "type" , example = "1" )
    private int type ;

    @Column(name = "objectid")
    @ApiModelProperty(value = "对象id" , name = "objectId" , example = "180918A012B1M140")
    private String objectId ;

    @ApiModelProperty(value = "店铺" , name = "shop" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Shop shop ;

    @ApiModelProperty(value = "商品" , name = "product" , example = "15156saddsa181123")
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private Product product ;

    @Column(name = "create_time")
    @ApiModelProperty(value = "创建时间" , name = "createTime" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;


}
