package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.ObjectVO.UserVO;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.utils.RedisOperator;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * DESC: 基础Controller层 需要被继承
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 16:12
 */
@RestController
public class BaseController {

    @Autowired
    public RedisOperator redisOperator ;



    public static final String USER_REDIS_SESSION = "user-redis-session" ;


    /**
     *DESC: 想redis中添加用户的token
     *@author hou.linan
     *@date:  2018/8/22 16:21
     *@param:  [user]
     *@return:  com.trs.wxnew.ResultVO.UsersVO
     */
    public UserVO setUserRedisSessionToken(User user){
        //将用户的信息添加到redis中
        String uniqueToken = UUID.randomUUID().toString();

        //此处使用 ‘ ： ’ 可以在redis中将数据分类保存
        redisOperator.set(USER_REDIS_SESSION+":"+user.getId() , uniqueToken , 1000*60*30 );

        UserVO usersVO = new UserVO();
        BeanUtils.copyProperties(user, usersVO);
        usersVO.setUserToken(uniqueToken);

        return usersVO ;
    }

    /*
     *DESC:移除用户的redis的UUID
     *@author hou.linan
     *@date:  2018/8/22 16:20
     *@param:  [userId]
     *@return:  void
     */
    public void removeUserRedisSessionToken(String userId){
        redisOperator.del(USER_REDIS_SESSION+":"+userId);
    }


}
