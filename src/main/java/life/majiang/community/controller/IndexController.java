package life.majiang.community.controller;

import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/i")
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/index")
    public String index(HttpServletRequest request){
        Cookie[] cookies = request.getCookies();
        System.out.println(cookies.length);
        for (Cookie cookie : cookies) {
            //System.out.println("enter for");
            if(cookie.getName().equals("tokenya")){
                String token = cookie.getValue();
                System.out.println("tokenya:"+token);
                User user = userMapper.findByToken(token);
                if(user!=null){
                    System.out.println("index user exists");
                    System.out.println(user.getToken());
                    request.getSession().setAttribute("user",user);
                }
                System.out.println("index user exists");
                break;
            }

        }
        System.out.println("index html");
        return "index";
    }
}
