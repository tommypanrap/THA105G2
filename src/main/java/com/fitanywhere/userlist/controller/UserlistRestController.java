package com.fitanywhere.userlist.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitanywhere.user.model.UserService;
import com.fitanywhere.userlist.model.UserlistService;

@RestController
@RequestMapping("/backend_userlist_api")
public class UserlistRestController {
	@Autowired
	private UserlistService userlistService;
	@Autowired
	private UserService userService;

	// 有空再把數據處理封裝到Service
	@PostMapping("/manual_change_uStatus")
	public ResponseEntity<?> manualChangeUserStatus(@RequestBody Map<String, String> requestBody) {
		
		String stringId = requestBody.get("uId");
		String stringValue = requestBody.get("value");
		Integer uId = null;
		Integer selectedValue = null;
		Integer newStatus = null;
		
		try {
			uId = Integer.parseInt(stringId);
			selectedValue = Integer.parseInt(stringValue);
			// 轉換Integer成功
		} catch (NumberFormatException e) {
			System.out.println("stringId轉換Interger失敗!");
		}

		if (selectedValue != null) {
			switch (selectedValue) {
			case 1:
				newStatus = 0; // 站方手動啟動
				break;
			case 2:
				newStatus = 1; // 站方手動BAN
				break;
			case 3:
				newStatus = 3; // 站方手動註銷
				break;			
			default:
				System.out.println("未設定的stringValue: " + stringValue);
				break;
			}
		}
		
		if (userService.updateUserStatus(uId, newStatus)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); //500 內部處理錯誤
		}	
	}

}
