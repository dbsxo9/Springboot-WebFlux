package webflux.example;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

public class MyFilter implements Filter {

    private EventNotify eventNotify;

    public MyFilter(EventNotify eventNotify){
        this.eventNotify = eventNotify;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("필터 실행됨");

        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.setContentType("text/event-stream; charset=utf-8");

        PrintWriter out = response.getWriter();
        // 1. Reactive Streams 라이브러리를 쓰면 표준을 지켜서 응답을 할 수 있다.
        for (int i= 0; i < 5; i++){
            out.print(" 응답 : " + i + "\n");
            out.flush(); // 버퍼 비우기
            try {
                Thread.sleep(1000); //응답 지연
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 2. SSE Emitter 라이브러리를 사용하면 편하게 쓸 수 있다.
        while (true){
            try {
                if(eventNotify.getChange()){
                    int lastIndex = eventNotify.getEvents().size() -1;
                    out.print(" 응답 : " + eventNotify.getEvents().get(lastIndex) + "\n");
                    out.flush(); // 버퍼 비우기
                    eventNotify.setChange(false);
                }
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        // 3. WebFlux -> Reactive Streams 가 적용된 stream을 배운다. (비동기 단일스레드 동작)
        // 4. Servlet MVC -> Reactive Streams 가 적용된 stream을 배운다. (멀티 스레드 방식)


    }
}
