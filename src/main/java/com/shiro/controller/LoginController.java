package com.shiro.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * Created by dell on 2020/9/22.
 */
@Controller
public class LoginController {

    @RequestMapping("/dologout")
    public String logout(HttpSession session, Model model) {
        System.out.println("安全退出："+session);
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
       // model.addAttribute("msg","安全退出！");
        return "login";
    }


    @PostMapping("/doLogin")
//    @ResponseBody
    public String doLogin(String username, String password,Model model) {
        System.out.println("用户和密码："+username+"===>"+password);
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(new UsernamePasswordToken(username, password));
            System.out.println("登录成功!");
          //  model.addAttribute("name", "登录成功");

            return  "test";

        } catch (UnknownAccountException e) {
            System.out.println("用户名不存在!");
            model.addAttribute("name", "用户名不存在");
        }catch (AuthenticationException  e) {
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