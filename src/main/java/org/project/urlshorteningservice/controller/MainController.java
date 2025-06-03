package org.project.urlshorteningservice.controller;

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
