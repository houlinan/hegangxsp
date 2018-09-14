package cn.hgxsp.hegangxsp.entity.jpaRepository;


import cn.hgxsp.hegangxsp.entity.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DESC：用户收藏jpa处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:57
 */
@Repository
public interface CollectionRepository  extends JpaRepository<Collection, Long> {


}
