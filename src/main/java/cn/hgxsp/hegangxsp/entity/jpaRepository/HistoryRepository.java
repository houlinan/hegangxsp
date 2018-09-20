package cn.hgxsp.hegangxsp.entity.jpaRepository;

import cn.hgxsp.hegangxsp.entity.History;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DESC：用户浏览历史表格
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/20
 * Time : 17:15
 */
@Repository
public interface HistoryRepository  extends JpaRepository<History, Long> {

    Page findAllByUserId(String userId , Pageable page) ;
}
