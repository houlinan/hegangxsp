package cn.hgxsp.hegangxsp.ObjectVO;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;

/**
 * DESC：商品目录VO类，里面只有商品封面，商品ID 和商品名称的返回值
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 22:57
 */
@Data
@ApiModel(value = "商品目录VO类，里面只有商品封面，商品ID 和商品名称的返回值" , description = "商品目录VO类，里面只有商品封面，商品ID 和商品名称的返回值")
public class ProductListVO {

    @Id
    @ApiModelProperty(value = "商品主键ID")
    private String id ;

    @ApiModelProperty(value = "商品封面" , name = "productFace" , example = "/dsadas/dasda.jps" )
    private String productFace;

    @ApiModelProperty(value = "商品名称" , name = "productName" , example = "护发素" )
    private String productName;
}
