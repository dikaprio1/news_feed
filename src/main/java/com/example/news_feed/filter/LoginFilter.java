package com.example.news_feed.filter;

import jakarta.servlet.*;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

//@WebFilter("/*") // 모든 요청을 필터링
public class LoginFilter implements Filter {

    private static final String[] WHITE_LIST = {
            "/api/auth/signup",
            "/api/auth/login"};

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);
        String path = httpRequest.getRequestURI();

        if (isWhiteList(path)) {
            chain.doFilter(request, response);
            return;
        }

        // 로그인 상태 확인
        if (session == null || session.getAttribute("user") == null) {
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json;charset=UTF-8");
            PrintWriter writer = httpResponse.getWriter();
            writer.write("{\"message\":\"로그인이 필요합니다.\"}");
            writer.flush();
            System.out.println("로그인이 필요합니다.");
            return;
        }
        chain.doFilter(request, response);
    }
    private boolean isWhiteList(String path) {
        for (String white : WHITE_LIST) {
            if (path.equals(white)) {
                return true;
            }
        }
        return false;
    }
}
