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
		// 確認發起請求的會員資料是否存在
		Optional<UserVO> optionalUser = userJpaRepository.findById(dto.getuId());
		if (!optionalUser.isPresent()) {
			System.out.println("User not found");
			return false;
		}
		// 取得該會員VO實例
		UserVO user = optionalUser.get();
		// 新增意見VO並開始封裝必要資料
		OpinionVO opinion = new OpinionVO();
		opinion.setOpTitle(dto.getOpTitle());
		opinion.setOpContent(dto.getOpContent());
		opinion.setOpSendTime(Timestamp.valueOf(LocalDateTime.now())); // 伺服器當下時間
		opinion.setOpStatus(0); // "0"表示未處理
		opinion.setUser(user);

		try {
			opinionJpaRepository.save(opinion);
			return true; // 寫入成功
		} catch (Exception e) {
			System.out.println("寫入意見過程出現異常!");
			e.printStackTrace();
			return false; // 寫入失敗
		}
	}

	// 前台分頁讀取
	@Transactional(readOnly = true)
	public Page<OpinionAllDataDTO> getAllOpinionsWithSelectedUser(Pageable pageable, Integer uId) {
		return opinionJpaRepository.findAllOpinionsWithSelectedUser(pageable, uId);
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
			// 更新失敗
			System.out.println("後台回覆會員意見流程異常!");
			return null;
		}
	}
}
