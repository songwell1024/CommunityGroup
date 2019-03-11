package com.communitygroup.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import javax.servlet.http.HttpServletRequest;

/**
 * @program:
 * @description:
 * @author: Song
 * @create: Created in 2019-03-01 16:43
 * @Modified by:
 **/
public class WebFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return "pre";   //前置过滤器
    }

    @Override
    public int filterOrder() {
        return 0;   //过滤器的优先级，数字越小优先级越高
    }

    @Override
    public boolean shouldFilter() {
        return true;  // 是否执行该过滤器，此处为true，说明需要过滤
    }

    /**
     * 过滤器内执行的操作，return 任何object的值都说明继续执行
     * setsendzullResponse(false) 才表示不继续执行
     * 所有的访问
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.out.println("zuul过滤器...");
        //得到上下文信息
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取header 首先获取request域
        HttpServletRequest request = requestContext.getRequest();
        String authorization = request.getHeader("Authorization");
        if (authorization != null){
            //把头信息转发到微服务
            requestContext.addZuulRequestHeader("Authorization",authorization);
        }
        return null;
    }
}
