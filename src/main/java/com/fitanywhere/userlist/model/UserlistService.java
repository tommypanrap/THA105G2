package com.fitanywhere.userlist.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.coach.model.CoachService;
import com.fitanywhere.user.model.UserJpaRepository;
import com.fitanywhere.user.model.UserService;

@Service
public class UserlistService {	
	@Autowired
	private UserJpaRepository userJpaRepository; 
	
	@Autowired
	private UserService userService;

	@Autowired
	private CoachService coachService;
	
	public List<UserlistAllDataDTO> getAllUsersWithCoachId() {
	    List<UserlistAllDataDTO> usersWithoutCoachId = userJpaRepository.findAllUsersWithoutHeadshot();

	    // 對每個DTO填充cId
	    for (int i = 0; i < usersWithoutCoachId.size(); i++) {
	        UserlistAllDataDTO dto = usersWithoutCoachId.get(i);
	        Integer cId = coachService.getCoachIdById(dto.getuId()); // 假設這是從CoachService獲取cStatus的方法
	        dto.setcId(cId);
	    }
	    return usersWithoutCoachId;
	}

	
	

}
