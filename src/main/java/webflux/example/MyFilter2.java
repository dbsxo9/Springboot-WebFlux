package webflux.example;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter2 implements Filter {

    private EventNotify eventNotify;

    public MyFilter2(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터2 실행됨");
        //데이터를 하나 발생시켜서 MyFilter의 응답 중 중간에 다른 데이터 응답시키기
        eventNotify.add("새로운 데이터");
    }
}
