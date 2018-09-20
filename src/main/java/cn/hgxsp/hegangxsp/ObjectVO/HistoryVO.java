package cn.hgxsp.hegangxsp.ObjectVO;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * DESC：搜索历史返回VO对象
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:20
 */
@Data
public class HistoryVO implements Serializable {

    @ApiModelProperty(value = "objectName" , name = "对象名称" )
    private String objectName ;

    @ApiModelProperty(value = "type" , name = "对象类型")
    private int type ;

    @ApiModelProperty(value = "id" , name = "对象Id")
    private String id ;

    @ApiModelProperty(value = "facePic" , name = "封面图片")
    private String facePic ;

    @ApiModelProperty(value = "time" , name = "查询时间")
    private String time ;

}
