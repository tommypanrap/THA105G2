package com.fitanywhere.opinion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.opinion.model.OpinionAllDataDTO;
import com.fitanywhere.opinion.model.OpinionService;


@Controller
public class OpinionController {
	
	@Autowired
	private OpinionService opinionService;
	
	@GetMapping("/frontend_opinion")
	public String frontEndOpinion(Model model)  {
        return "front-end/opinion/student_opinion"; 
    }
	
	@GetMapping("/backend_opinion")
	public String listAllOpinions (Model model, @RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size) {
		
		Pageable pageable = PageRequest.of(page, size);
        Page<OpinionAllDataDTO> opinionPage = opinionService.getAllOpinionsWithUsers(pageable);		
        

		model.addAttribute("opinionPage", opinionPage);
		return "back-end/backend_opinion";
	}


}

