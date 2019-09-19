package cn.hgxsp.hegangxsp.service;

import cn.hgxsp.hegangxsp.entity.History;
import org.springframework.data.domain.Page;

/**
 * DESC：用户搜索历史service层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:16
 */
public interface HistoryService {

    /**
    *DESC:添加一条记录
    *@author hou.linan
    *@date:  2018/9/20 17:19
    *@param:  [history]
    *@return:  void
    */
    void addHistory(History history);

    /**
    *DESC:查询某人所有的记录
    *@author hou.linan
    *@date:  2018/9/20 17:19
    *@param:  [userId]
    *@return:  org.springframework.data.domain.Page
    */
    Page findAllByUserId(String userId ,Integer index, Integer pageSize);

    /**
    *DESC: 删除所有的记录
    *@author hou.linan
    *@date:  2018/9/20 17:19
    *@param:  [userId]
    *@return:  void
    */
    void deleteAllHistory(String userId) ;

    /**
    *DESC: 更新一个历史的浏览时间
    *@author hou.linan
    *@date:  2018/9/21 10:47
    *@param:  [userId, objectId, type]
    *@return:  void
    */
    void updateHistroy(History history);
}
