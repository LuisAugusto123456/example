package pe.indigital.tunki.core.example.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Value("${appName}")
    private String appName;

    @Value("${environment}")
    private String environment;

    @GetMapping("/")
    public String homePage(Model model) {
        model.addAttribute("appName", this.appName );
        model.addAttribute("environment", this.environment);
        return "home/index";
    }
}
