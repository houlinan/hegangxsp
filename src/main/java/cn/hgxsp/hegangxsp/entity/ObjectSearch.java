package cn.hgxsp.hegangxsp.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * DESC：搜索表
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/2
 * Time : 16:04
 */
@Entity
@Data
@Table(name = "objectsearch")
public class ObjectSearch implements Serializable {


    private static final long serialVersionUID = 8329796519893786391L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 搜索的内容
     */
    private String content;

}
