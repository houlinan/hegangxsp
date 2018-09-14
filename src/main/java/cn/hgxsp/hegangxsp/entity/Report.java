package cn.hgxsp.hegangxsp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * DESC：用户商品举报表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:10
 */
@Entity
@Table(name = "report")
@Data
public class Report implements Serializable {


    private static final long serialVersionUID = 2805787059292944785L;
    @Id
    @ApiModelProperty(value = "主键ID" , name = "id" )
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;


    @ApiModelProperty(value = "举报类型：1-用户、2-店铺、3-商品" , name = "reportType" , example = "1")
    @Column(name = "reporttype" , length = 2)
    private Integer reportType ;


    @ApiModelProperty(value = "举报详情" , name = "reportDesc" , example = "涉黄!!" ,required = true)
    @Column(name = "reportInfo")
    private String reportDesc ;


    @ApiModelProperty(value = "举报截图" , name = "reportFace" , example = "/dsadasds/dsadas.jpg" ,required = true)
    @Column(name = "reportface")
    private String reportFace ;


    @Column(name = "create_time")
    @ApiModelProperty(value = "举报时间" , name = "createTime" )
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
}
