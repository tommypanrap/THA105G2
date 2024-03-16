package com.fitanywhere.socialpost.controller;


import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.socialpost.model.SocialPostSearchDTO;
import com.fitanywhere.user.model.UserHeadshotOnlyDTO;
import com.fitanywhere.user.model.UserService;
import com.fitanywhere.user.model.UserVO;

@RestController
@RequestMapping("/socialpost")
public class SocialPostRestController {
	
	@Autowired
	UserService userSvc;
	
	@PostMapping("search_social_member")
    public ResponseEntity<Map<String, Object>> search_social_member(@RequestBody SocialPostSearchDTO searchDTO) throws IOException {
        System.out.println("有進來");
		List<UserVO> matchingUsers = userSvc.searchUsersByNickname(searchDTO.getSearchValue(), searchDTO.getuId());

        List<Map<String, Object>> usersInfo = new ArrayList<>();
        for (UserVO user : matchingUsers) {
            UserHeadshotOnlyDTO headshotDTO = userSvc.getUserHeadshotDTOById(user.getuId());
            System.out.println("headshotDTO:"+headshotDTO);
            Map<String, Object> userInfo = new HashMap<>();
            userInfo.put("nickname", user.getuNickname()); 
            System.out.println(user.getuNickname());
            if (headshotDTO != null && headshotDTO.getuHeadshot() != null) {
                byte[] photoBytes = headshotDTO.getuHeadshot();
//                System.out.println("headshotDTO:"+photoBytes);
                String base64Encoded = Base64.getEncoder().encodeToString(photoBytes);
                userInfo.put("headshot", base64Encoded);
                

            } else {
                userInfo.put("headshot", null);
            }

            usersInfo.add(userInfo);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("users", usersInfo);

        return ResponseEntity.ok(response);
    }
	
	
	
}


