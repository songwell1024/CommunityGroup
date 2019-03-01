package com.communitygroup.qa.interceptor;

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
 * @create: Created in 2019-02-28 14:48
 * @Modified by:
 **/
@Component
public class JwtInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Bearer ")){
            String token = authorization.substring(7);
            if (token != null){
                Claims claims = jwtUtil.parseJWT(token);
                if (claims != null && claims.get("roles").equals("user")){
                    request.setAttribute("user_claim", claims);
                }
                if (claims != null && claims.get("roles").equals("admin")){
                    request.setAttribute("admin_claim", claims);
                }
            }
        }
        return true;
    }
}
