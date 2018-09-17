package cn.hgxsp.hegangxsp.service;


import cn.hgxsp.hegangxsp.entity.Shop;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;

/**
 * DESC：用户方法接口类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 16:03
 */
public interface UserService {

    /**
     * DESC: 添加用户
     *
     * @author hou.linan
     * @date: 2018/8/22 16:04
     * @param: [user]
     * @return: cn.hgxsp.hgxsp.entity.User
     */
    User addUser(User user);


    /**
     * DESC: 查看用户是否存在
     *
     * @author hou.linan
     * @date: 2018/8/22 16:28
     * @param: [userName]
     * @return: boolean
     */
    boolean queryUserNameIsExist(String userName);


    /**
     * DESC: 通过用户名称查询用户对象
     *
     * @author hou.linan
     * @date: 2018/8/22 17:09
     * @param: [userName]
     * @return: cn.hgxsp.hgxsp.entity.User
     */
    User findUserByUserName(String userName);

    /**
     * DESC: 更新用户信息
     *
     * @author hou.linan
     * @date: 2018/8/24 22:48
     * @param: [user]
     * @return: cn.hgxsp.hgxsp.entity.User
     */
    User updateUser(User user);

    /**
     * DESC:通过用户iD查看用户信息
     *
     * @author hou.linan
     * @date: 2018/8/26 14:50
     * @param: [userId]
     * @return: cn.hgxsp.hgxsp.entity.User
     */
    User findUserById(String userId);


    /**
     * DESC: 移除用户绑定的店铺
     *
     * @author hou.linan
     * @date: 2018/8/27 15:09
     * @param: [user, shop]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    WXJSONResult removeUserShop(User user, Shop shop);


    /**
     * DESC:查询用户拥有店铺数量
     *
     * @author hou.linan
     * @date: 2018/8/29 13:52
     * @param: [userId]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    WXJSONResult countUserHasShop(String userId);

    /**
     * DESC: 删除一个用户，并删除用户的所有文件和记录
     *
     * @author hou.linan
     * @date: 2018/9/6 16:58
     * @param: [user]
     * @return: void
     */
    void deleteUser(User user);
}
