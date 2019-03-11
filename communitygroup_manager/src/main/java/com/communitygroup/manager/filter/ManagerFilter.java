package com.communitygroup.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.exception.ZuulException;
import com.netflix.zuul.context.RequestContext;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 19:25
 * @Modified by:
 **/
public class ManagerFilter extends ZuulFilter {
    @Autowired
    JwtUtil jwtUtil;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        //获取上下文信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取Request域
        HttpServletRequest request = requestContext.getRequest();

        if(request.getMethod().equals("OPTIONS")){  //OPTIONS请求相当于一个检测目标是否安全的操作，类似于心跳机制。所以我们在后台拦截器里面应该把这个请求过滤掉。
            return null;
        }

        String url=request.getRequestURL().toString();
        if(url.indexOf("/admin/login")>0){
            System.out.println("登陆页面"+url);  //跳转到登录页面,没有登录就不用进行鉴权了
            return null;
        }

        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer")){
            String token  = authorization.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null){
                if (claims.get("roles").equals("admin")){
                    requestContext.addZuulRequestHeader("Authorization", authorization);
                    System.out.println("token 验证通过，添加了头信息"+ authorization);
                    return null;
                }
            }
        }
        requestContext.setSendZuulResponse(false);  //终止运行
        requestContext.setResponseStatusCode(401);
        requestContext.setResponseBody("无权访问");
        requestContext.getResponse().setContentType("text/html;charset=UTF‐8");  //返回的网页进行编码
        return null;

    }
}
