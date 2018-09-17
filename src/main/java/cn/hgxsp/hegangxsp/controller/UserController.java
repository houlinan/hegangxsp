package cn.hgxsp.hegangxsp.controller;

import cn.hgxsp.hegangxsp.ObjectVO.UserVO;
import cn.hgxsp.hegangxsp.entity.User;
import cn.hgxsp.hegangxsp.service.ShopService;
import cn.hgxsp.hegangxsp.service.UserService;
import cn.hgxsp.hegangxsp.utils.MD5Utils;
import cn.hgxsp.hegangxsp.utils.UploadUtils;
import cn.hgxsp.hegangxsp.utils.WXJSONResult;
import cn.hgxsp.hegangxsp.utils.org.n3r.idworker.Sid;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

/**
 * DESC：用户服务controller层
 * CREATED BY ：@hou.linan
 * CREATED DATE ：2018/8/22
 * Time : 16:11
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(value = "用户相关API方法", tags = "用户相关Controller相关API")
public class UserController extends BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private ShopService shopService;

    @Value("${upload.path}")
    public String UPLOADPATH  ;


    @Autowired
    private Sid sid;

    @GetMapping("/hello")
    public String hello() {

        log.info("进去了！");
        return UPLOADPATH;
    }


    /*
     *
     *===================================================================================
     * -----------------用户登陆、注册、注销接口！
     *==========================================================================
     *
     * */

    /**
     * DESC:用户注册
     *
     * @author hou.linan
     * @date: 2018/8/5 12:28
     * @param: [user]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @ApiOperation(value = "用户注册", notes = "用户注册的接口")
    @PostMapping("/regist")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户登陆名称", required = true, dataType = "String", paramType = "form", defaultValue = "admin"),
            @ApiImplicitParam(name = "passWord", value = "用户密码", required = true, dataType = "String", paramType = "form", defaultValue = "admim"),
    })
    public WXJSONResult registUser(@RequestParam(name = "userName") String userName,
                                   @RequestParam(name = "passWord") String passWord) throws Exception {

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return WXJSONResult.errorMsg("传入的用户名或密码为空");
        }
        //判断用户是否存在
        if (userService.queryUserNameIsExist(userName)) {
            return WXJSONResult.errorMsg("该用户已经存在");
        }

        User user = new User();
        user.setId(sid.nextShort());
        if ("admin".equals(userName)) {
            user.setRole(0);
        }
        user.setUserName(userName);
        user.setNikeName(userName);
        user.setPassWord(MD5Utils.getMD5Str(passWord));
        user.setCreateTime(new Date());

        userService.addUser(user);
        user.setPassWord("");
        user.setCreateTime(new Date());

        //设置UserToken
        UserVO usersVO = setUserRedisSessionToken(user);

        //添加日志
        log.info("用户【{}】注册成功！！" + user.getUserName());
        return WXJSONResult.ok(usersVO);
    }

    /**
     * DESC:用户登陆
     *
     * @author hou.linan
     * @date: 2018/8/5 12:28
     * @param: [user]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @PostMapping("/login")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名称", required = true, dataType = "String", paramType = "query", defaultValue = "admin"),
            @ApiImplicitParam(name = "passWord", value = "用户密码", required = true, dataType = "String", paramType = "query", defaultValue = "admim"),
    })
    @ApiOperation(value = "用户登陆", notes = "用户登陆接口")
    public WXJSONResult login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) throws Exception {

//        String userName = user.getUserName();
//        String passWord = user.getPassWord() ;
        log.info("用户尝试登陆， 用户名为：{} ， 用户的密码为：{}", userName, passWord);

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(passWord)) {
            return WXJSONResult.errorMsg("用户传入的用户名或者密码为空");
        }

        //用户不存在？
        if (!userService.queryUserNameIsExist(userName)) {
            return WXJSONResult.errorMsg("用户名或密码错误");
        }
        User currUser = userService.findUserByUserName(userName);


        // 判断密码
        if (!currUser.getPassWord().equals(MD5Utils.getMD5Str(passWord))) {
            return WXJSONResult.errorMsg("用户名或密码错误");
        }
        currUser.setLastLoginTime(new Date());
        userService.updateUser(currUser);

        currUser.setPassWord("");

        //设置UserToken
        UserVO usersVO = setUserRedisSessionToken(currUser);

        log.info("用户【{}】登陆成功，已经跳转到响应页面", userName);
        return WXJSONResult.ok(usersVO);
    }


    /**
     * DESC: 更新用户信息
     *
     * @author hou.linan
     * @date: 2018/8/30 9:35
     * @param: [user]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/update")
    @CachePut(cacheNames = "user" , key = "#userId" , unless = "#result.status != 200")
    @ApiOperation(value = "用户修改个人信息", notes = "用户修改个人信息接口")
    public WXJSONResult update(@RequestParam("userId") String userId ,
            @RequestBody User user) {
        if (user == null || StringUtils.isEmpty(user.getUserName())) {
            return WXJSONResult.errorMsg("传入的用户信息为空,请重新登录");
        }
        String sex = user.getSex();
        String sexString = "0";
        if (!StringUtils.isEmpty(sex)) {
            if ("我是男生".equals(sex)) {
                sexString = "1";
            } else if ("我是女生".equals(sex)) {
                sexString = "2";
            }
        }
        user.setSex(sexString);
        User result = userService.updateUser(user);
        result.setPassWord("");
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(result, userVO);

        return WXJSONResult.ok(userVO);
    }


    /**
     * DESC: 验证用户是否拥有店铺
     *
     * @author hou.linan
     * @date: 2018/8/30 9:35
     * @param: [userId]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @PostMapping("/checkUserHasShop")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query", defaultValue = "admin")
    @ApiOperation(value = "查询用户是否拥有店铺店铺", notes = "查询用户是否拥有店铺店铺的接口")
    public WXJSONResult getUserShop(@RequestParam("userId") String userId) {

        return userService.countUserHasShop(userId);
    }


    /**
     * DESC: 通过用户的useriD获取用户信息
     *
     * @author hou.linan
     * @date: 2018/8/30 9:35
     * @param: [userId]
     * @return: cn.hgxsp.hgxsp.utils.WXJSONResult
     */
    @GetMapping("/getUserInfo")
    @Cacheable(cacheNames = "user" , key = "#userId" , unless = "#result.status != 200")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query", defaultValue = "180904CXH5WHYW94")
    @ApiOperation(value = "通过用户ID获取用户详情", notes = "通过用户ID获取用户详情的接口")
    public WXJSONResult getUserInfo(@RequestParam("userId") String userId) {
        log.info("userId = {}", userId);
        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("传入的用户ID为空");
        }

        User user = userService.findUserById(userId);
        return user == null ? WXJSONResult.errorMsg("该用户不存在") : WXJSONResult.ok(user);


    }

    /**
     * DESC:用户上传头像
     *
     * @author hou.linan
     * @date: 2018/8/5 12:29
     * @param: [userId, files]
     * @return: com.trs.wxnew.utils.WXJSONResult
     */
    @PostMapping("/uploadUserFace")
    @CachePut(cacheNames = "user" , key = "#userId" , unless = "#result.status != 200")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    @ApiOperation(value = "用户上传头像", notes = "用户上传头像接口")
    public WXJSONResult uploadUserFace(String userId,
                                       @RequestParam("files") MultipartFile[] files) throws Exception {

        if (StringUtils.isEmpty(userId)) {
            return WXJSONResult.errorMsg("用户传入的用户ID为空");
        }

        User user = userService.findUserById(userId);
        System.out.println("userId == " + userId);    
        if (ObjectUtils.isEmpty(user)) {
            return WXJSONResult.errorMsg("该用户不存在");
        }


        String uploadPathDB = "/" + userId + "/face";

        //上传图片
        uploadPathDB = UploadUtils.uploadPic(files[0], uploadPathDB, "@" + user.getUserName());
        if (StringUtils.isEmpty(uploadPathDB)) {
            return WXJSONResult.errorMsg("上传头像失败");
        }

        //保存数据
        user.setFacePath(uploadPathDB);
        userService.updateUser(user);

        return WXJSONResult.ok(user);
    }

    /**
     * DESC: 用户注销接口
     *
     * @author hou.linan
     * @date: 2018/9/6 16:50
     * @param: [userId]
     * @return: void
     */
    @GetMapping("/logout")
    @ApiOperation(value = "用户注销", notes = "用户注销接口")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    public void logout(String userId) {

        removeUserRedisSessionToken(userId);

    }


    /**
     * DESC: 用户删除接口
     *
     * @author hou.linan
     * @date: 2018/9/6 16:50
     * @param: [userId]
     * @return: void
     */
    @GetMapping("/deleteUser")
    @CacheEvict(cacheNames = "user"  , key = "#userId" )
    @ApiOperation(value = "用户删除接口", notes = "用户删除接口")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true, dataType = "String", paramType = "query")
    public void deleteUser(String userId) {

        if (StringUtils.isEmpty(userId)) return;

        User user = userService.findUserById(userId);
        if (ObjectUtils.isEmpty(user)) return;

        userService.deleteUser(user);

    }

}
