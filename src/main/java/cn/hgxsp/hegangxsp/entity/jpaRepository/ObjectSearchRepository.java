package cn.hgxsp.hegangxsp.entity.jpaRepository;

import cn.hgxsp.hegangxsp.entity.ObjectSearch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DESC：搜索类jpa接口类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 16:07
 */
@Repository
public interface ObjectSearchRepository extends JpaRepository<ObjectSearch, Long> {
}
