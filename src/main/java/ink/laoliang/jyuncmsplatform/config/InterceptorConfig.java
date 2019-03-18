package ink.laoliang.jyuncmsplatform.config;

import ink.laoliang.jyuncmsplatform.util.JwtToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Date;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${custom.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ValidateAuthorizationInterceptor())
                .excludePathPatterns("/users/login");
    }

    private class ValidateAuthorizationInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 如果程序抛异常给前端，将再次访问 /error ，这里返回 true 将异常顺利抛向前端
            if (request.getRequestURI().equals("/error")) {
                return true;
            }

            // 核验 Token
            String result = JwtToken.verifyToken(request.getHeader("Authorization"), request.getHeader("Username"), JWT_SECRET_KEY);
            if (result != null) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.getWriter().write("{\n" +
                        "    \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n" +
                        "    \"status\": 401,\n" +
                        "    \"error\": \"Unauthorized\",\n" +
                        "    \"message\": \"" + result + "\",\n" +
                        "    \"path\": \"" + request.getRequestURI() + "\"\n" +
                        "}");
                return false;
            }

            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        }
    }
}
