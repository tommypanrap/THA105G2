package com.fitanywhere.userlist.model;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fitanywhere.coach.model.CoachService;
import com.fitanywhere.user.model.UserJpaRepository;
import com.fitanywhere.user.model.UserService;

@Service
public class UserlistService {
	@Autowired
	private UserJpaRepository userJpaRepository;

	@Autowired
	private CoachService coachService;

	// 取得所有User
	public Page<UserlistAllDataDTO> getAllUsersWithCoachId(Pageable pageable) {
		Page<UserlistAllDataDTO> page = userJpaRepository.findAllUsersWithoutHeadshot(pageable);

		page.forEach(new Consumer<UserlistAllDataDTO>() {
			@Override
			public void accept(UserlistAllDataDTO dto) {
				Integer cId = coachService.getCoachIdById(dto.getuId()); // 填充cId
				dto.setcId(cId);
			}
		});

		return page;
	}

	// 依照uStatus取回特定User
	public Page<UserlistAllDataDTO> getAllUsersByStatus(List<Integer> status, Pageable pageable) {
		Page<UserlistAllDataDTO> page = userJpaRepository.findAllUsersByStatus(status, pageable);

		page.forEach(new Consumer<UserlistAllDataDTO>() {
			@Override
			public void accept(UserlistAllDataDTO dto) {
				Integer cId = coachService.getCoachIdById(dto.getuId()); // 填充cId
				dto.setcId(cId);
			}
		});

		return page;
	}
	
	// 依照Mail查詢會員	
	public UserlistAllDataDTO findSingleUserByEmail(String uMail) {
	    // 使用 Optional 來處理可能為空的情況
	    Optional<UserlistAllDataDTO> userOpt = userJpaRepository.findSingleUserDTOByMail(uMail);
	    
	    // 檢查 Optional 是否包含值
	    if (userOpt.isPresent()) {
	        // 如果存在，獲取 UserlistAllDataDTO 物件
	        UserlistAllDataDTO userDTO = userOpt.get();
	        
	        // 填充 cId
	        Integer cId = coachService.getCoachIdById(userDTO.getuId());
	        userDTO.setcId(cId);
	        
	        // 返回填充後的 UserlistAllDataDTO 物件
	        return userDTO;
	    } else {
	        // 如果不存在，返回 null 或者拋出異常，具體取決於您的需求
	        return null;
	    }
	}


}
