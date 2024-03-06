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
        UserVO user = userJpaRepository.findByuMail(uMail);
        return user != null;
    }
    
    // 檢查信箱是否註冊
    public boolean isNicknameRegistered(String uNickname) {
        UserVO user = userJpaRepository.findByuNickname(uNickname);
        return user != null;
    }
    
    // 檢查信箱是否註冊
    public boolean isPhoneRegistered(String uPhone) {
        UserVO user = userJpaRepository.findByuPhone(uPhone);
        return user != null;
    }

    // 核對登入密碼是否正確
    public UserVO userLogin(String uMail, String inputPassword) {
        UserVO user = userJpaRepository.findByuMail(uMail);
        if (user != null && EncryptionUtil.checkPassword(inputPassword, user.getuPassword())) {
            return user; // 登入成功 返回會員資料
        }
        return null; // 登錄失敗
    }
    
    //	返回ID對應的整筆MySQL資料
    public UserVO getUserDataByID(Integer uId){
    	
    	UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
    }
    
    
//	Andy
    public UserVO getUser(Integer uId){
    	
    	UserVO userVO = userJpaRepository.findByuId(uId);
		return userVO;
    }
    
    //andy 單取出user大頭照
    public byte[] getUserHeadshot(Integer uId) {
    	byte[] uHeadshot = userJpaRepository.getUserHeadshotByUserId(uId);
    	return uHeadshot;
    }

 // Tommy
 	public List<UserVO> getAll() {
 		return userJpaRepository.findAll();
 	}
 	
 	public void updateUserProfile(UserVO userVO) {
 		userJpaRepository.save(userVO);
 	}
}
