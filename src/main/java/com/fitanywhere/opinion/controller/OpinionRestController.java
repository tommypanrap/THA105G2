package com.fitanywhere.opinion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.opinion.model.OpinionAddReplyDTO;
import com.fitanywhere.opinion.model.OpinionService;

@RestController
@RequestMapping("/opinion_api")
public class OpinionRestController {
	
	@Autowired
	private OpinionService opinionService;
	
	@PostMapping("/update_opinion_reply")
	public ResponseEntity<?> updateOpinionReply(@RequestBody OpinionAddReplyDTO opinionDTO){
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
