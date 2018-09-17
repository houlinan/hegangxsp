package cn.hgxsp.hegangxsp.entity.jpaRepository;

import cn.hgxsp.hegangxsp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DESC：用户jpa处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:57
 */
@Repository
public interface UserRepository extends JpaRepository<User, String> {

    /*
    *DESC: 通过用户名称查询用户信息
    *@author hou.linan
    *@date:  2018/8/22 16:30
    *@param:  [userName]
    *@return:  cn.hgxsp.hgxsp.entity.User
    */
    User findUserByUserName(String userName) ;

}
