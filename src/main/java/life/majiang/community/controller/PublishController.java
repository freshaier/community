package life.majiang.community.controller;

import life.majiang.community.mapper.QuestionMapper;
import life.majiang.community.mapper.UserMapper;
import life.majiang.community.model.Question;
import life.majiang.community.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
//@RequestMapping("/p")
public class PublishController {
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/publish")
    public String publish(){

        return "publish";
    }
    @PostMapping("/publish")
    public String doPublish(@RequestParam(value = "title",required = false) String title,
                            @RequestParam(value = "description",required = false) String description,
                            @RequestParam(value = "tag",required = false) String tag,
                            HttpServletRequest request, Model model
                            ){
        model.addAttribute("tag",tag);
        System.out.println(tag);
        model.addAttribute("description",description);
        System.out.println(description);
        model.addAttribute("title",title);
        System.out.println(title);
        if(description==null||description.equals("")){
            model.addAttribute("error","问题补充不能为空");
            return "publish";
        }
        if(title==null||title.equals("")){
            model.addAttribute("error","标题不能为空");
            return "publish";
        }

        if(tag==null||tag.equals("")){
            model.addAttribute("error","标签不能为空");
            return "publish";
        }


        User user =null;
        Cookie[] cookies = request.getCookies();
        System.out.println("cookies length:"+cookies.length);
        for (Cookie cookie : cookies) {
            if(cookie.getName().equals("tokenya")){
                String token = cookie.getValue();
                System.out.println(token);
                user = userMapper.findByToken(token);
                if(user!=null){
                    request.getSession().setAttribute("user",user);
                }
                System.out.println("publish user exists");
                break;
            }
        }

        if(user==null){
            model.addAttribute("error","用户未登录");
            return "publish";
        }

        Question question = new Question();
        question.setTitle(title);
        question.setDescription(description);
        question.setTag(tag);
        question.setCreator(user.getId());
        question.setGmtCreate(System.currentTimeMillis());
        question.setGmtModified(question.getGmtCreate());
        questionMapper.create(question);
        return "redirect:/";
    }
}
