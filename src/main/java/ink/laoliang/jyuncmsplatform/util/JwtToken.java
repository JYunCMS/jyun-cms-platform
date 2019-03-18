package ink.laoliang.jyuncmsplatform.util;

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
     * @param username
     * @param jwtSecretKey
     * @return
     */
    public static String createToken(String username, String jwtSecretKey) {
        long nowTime = System.currentTimeMillis();

        JwtBuilder builder = Jwts.builder()
                .setIssuer("JYunCMS") // 签发人(iss) -> JYunCMS
                .setAudience(username) // 受众(aud) -> 存放用户名
                .setIssuedAt(new Date(nowTime)) // 签发时间(iat) -> 当前系统时间
                .setExpiration(new Date(nowTime + VALID_TIME)) // 过期时间(exp) -> 当前系统时间 + 定义的有效时长
                .signWith(SIGNATURE_ALGORITHM, new SecretKeySpec(jwtSecretKey.getBytes(), SIGNATURE_ALGORITHM.getJcaName()));

        return builder.compact();
    }

    /**
     * 核验 Token
     *
     * @param token
     * @param username
     * @param jwtSecretKey
     * @return
     */
    public static String verifyToken(String token, String username, String jwtSecretKey) {
        try {
            Claims body = Jwts.parser()
                    .setSigningKey(new SecretKeySpec(jwtSecretKey.getBytes(), SIGNATURE_ALGORITHM.getJcaName()))
                    .parseClaimsJws(token)
                    .getBody();

            if (!body.getAudience().equals(username)) {
                // throw new UserTokenException("【用户令牌异常】- 非法令牌，请重新登录！");
                return "【用户令牌异常】- 令牌与用户名不匹配，请重新登录！";
            }
        } catch (ExpiredJwtException expiredJwtException) {
            // throw new UserTokenException("【用户令牌异常】- Token 已过期，请重新登录！");
            return "【用户令牌异常】- 令牌已过期，请重新登录！";
        } catch (Exception e) {
            // throw new UserTokenException("【用户令牌异常】- JWT 签名与本地计算的签名不匹配，请重新登陆！", e);
            return "【用户令牌异常】- JWT 签名与本地计算的签名不匹配，请重新登陆！";
        }

        return null;
    }
}
