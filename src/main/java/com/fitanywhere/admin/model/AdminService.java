package com.fitanywhere.admin.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fitanywhere.service.PasswordEncryptionService;

@Service
public class AdminService {

	@Autowired
	private AdminJpaRepository adminJpaRepository;

	// 登入-登入資料預查
	@Transactional(readOnly = true)
	public boolean isAdminIdCorrect(Integer adminId) {
		return adminJpaRepository.existsByAdminId(adminId);
	}

	@Transactional(readOnly = true)
	public boolean isAdminNameCorrect(String adminName) {
		// 若MySQL大小寫不敏感則不敏感 若大小寫敏感則敏感
		return adminJpaRepository.existsByAdminName(adminName);
	}

	// 登入-登入驗證流程
	@Transactional(readOnly = true)
	public int adminLoginVerification(Integer adminId, String inputName, String inputPassword) {
		try {
			String savedPassword = adminJpaRepository.findOnlyPasswordByAdminId(adminId);
			String savedName = adminJpaRepository.findOnlyNameByAdminId(adminId);
			
			// 放寬到AdminId大小寫不敏感
			if (PasswordEncryptionService.checkPassword(inputPassword, savedPassword)
					&& inputName.equalsIgnoreCase(savedName)) {
				// 登入成功
				return 0;
			} else {
				// 登入失敗
				return 1;
			}
		} catch (Exception e) {
			// 登入失敗
			System.out.println("系統處理異常");
			return 2;
		}
	}

	// 登入-權限查詢
	@Transactional(readOnly = true)
	public int getAdminLevel(Integer adminId) {
		return adminJpaRepository.findOnlyLevelByAdminId(adminId);
	}

}
