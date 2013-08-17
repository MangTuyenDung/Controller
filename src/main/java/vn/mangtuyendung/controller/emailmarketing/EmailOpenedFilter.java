package vn.mangtuyendung.controller.emailmarketing;

import com.hadoopvietnam.service.statistics.StatisticService;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

public class EmailOpenedFilter
        implements Filter {

    @Autowired
    private StatisticService statisticService;
    private static Pattern imagePattern = Pattern.compile("([^\\s]+(\\.(?i)(jpg|png|gif|bmp|ico))$)", 2);

    private boolean isOpened(HttpServletRequest request) {
        String url = request.getRequestURI().toString();
        Matcher m = imagePattern.matcher(url);
        return m.matches();
    }

    public void init(FilterConfig filterConfig)
            throws ServletException {
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (isOpened(httpServletRequest)) {
            this.statisticService.save(httpServletRequest);
        }
        chain.doFilter(request, response);
    }
}