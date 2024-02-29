package com.fitanywhere.user.model;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitanywhere.util.EncryptionUtil;

@Service
public class UserService {

    @Autowired
    private UserJpaRepository userJpaRepository;

    // 檢查信箱是否註冊
    public boolean isEmailRegistered(String uMail) {
        UserVO user = userJpaRepository.findByUMail(uMail);
        return user != null;
    }

    // 核對登入密碼是否正確
    public UserVO userLogin(String uMail, String inputPassword) {
        UserVO user = userJpaRepository.findByUMail(uMail);
        if (user != null && EncryptionUtil.checkPassword(inputPassword, user.getuPassword())) {
            return user; // 登入成功 返回會員資料
        }
        return null; // 登錄失敗
    }
    
    public UserVO getUser(Integer uId){
    	
    	UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
    }
}
