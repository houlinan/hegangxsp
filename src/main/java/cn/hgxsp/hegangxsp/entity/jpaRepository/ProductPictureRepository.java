package cn.hgxsp.hegangxsp.entity.jpaRepository;


import cn.hgxsp.hegangxsp.entity.ProductPicture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DESC：商品图片jpa处理类
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 15:57
 */
@Repository
public interface ProductPictureRepository extends JpaRepository<ProductPicture, Long> {


}
