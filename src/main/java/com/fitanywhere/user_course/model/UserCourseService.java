package com.fitanywhere.user_course.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("usercourseService")
public class UserCourseService {

	@Autowired
	UserCourseRepository repository;

	public Integer addUserCourse(UserCourseVO usercourseVO) {
		UserCourseVO savedUserCourse = repository.save(usercourseVO);
		return savedUserCourse.getCrId();
	}

	public void updateUserCourse(UserCourseVO usercourseVO) {
		repository.save(usercourseVO);
	}

	public void deleteUserCourse(Integer usCrId) {
		if (repository.existsById(usCrId))
			repository.deleteByusCrId(usCrId);
//		    repository.deleteById(empno);
	}

	public UserCourseVO getOneUserCourse(Integer usCrId) {
		Optional<UserCourseVO> optional = repository.findById(usCrId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<UserCourseVO> getAll() {
		return repository.findAll();
	}
	
	public Integer getStudentCount(Integer uId) {
		return repository.getStudentCount(uId);
	}

}
