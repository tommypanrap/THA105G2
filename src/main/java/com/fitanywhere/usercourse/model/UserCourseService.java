package com.fitanywhere.usercourse.model;

import com.fitanywhere.course.model.CourseVO;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service("usercourseService")
public class UserCourseService {

	@Autowired
	UserCourseRepository repository;


	public void updateUserCourse(UserCourseVO usercourseVO) {
		repository.save(usercourseVO);
	}

	public void deleteUserCourse(Integer usCrId) {
		if (repository.existsById(usCrId))
			repository.deleteByusCrId(usCrId);
	}

	public UserCourseVO getOneUserCourse(Integer usCrId) {
		Optional<UserCourseVO> optional = repository.findById(usCrId);
		return optional.orElse(null);
	}

	public List<UserCourseVO> getAll() {
		return repository.findAll();
	}

	public Integer getStudentCount(Integer uId) {
		return repository.getStudentCount(uId);
	}

	public String getNickname(Integer uId){
		return repository.getNickname(uId);
	}
}
