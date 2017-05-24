package ru.lightstar.urlshort.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Controller used to show demo page.
 *
 * @author LightStar
 * @since 0.0.1
 */
@Controller
public class DemoController {

    @RequestMapping("/demo")
    public String help() {
        return "demo";
    }
}