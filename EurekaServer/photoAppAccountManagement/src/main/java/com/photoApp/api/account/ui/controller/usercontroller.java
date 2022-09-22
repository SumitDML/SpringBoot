package photoAppAccountManagement.src.main.java.com.photoApp.api.account.ui.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account")
public class usercontroller {
    @GetMapping("/status/check")
    public String status(){
        return "Working!!";
    }


}
