package com.fitanywhere.course.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("courseService")
public class CourseService {

	@Autowired
	CourseRepository repository;

	@Autowired
	CoursePagingRepository pagingRepository;

	// 獲取所有課程DTO
	public Page<CoursesDTO> getAllCourses(Pageable pageable){
	return  pagingRepository.findAllProjectedBy(pageable);
	}

	public Integer addCourse(CourseVO courseVO) {
		CourseVO savedCourse = repository.save(courseVO);
		return savedCourse.getCrId();
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
	
	public Integer getCourseCount(Integer uId) {
		return repository.getCourseCount(uId);
	}
	
	public Integer getStarCount(Integer uId) {
		return repository.getTotalStarCount(uId);
	}
	
	public List<CourseVO> getAllCourseByuId(Integer uId){
		return repository.getAllcourseByuId(uId);
	}
	
	public List<CourseStatus0DTO> getCourseByStatus0(Integer uId){
		List<Object[]> results = repository.getCourseByStatus0(uId);
        List<CourseStatus0DTO> CourseStatus0DTO = new ArrayList<>();
        for (Object[] result : results) {
            CourseStatus0DTO dto = new CourseStatus0DTO();
            dto.setCrClass((String) result[0]);
            dto.setCrTitle((String) result[1]);
            dto.setCrCover((byte[]) result[2]);
            dto.setCrPrice((Integer) result[3]);
            dto.setCrId((Integer) result[4]);
            CourseStatus0DTO.add(dto);
        }
        return CourseStatus0DTO;
	}
	
	public List<CourseStatus1DTO> getCourseByStatus1(Integer uId){
		List<Object[]> results = repository.getCourseByStatus1(uId);
		 List<CourseStatus1DTO> CourseStatus1DTO = new ArrayList<>();
		 for (Object[] result : results) {
	            CourseStatus1DTO dto = new CourseStatus1DTO();
	            dto.setCrTotStar((Integer) result[0]);
	            dto.setCrCmQuan((Integer) result[1]);
	            dto.setCrClass((String) result[2]);
	            dto.setCrTitle((String) result[3]);
	            dto.setCrCover((byte[]) result[4]);
	            dto.setCrPrice((Integer) result[5]);
	            CourseStatus1DTO.add(dto);
	        }
	        return CourseStatus1DTO;
	}
	
	public List<CourseStatus2DTO> getCourseByStatus2(Integer uId){
		List<Object[]> results = repository.getCourseByStatus2(uId);
        List<CourseStatus2DTO> CourseStatus2DTO = new ArrayList<>();
        for (Object[] result : results) {
            CourseStatus2DTO dto = new CourseStatus2DTO();
            dto.setCrClass((String) result[0]);
            dto.setCrTitle((String) result[1]);
            dto.setCrCover((byte[]) result[2]);
            dto.setCrPrice((Integer) result[3]);
            CourseStatus2DTO.add(dto);
        }
        return CourseStatus2DTO;
	}

	// 透過crId取cr cover
	@Transactional(readOnly = true)
	public CourseCrCoverDTO getCourseCrCoverById(Integer crId){
		return  repository.findCourseCrCoverById(crId);
	}

	// 用uId抓相關課程
	@Transactional(readOnly = true)
	public List<CourseVO> getCourseByUId(Integer uId){
		return repository.getCourseByUId(uId);
	}
	
	// Tommy
	public List<CourseVO> getSixCourses() {
		
		Pageable firstPageWithSixCourses = PageRequest.of(0, 6);
		Page<CourseVO> courses = repository.findSixCourses(firstPageWithSixCourses);
		List<CourseVO> courseListSix = courses.getContent();
		
		return courseListSix;
	}

}
