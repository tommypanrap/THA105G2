package com.fitanywhere.opinion.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class OpinionController {

	@GetMapping("/backend_opinion")
	public String backend_opinion(Model model)  {
        return "back-end/backend_opinion"; 
    }
	
}
