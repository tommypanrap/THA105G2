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

@RestController
@RequestMapping("/backend_userlist_api")
public class UserlistRestController {

	@Autowired
	private UserService userService;

	// 有空再把數據處理封裝到Service
	@PostMapping("/manual_change_uStatus")
	public ResponseEntity<?> manualChangeUserStatus(@RequestBody Map<String, String> requestBody) {
		try {
			Integer uId = Integer.parseInt(requestBody.get("uId"));
			Integer selectedValue = Integer.parseInt(requestBody.get("value"));
			if (userService.updateUserStatusWithMapping(uId, selectedValue)) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
			}
		} catch (NumberFormatException e) {
			System.out.println("參數轉Integer失敗!");
			e.printStackTrace();
			return ResponseEntity.badRequest().body("參數轉Integer錯誤!");
		} catch (IllegalArgumentException e) {
			System.out.println("未定義的參數!");
			e.printStackTrace();
			return ResponseEntity.badRequest().body("未定義的參數!");
		} catch (Exception e) {
			System.out.println("內部處理錯誤!");
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // 500
		}
	}
}

