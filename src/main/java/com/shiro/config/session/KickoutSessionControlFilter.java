package com.shiro.config.session;




import com.shiro.bean.SessionBean;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionKey;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by dell on 2020/9/23.
 */



/**
 * 并发登录人数控制
 * @author wgc
 */
public class KickoutSessionControlFilter extends AccessControlFilter {

    private static final Logger logger = LoggerFactory.getLogger(KickoutSessionControlFilter.class);


    /**
     * 踢出后到的地址
     */
    private String kickoutUrl;

    /**
     * 踢出之前登录的/之后登录的用户 默认踢出之前登录的用户
     */
    private boolean kickoutAfter = false;
    /**
     * 同一个帐号最大会话数 默认1
     */
    private int maxSession = 1;

    private String kickoutAttrName = "kickout";

    private SessionManager sessionManager;
    private Cache<String, Deque<Serializable>> cache;

    public void setKickoutUrl(String kickoutUrl) {
        this.kickoutUrl = kickoutUrl;
    }

    public void setKickoutAfter(boolean kickoutAfter) {
        this.kickoutAfter = kickoutAfter;
    }

    public void setMaxSession(int maxSession) {
        this.maxSession = maxSession;
    }

    public void setSessionManager(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    /**
     *  设置Cache的key的前缀
     */
    public void setCacheManager(CacheManager cacheManager) {
        this.cache = cacheManager.getCache("shiro-kickout-session");
    }

    /**
     * 是否允许访问，返回true表示允许
     */
    @Override protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
            throws Exception {
        return false;
    }

    private Map<String,List<SessionBean>> map = new HashMap<>();



    /**
     * 表示访问拒绝时是否自己处理，如果返回true表示自己不处理且继续拦截器链执行，返回false表示自己已经处理了（比如重定向到另一个页面）。
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
            throws Exception {



        Subject subject = getSubject(request, response);
        if(!subject.isAuthenticated() && !subject.isRemembered())
        {
            //如果没有登录，直接进行之后的流程
            return true;
        }

//        Session session = subject.getSession();
//        String username = (String) subject.getPrincipal();//*********************
//        Serializable sessionId = session.getId();
//        System.out.println("进入KickoutControl, sessionId:{}"+sessionId);// webSession
//        List<SessionBean> sessionBeans =  map.get(username);
//        if(!CollectionUtils.isEmpty(sessionBeans)){
//            for(SessionBean bean:sessionBeans){
//                if(!bean.getSessionId().equals(sessionId)) {
//                    System.out.println("登出之前存在的此用户的数据:" + bean.getSessionId());
//                    bean.getSubject().logout();
//                }
//            }
//        }
//        List<SessionBean>  sessionBeanList = new ArrayList<>();
//        SessionBean  sessionBean = new SessionBean();
//        sessionBean.setSessionId(sessionId);
//        sessionBean.setSubject(subject);
//        sessionBeanList.add(sessionBean);
//        map.put(username,sessionBeanList);
//        System.out.println("====>"+map);





        return true;
    }
}

