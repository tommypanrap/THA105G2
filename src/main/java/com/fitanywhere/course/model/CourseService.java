package com.fitanywhere.course.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("courseService")
public class CourseService {

	@Autowired
	CourseRepository repository;

	public void addCourse(CourseVO courseVO) {
		repository.save(courseVO);
	}

	public void updateCourse(CourseVO courseVO) {
		repository.save(courseVO);
	}

	public void deleteCourse(Integer crId) {
		if (repository.existsById(crId))
			repository.deleteByCrid(crId);
//		    repository.deleteById(empno);
	}

	public CourseVO getOneCourse(Integer crId) {
		Optional<CourseVO> optional = repository.findById(crId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<CourseVO> getAll() {
		return repository.findAll();
	}

}
