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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Value("${custom.upload-dir}")
    private String UPLOAD_DIR;

    @Value("${custom.jwt-secret-key}")
    private String JWT_SECRET_KEY;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludePathList = new ArrayList<>();
        excludePathList.add("/hello"); // 初始化接口
        excludePathList.add("/upload"); // 媒体资源上传接口
        excludePathList.add("/" + UPLOAD_DIR + "/**"); // 媒体资源存储地址
        excludePathList.add("/users/login"); // 后台登录接口
        excludePathList.add("/public/**"); // 前台应用的公开接口

        registry.addInterceptor(new ValidateAuthorizationInterceptor())
                .excludePathPatterns(excludePathList);
    }

    private class ValidateAuthorizationInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
            // 核验 Token 并获取用户角色
            String USER_ROLE = JwtToken.verifyTokenAndGetUserRole(request.getHeader("Authorization"), request.getHeader("From"), JWT_SECRET_KEY);

            // 返回字符串开头为“【”，表示 “未通过身份验证” ，返回 401 Unauthorized 错误
            if (USER_ROLE.substring(0, 1).equals("【")) {
                response.setStatus(401);
                response.setContentType("application/json;charset=UTF-8");
                response.setHeader("Connection", "close");
                String responseError = "{\n" +
                        "    \"timestamp\": \"" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "\",\n" +
                        "    \"status\": 401,\n" +
                        "    \"error\": \"Unauthorized\",\n" +
                        "    \"message\": \"" + USER_ROLE + "\",\n" +
                        "    \"path\": \"" + request.getRequestURI() + "\"\n" +
                        "}";
                response.getWriter().write(responseError);
                return false;
            }

            // 通过身份验证，将 Token 中提取的用户角色信息放置在 request 的 attribute 中，供 Service 层取用，验证角色权限
            request.setAttribute("USER_ROLE", USER_ROLE);
            return true;
        }

        @Override
        public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        }
    }
}
