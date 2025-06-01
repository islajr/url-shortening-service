package org.project.urlshorteningservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/")
    public String home() {

        return "home";
    }

    @RequestMapping("/templates/generateurl.html")
    public String generateURL() {

        return "generateurl";
    }

    @RequestMapping("/templates/retrieveurl.html")
    public String retrieveURL() {

        return "retrieveurl";
    }

    @RequestMapping("/templates/updateurl.html")
    public String updateURL() {

        return "updateurl";
    }

    @RequestMapping("/templates/deleteurl.html")
    public String deleteURL() {

        return "deleteurl";
    }

    @RequestMapping("/templates/geturlstats.html")
    public String getURLStats() {

        return "geturlstats";
    }

}
