package com.sjb.sjbProject.config;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "hotelLoginFilter", urlPatterns = {"/hotel/*"})
public class HotelLoginFilter implements Filter {

    // 標識：表示當前使用者尚未登入
    String NO_LOGIN = "尚未登入";

    // 不需要登入就可訪問的路徑
    String[] includeUrls = new String[]{"/sjb/hotel/login", "/sjb/hotel/logout", "/sjb/hotel/startRegister"};

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpSession session = request.getSession(false);
        String uri = request.getRequestURI();

        // System.out.println("過濾器 URL：" + uri);
        // 是否需要過濾
        boolean needFilter = isNeedFilter(uri);

        if (!needFilter) { // 不需要過濾直接傳給下一個過濾器
            filterChain.doFilter(servletRequest, servletResponse);
        } else { // 需要過濾器
            // session 中包含 loginId，則是登入狀態
            if (session != null && session.getAttribute("loginId") != null) {
                System.out.println("user:"+session.getAttribute("loginId"));
                filterChain.doFilter(request, response);
            } else {
                String requestType = request.getHeader("X-Requested-With");
                // 判斷是否是 AJAX 請求
                if (requestType != null && "XMLHttpRequest".equals(requestType)) {
                    response.getWriter().write(this.NO_LOGIN);
                } else {
                    // 重定向到登入
                    System.out.println("未登入請求 URL：" + uri);
                    response.sendRedirect(request.getContextPath() + "/hotel/login");
                }
                return;
            }
        }
    }

    public boolean isNeedFilter(String uri) {

        for (String includeUrl : includeUrls) {
            if (includeUrl.equals(uri)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
