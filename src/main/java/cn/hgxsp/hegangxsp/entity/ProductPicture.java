package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：商品图片对应表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 14:41
 */
@Entity
@Data
@Table(name = "productpicture")
public class ProductPicture implements Serializable{


    private static final long serialVersionUID = 3064638728106316807L;
    @Id
    @ApiModelProperty(value = "主键ID" , name = "id" )
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @ApiModelProperty(value="图片路径" , name = "path" , example = "/dsda/dasdas.jps")
    private String path ;

    @ApiModelProperty(value="图片所属商品" , name = "fromProduct" , example = "125d6sa15asd12")
    @Column(name = "fromproduct" , length = 60)
    private String fromProduct ;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="create_time",columnDefinition="date")
    private Date createTime;

}
