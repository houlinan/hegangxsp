package cn.hgxsp.hegangxsp;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * DESC：
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/29
 * Time : 10:12
 */
@Configuration
public class MyWebAppConfigurer extends WebMvcConfigurerAdapter {


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
//
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
//
        registry.addResourceHandler("/**" )
                .addResourceLocations(
                        "classpath:/META-INF/resources/" )
                .addResourceLocations(
                        "file:G:/JAVA/wxFilesForHGXSP/" );
    }




}