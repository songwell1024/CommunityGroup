package com.communitygroup.user.interceptor;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @program:
 * @description: 拦截器
 * @author: Song
 * @create: Created in 2019-02-28 11:05
 * @Modified by:
 **/
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private JwtUtil jwtUtil;

    //return true就是不拦截放行
    //return true就是拦截
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       System.out.println("经过了拦截器");
        String authorization = request.getHeader("Authorization");
        if (authorization != null && !"".equals(authorization) && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            Claims claims = jwtUtil.parseJWT(token);
            if (claims != null && claims.get("roles").equals("admin")){
                request.setAttribute("admin_claim",claims);
            }
            if (claims != null && claims.get("roles").equals("user")){
                request.setAttribute("user_claim",claims);
            }
        }
        return true;
    }
}
