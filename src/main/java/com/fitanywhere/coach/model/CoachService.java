package com.fitanywhere.coach.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("coachService")
public class CoachService {

	@Autowired
	CoachRepository repository;

	public void addCoach(CoachVO coachVO) {
		repository.save(coachVO);
	}

	public boolean updateCoach(CoachVO coachVO) {
		try {
			repository.save(coachVO);
 	        return true; // 保存成功，返回 true
 	    } catch (Exception e) {
 	        e.printStackTrace();
 	        return false; // 保存失败，返回 false
 	    }
	}

	public void deleteCoach(Integer cId) {
		if (repository.existsById(cId))
			repository.deleteByCid(cId);
//		    repository.deleteById(empno);
	}

	public CoachVO getOneCoach(Integer uId) {
		CoachVO coachVO = repository.findCoachVOById(uId);
//		return optional.get();
		return coachVO;  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<CoachVO> getAll() {
		return repository.findAll();
	}

}
