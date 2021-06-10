package de.tuberlin.tkn.lit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

    @Controller
    @CrossOrigin
    public class MainController {

        @RequestMapping(value = "/", method = RequestMethod.GET)
        public String index(Model model) {
            // this attribute will be available in the view index.html as a thymeleaf variable
            model.addAttribute("eventName", "FIFA 2018");
            // this just means render index.html from static/ area
            return "index";
        }
    }
