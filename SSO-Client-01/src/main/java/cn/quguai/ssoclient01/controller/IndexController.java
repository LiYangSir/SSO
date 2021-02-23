package cn.quguai.ssoclient01.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    @Value("${sso.certification.center-url}")
    private String centerUrl;

    @Value("${sso.certification.path}")
    private String path;

    @Resource
    private RestTemplate restTemplate;

    @GetMapping("/")
    public String index(@RequestParam(value = "token", required = false) String token, HttpSession session) {

        if (StringUtils.hasLength(token)) {
            // 跟库token获取最新值
            String username = restTemplate.getForObject(centerUrl + "/token?token=" + token, String.class);
            // 保存到session中
            session.setAttribute("user", username);
            return "index";
        } else {
            Object user = session.getAttribute("user");
            return user == null ? "redirect:" + centerUrl + path + "?redirectUrl=http://localhost:8081" : "index";
        }
    }
}
