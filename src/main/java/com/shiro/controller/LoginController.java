package com.shiro.controller;

import com.shiro.bean.SessionBean;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by dell on 2020/9/22.
 */
@Controller
public class LoginController {

//    @Autowired
//    private MapCache mapCache;



    private Map<String,List<SessionBean>> map = new HashMap<>();


    @RequestMapping("/dologout")
    public String logout(HttpSession session, Model model) {
        System.out.println("安全退出："+session);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "login";
    }


    @PostMapping("/doLogin")
  // @ResponseBody
    public String doLogin(String username, String password,Model model) {


        System.out.println("用户和密码："+username+"===>"+password);


        Subject subject = SecurityUtils.getSubject();



        Session session = subject.getSession();
//        String username = (String) subject.getPrincipal();//*********************
        Serializable sessionId = session.getId();
        System.out.println("进入KickoutControl, sessionId:{}"+sessionId);// webSession
        List<SessionBean> sessionBeans =  map.get(username);
        if(!CollectionUtils.isEmpty(sessionBeans)){
            for(SessionBean bean:sessionBeans){
                if(!bean.getSessionId().equals(sessionId)) {
                    System.out.println("登出之前存在的此用户的数据:" + bean.getSessionId());
                     model.addAttribute("name", "您的账号在另一台设备上登录,如果非本人操作......");
                     bean.getSubject().logout();
                }
            }
        }
        List<SessionBean>  sessionBeanList = new ArrayList<>();
        SessionBean  sessionBean = new SessionBean();
        sessionBean.setSessionId(sessionId);
        sessionBean.setSubject(subject);
        sessionBeanList.add(sessionBean);
        map.put(username,sessionBeanList);
        System.out.println("====>"+map);



        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("登录成功!");
          //  model.addAttribute("name", "登录成功");

            return  "test";

        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在!");
            model.addAttribute("name", "用户名不存在");
        } catch (ExcessiveAttemptsException e){
            System.out.println("用户[{}]进行登录验证..失败验证超过{}次");
            model.addAttribute("name", "用户进行登录验证..失败验证超过次数");
        } catch (AuthenticationException  e) {
            model.addAttribute("name", "账号或密码错误");
        } catch (AuthorizationException  e) {
            e.printStackTrace();
            model.addAttribute("name", "没有权限");
           System.out.println("登录失败!");
        }
        return  "login";


    }


//    @RequiresPermissions("query")
    @GetMapping("/login")
    public String login(HttpSession session) {
        System.out.println("安全HttpSession："+session);
        return "login";
    }


    /**
     * 模拟首页
     * @return
     */
//    @RequiresPermissions("query")
    @GetMapping("/index")
    public  String index(){
        return  "test";
    }


    @RequiresPermissions("query")
    @GetMapping("/add")
    public String hello(HttpSession session) {
        System.out.println("安全HttpSession："+session);
      String id=  session.getId();
      System.out.println("sessionid:web传的"+id);

        return "user/add";
    }


    @RequiresPermissions("add")
    @GetMapping("/update")
    public String update() {
        return "user/update";

    }




//    @RequiresRoles("admin")
//    @GetMapping("/admin")
//    public String admin() {
//        return "admin success!";
//    }
//
//    @RequiresPermissions("query")
//    @GetMapping("/index")
//    public String index1() {
//        return "index success!";
//    }
//
//    @RequiresPermissions("add")
//    @GetMapping("/add")
//    public String add() {
//        return "add success!";
//    }
}