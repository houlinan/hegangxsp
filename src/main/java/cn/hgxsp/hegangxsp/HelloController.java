package cn.hgxsp.hegangxsp;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/9/13
 * Time : 10:42
 */
@RestController
public class HelloController {

    @GetMapping
    public String hellop(){
        return "Hello11111111111111";
    }
}
