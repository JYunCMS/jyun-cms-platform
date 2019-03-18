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

            // 核验 Token 并获取用户角色
            String USER_ROLE = JwtToken.verifyTokenAndGetUserRole(request.getHeader("Authorization"), request.getHeader("Username"), JWT_SECRET_KEY);
            request.setAttribute("USER_ROLE", USER_ROLE);
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
