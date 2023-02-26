package com.sparta.springmvc;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/response")
public class HelloResponseController {

    private static long visitCount = 0;

    @GetMapping("/html/redirect")
    public String htmlFile() {
        return "redirect:/hello.html";
    }

    @GetMapping("/html/templates")
    public String htmlTemplates() {
        return "hello";
    }

    @ResponseBody
    @GetMapping("/body/html")
    public String helloStringHTML() {
        return "<!DOCTYPE html>" +
                "<html>" +
                "<head><meta charset=\"UTF-8\"><title>By @ResponseBody</title></head>" +
                "<body> Hello, 정적 웹 페이지!!</body>" +
                "</html>";
    }

    @GetMapping("/html/dynamic")
    public String helloHtmlFile(Model model) {
        visitCount++;
        model.addAttribute("visits", visitCount);
        return "hello-visit";
    }

    @ResponseBody
    @GetMapping("/json/string")
    public String helloStringJson() {
        return "{\"name\":\"르세라핌\",\"age\":20}";
    }

    @ResponseBody
    @GetMapping("/json/class")
    public com.sparta.springmvc.Star helloJson() {
        return new com.sparta.springmvc.Star("르세라핌", 20);
    }
}