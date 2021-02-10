package com.works.sevendays.util;

import org.apache.catalina.filters.ExpiresFilter;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Configuration
public class FilterConfig implements Filter {
    final Util util;
    public FilterConfig(Util util){
        this.util=util;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String url = req.getRequestURI();
        if (!url.contains("h2-console")) {
            util.userInfo(url);
        }
        chain.doFilter(request, response);
    }
}
