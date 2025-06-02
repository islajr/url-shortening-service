package org.project.urlshorteningservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    public String home() {

        return "home";
    }


    public String generateURL() {

        return "generateurl";
    }


    public String retrieveURL() {

        return "retrieveurl";
    }


    public String updateURL() {

        return "updateurl";
    }


    public String deleteURL() {

        return "deleteurl";
    }


    public String getURLStats() {

        return "geturlstats";
    }

}
