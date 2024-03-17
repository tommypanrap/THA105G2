package com.fitanywhere.opinion.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.user.model.UserJpaRepository;
import com.fitanywhere.user.model.UserVO;

@Service
public class OpinionService {

	@Autowired
	private OpinionJpaRepository opinionJpaRepository;

	@Autowired
	private UserJpaRepository userJpaRepository;	

	// 前台新增意見
	@Transactional
	public boolean isNewUserOpinionAdded(OpinionNewCreateDTO dto) {
		
		    Optional<UserVO> optionalUser = userJpaRepository.findById(dto.getuId());
		    if (!optionalUser.isPresent()) {
		        System.out.println("User not found");
		        return false; // 如果找不到User，返回false
		    }
		    UserVO user = optionalUser.get();
		    System.out.println(user.getuMail());

		    OpinionVO opinion = new OpinionVO();
		    opinion.setOpTitle(dto.getOpTitle());
		    opinion.setOpContent(dto.getOpContent());
		    opinion.setOpContent(dto.getOpContent());
		    opinion.setOpSendTime(Timestamp.valueOf(LocalDateTime.now()));
		    opinion.setOpStatus(0);	// "0"表示未處理	    
		    opinion.setUser(user);

		    try {
		        opinionJpaRepository.save(opinion);
		        return true; // 寫入成功
		    } catch (Exception e) {
		        System.out.println("寫入意見過程出現異常!");
		        e.printStackTrace();
		        return false;  // 寫入失敗
		    }
	}	

	// 後台分頁讀取
	@Transactional(readOnly = true)
	public Page<OpinionAllDataDTO> getAllOpinionsWithUsers(Pageable pageable) {
		return opinionJpaRepository.findAllOpinionsWithUser(pageable);
	}

	// 後台回覆處理
	@Transactional
	public OpinionAddReplyDTO updateOpinionData(OpinionAddReplyDTO opinionDTO) {
		int updatedCount = opinionJpaRepository.updateOpinionReplyContentAndStatusById(opinionDTO.getOpId(),
				opinionDTO.getOpReplyContent());
		if (updatedCount > 0) {
			// 更新成功
			opinionDTO.setOpReplyTime(LocalDateTime.now()); // 寫入當下時間
			return opinionDTO;
		} else {
			// 更新失败
			System.out.println("後台回覆會員意見流程異常!");
			return null;
		}
	}
}
