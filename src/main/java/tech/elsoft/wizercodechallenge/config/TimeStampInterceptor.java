package tech.elsoft.wizercodechallenge.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
@Slf4j
public class TimeStampInterceptor implements HandlerInterceptor {
    private static final String START_TIME_ATTRIBUTE = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StopWatch sw = new StopWatch();
        sw.start();
        log.info("Request Received {} - {}", request.getRequestURI(), request.getMethod());
        request.setAttribute(START_TIME_ATTRIBUTE, sw);
        return true;
    }



    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        StopWatch sw = (StopWatch) request.getAttribute(START_TIME_ATTRIBUTE);
        sw.stop();
        Double totalTimeSeconds = sw.getTotalTimeSeconds();
//        response.addHeader("X-API-RESPONSE-TIME", String.valueOf(totalTimeSeconds));
        response.setHeader("X-API-RESPONSE-TIME", String.valueOf(totalTimeSeconds));
//        response.setHeader();
        log.info("Request Completed >>> STATUS {} - TOTAL TIME {}", response.getStatus(), totalTimeSeconds);


    }
}
