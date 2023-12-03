package com.oxygen.wechat.interceptor;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.oxygen.wechat.util.JWTUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class JWTInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")){
            return true;
        }
        Map<String, Object> map = new HashMap<>();
        //1.获取请求头中的令牌
        String token = request.getHeader("Authorization");
        token = token.replace("Bearer ", "");



        /*String refreshToken = request.getHeader("ReAuthorization");
        refreshToken = refreshToken.replace("Bearer ", "");*/
//        String token = request.getHeader("token");
//        String refreshToken = request.getHeader("refreshToken");
        System.out.println(token);
        /*String username = JWTVerify.getClaim("username").asString();*/
        if (JWTUtils.verify(token) && JWTUtils.checkToken(token) == true) {
            logger.info("token生效");
            long expireTime = JWT.decode(token)
                    .getExpiresAt().getTime()/1000;
            logger.info("token剩余时间为==="+(expireTime - System.currentTimeMillis()/1000));
            return true;
        }else if(JWTUtils.verify(token) == true && JWTUtils.checkToken(token) == false) {
            logger.info("token即将失效");
            DecodedJWT JWTVerify = JWTUtils.getTokenInfo(token);
            String username = JWTVerify.getClaim("username").asString();
            Map<String, String> payload = new HashMap<>();
            payload.put("username", username);
            //生成JWT令牌
            String newToken = JWTUtils.getToken(payload);
//            String newRefreshToken = JWTUtils.getReFreshToken(payload);
//            map.put("msg","token已失效，请重新登录");
            logger.info("newToken===" + newToken);
//            System.out.println("newRefreshToken===" + newRefreshToken);
            response.addHeader("token", newToken);
//            response.addHeader("refreshToken", newRefreshToken);
            response.addIntHeader("code", 50000);
            return true;
        }else {
            logger.info("token失效");
            map.put("msg","token失效，请重新登录");
        }
//        map.put("msg","refreshToken失效，请重新登录");
        response.addIntHeader("code",50001);
        map.put("code",108);
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
