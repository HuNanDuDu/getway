package com.example.getway.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
public class MyFilter extends ZuulFilter {
    private Logger log  = LoggerFactory.getLogger(MyFilter.class);
    /**
     * filterType：返回一个字符串代表过滤器的类型，在zuul中定义了四种不同生命周期的过滤器类型，具体如下：
     * pre：路由之前
     * routing：路由之时
     * post： 路由之后
     * error：发送错误调用
     * @return
     */
    @Override
    public String filterType() {
        System.out.println("过滤器类型");
        return "route";
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
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        System.out.println(request);
        System.out.println("------------"+request.getServerPort());

        Object  token = request.getParameter("token");
        System.out.println(token);
        if(token == null) {
            log.info("fail");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);//401表示无权限
            try {
                ctx.getResponse().getWriter().write("token is empty");
            }catch (Exception e)
            {
            }
            return null;
        }
        log.info("pass");
        return null;
    }
}
