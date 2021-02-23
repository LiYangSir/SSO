package cn.quguai.ssoserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

@Controller
public class LoginController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/login")
    public String login(@RequestParam("redirectUrl") String redirectUrl,
                        @CookieValue(value = "token", required = false) String token) {
        if (token != null) {
            return "redirect:" + redirectUrl + "?token=" + token;
        }
        return "login";
    }

    @ResponseBody
    @GetMapping("/token")
    public String token(@RequestParam("token") String token) {
        return redisTemplate.opsForValue().get(token);
    }

    @PostMapping("/login")
    public String redirect(@RequestParam("username") String username,
                           @RequestParam("password") String password,
                           @RequestParam("redirectUrl") String redirectUrl,
                           HttpServletResponse response) {
        if (StringUtils.hasText(username) && StringUtils.hasText(password)) {
            // 成功登录，并返回token
            String token = UUID.randomUUID().toString();
            redisTemplate.opsForValue().set(token, username);
            response.addCookie(new Cookie("token", token));
            return "redirect:" + redirectUrl + "?token=" + token;
        }
        return "redirect:/login";
    }

}
