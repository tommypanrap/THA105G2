package com.fitanywhere.userlist.model;

import java.util.List;
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
	private UserService userService;

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

}
