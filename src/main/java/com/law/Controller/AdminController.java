package com.law.Controller;


import com.law.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {



    @RequestMapping(value = {"/admin/dashboard"}, method = RequestMethod.GET)
    public String adminHome() {
        return "admin/dashboard";
    }
}

