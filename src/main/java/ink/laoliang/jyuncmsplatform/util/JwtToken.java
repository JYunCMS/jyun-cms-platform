package ink.laoliang.jyuncmsplatform.util;

import ink.laoliang.jyuncmsplatform.domain.User;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;

import javax.crypto.spec.SecretKeySpec;
import java.util.Date;

@Component
public class JwtToken {

    // Jwt 的 Token 有效时长（毫秒数）
    private final static long VALID_TIME = 1000 * 60 * 60 * 24; // 24小时

    // 为 Token 签名的 算法
    private final static SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS512;

    /**
     * 创建 Token
     *
     * @param user
     * @param jwtSecretKey
     * @return
     */
    public static String createToken(User user, String jwtSecretKey) {
        long nowTime = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setIssuer("JYunCMS") // 签发人(iss) -> JYunCMS
                .setAudience(user.getUsername()) // 受众(aud) -> 存放用户名
                .setSubject(user.getRole()) // 主题(sub) -> 存放用户角色
                .setIssuedAt(new Date(nowTime)) // 签发时间(iat) -> 当前系统时间
                .setExpiration(new Date(nowTime + VALID_TIME)) // 过期时间(exp) -> 当前系统时间 + 定义的有效时长
                .signWith(SIGNATURE_ALGORITHM, new SecretKeySpec(jwtSecretKey.getBytes(), SIGNATURE_ALGORITHM.getJcaName()));

        return builder.compact();
    }

    /**
     * 核验 Token 并获取用户角色
     * 当身份验证通过，返回用户角色供拦截器向 Service 层传递
     *
     * @param token
     * @param username
     * @param jwtSecretKey
     * @return
     */
    public static String verifyTokenAndGetUserRole(String token, String username, String jwtSecretKey) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new SecretKeySpec(jwtSecretKey.getBytes(), SIGNATURE_ALGORITHM.getJcaName()))
                    .parseClaimsJws(token)
                    .getBody();

            if (!body.getAudience().equals(username)) {
                return "【用户令牌异常】- 令牌与用户名不匹配，请重新登录！";
            }

            // 身份验证通过，返回用户角色供拦截器向 Service 层传递
            return body.getSubject();

        } catch (ExpiredJwtException expiredJwtException) {
            return "【用户令牌异常】- 令牌已过期，请重新登录！";
        } catch (Exception e) {
            return "【用户令牌异常】- JWT 签名与本地计算的签名不匹配，请重新登陆！";
        }
    }
}
