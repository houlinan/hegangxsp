package cn.hgxsp.hegangxsp.ObjectVO;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.persistence.Id;
import java.util.Date;

/**
 * DESC：用户实体类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 13:08
 */
@Data
@ApiModel(value = "用户返给前端实体类" , description = "用户实体对象")
public class UserVO  {

    @Id
    @ApiModelProperty(value = "用户主键ID" , hidden = true)
    private String id ;


    @ApiModelProperty(value = "用户登陆名" , name = "userName" , example = "hegangxsp" , required = true)
    private String userName;

    @ApiModelProperty(value = "用户昵称" , name = "nikeName" , example = "哈哈啊哈" )
    private String nikeName;

    @ApiModelProperty(value = "用户性别,1-男、2-女、3-保密" , name = "sex" , example = "1" )
    private String sex;

    @ApiModelProperty(value = "生日" , name = "birthday" , example = "19910812" )
    private String birthday;


    @ApiModelProperty(value = "用户角色" , name = "role" , example = "2" , required = true)
    private Integer role = 2 ;


    @ApiModelProperty(value = "用户状态:1-正常、2-停用" , name = "actived" , example = "1" )
    private Integer actived = 1;


    @ApiModelProperty(value = "用户手机" , name = "moblie" , example = "17625997779" )
    private String moblie ;


    @ApiModelProperty(value = "用户真实姓名" , name = "trueName" , example = "侯立男" )
    private String trueName ;

    @ApiModelProperty(value = "用户签名" , name = "userDesc" , example = "这里是一段用户的介绍或者个性签名" )
    private String userdesc ;

    @ApiModelProperty(value = "用户头像路径" , name = "facePath" , example = "/dada/dsadas.jpg" )
    private String facePath ;

    @ApiModelProperty(value = "用户地址" , name = "address" , example = "黑龙江省鹤岗市" )
    private String address;

    @ApiModelProperty(value = "微信号码" , name = "wxNumber" , example = "2929452" )
    private String wxNumber;

    @ApiModelProperty(value = "用户Token" , name = "userToken" , example = "dsadasd-dsada-dsadas" )
    private String userToken;

//
//    //拥有店铺
//    @ManyToOne
//    @JoinColumn(foreignKey = @ForeignKey(name = "none"))
//    private Shop shop ;


    /*
     * 最后登陆时间
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date lastLoginTime;


    /*
     * 创建时间
     * */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", locale = "zh", timezone = "GMT+8")
    private Date createTime;




}
