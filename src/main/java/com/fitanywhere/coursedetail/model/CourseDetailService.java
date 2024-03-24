package com.fitanywhere.coursedetail.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("coursedetailService")
public class CourseDetailService {

	@Autowired
	CourseDetailRepository repository;

	public boolean addCourseDetail(CourseDetailVO coursedetailVO) {
		try{
			repository.save(coursedetailVO);
			return true;
		}catch(Exception e) {
 	        e.printStackTrace();
 	        return false; // 保存失败，返回 false
 	    }
	}

	public void updateCourseDetail(CourseDetailVO coursedetailVO) {
		repository.save(coursedetailVO);
	}

	public List<CourseDetailVO> getCourseDetailByCrId(Integer crId){
		return repository.findVideosByCourseId(crId);
	}

//	public CourseVO getOneCourse(Integer crId) {
//		Optional<CourseVO> optional = repository.findById(crId);
////		return optional.get();
//		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
//	}

	public List<CourseDetailVO> getAll() {
		return repository.findAll();
	}
	
	public List<String> getCourseVideoPathByCourseId(Integer crId){
		return repository.getCourseVideoPathByCourseId(crId);
	}
	
	public List<String> getCourseSaleVideoPathByCourseId(Integer crId){
		return repository.getCourseSaleVideoPathByCourseId(crId);
	}
	
//	public Integer getCourseCount(Integer uId) {
//		return repository.getCourseCount(uId);
//	}
	
//	public List<CourseVO> getAllCourseByuId(Integer uId){
//		return repository.getAllcourseByuId(uId);
//	}

}
