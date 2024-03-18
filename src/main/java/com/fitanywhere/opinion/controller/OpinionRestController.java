package com.fitanywhere.opinion.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.opinion.model.OpinionAddReplyDTO;
import com.fitanywhere.opinion.model.OpinionNewCreateDTO;
import com.fitanywhere.opinion.model.OpinionService;

@RestController
@RequestMapping("/opinion_api")
public class OpinionRestController {

	@Autowired
	private OpinionService opinionService;

	// 前台會員撰寫意見
	@PostMapping("/user_create_opinion")
	public ResponseEntity<?> createUserOpinion(@RequestBody OpinionNewCreateDTO opinionDTO,
			HttpServletRequest request) {
		// 從Session讀取uId並寫入DTO
		HttpSession session = request.getSession(false);
		if (session != null && session.getAttribute("loginStatus") != null) {
			Integer uId = (Integer) session.getAttribute("uId");
			opinionDTO.setuId(uId);
		}
		// 處理寫入
		if (opinionService.isNewUserOpinionAdded(opinionDTO)) {
			// 新增成功
			return ResponseEntity.ok().build(); // 200
		} else {
			// 新增失敗
			return ResponseEntity.badRequest().build(); // 500
		}
	}

	// 後台站方回覆會員意見
	@PostMapping("/update_opinion_reply")
	public ResponseEntity<?> updateOpinionReply(@RequestBody OpinionAddReplyDTO opinionDTO) {
		
		// 將封裝成DTO的接收資料呼叫Service處理
		OpinionAddReplyDTO updatedOpinionDTO = opinionService.updateOpinionData(opinionDTO);

		if (updatedOpinionDTO != null) {
			// 更新成功，返回更新後的 DTO
			return ResponseEntity.ok(updatedOpinionDTO);
		} else {
			// 更新失敗或找不到對應的意見資料，返回錯誤訊息
			return ResponseEntity.badRequest().body("伺服器錯誤, 請稍後再試!");
		}
	}

}
