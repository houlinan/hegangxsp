package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：用户收藏表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:16
 */
@Entity
@Table(name = "collection")
@Data
public class Collection implements Serializable {


    private static final long serialVersionUID = -5740611786002411276L;
    @Id
    @ApiModelProperty(value = "主键ID" , name = "id" )
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value = "收藏种类" ,name = "collectionType"  ,example = "1")
    @Column(name = "collectiontype")
    private Integer collectionType;

    @ApiModelProperty(value = "收藏用户" ,name = "collectionUser" )
    @ManyToOne
    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
    private User collectionUser;

    @ApiModelProperty(value = "收藏的物品ID" ,name = "goodsId"  ,example = "1121dsadasa4123")
    @Column(name = "goodsId")
    private String goodsId;


    @Column(name = "create_time")
    @ApiModelProperty(value = "举报时间" , name = "createTime" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
}
