package com.fitanywhere;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.adcarousel.model.AdCarouselService;
import com.fitanywhere.adcarousel.model.AdCarouselVO;
import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;

import java.io.IOException;
import java.util.*;

import javax.validation.Valid;

@Controller
public class IndexController_inSpringBoot {
	
	@Autowired
	CourseService courseSvc;
	
	@Autowired
    private AdCarouselService AdCarSvc;
	
	
	
	@GetMapping("/")
    public String index(@Valid CourseVO courseVO,Model model)  throws IOException {
		System.out.println(courseSvc.getAll());
		List<CourseVO> list = courseSvc.getAll();
		model.addAttribute("courseListData", list);
		System.out.println("list:"+list);
        return "index"; //view
        
    }
	
//	�ШD�i�Ө�s�s�i�P�_�O�_�ŦX�n�D
	@ModelAttribute("ads")
	public List<AdCarouselVO> populateAds() {
		return AdCarSvc.getBaseHomePageAd();
	}

}
