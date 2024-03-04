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

	public void updateCoach(CoachVO coachVO) {
		repository.save(coachVO);
	}

	public void deleteCoach(Integer cId) {
		if (repository.existsById(cId))
			repository.deleteByCid(cId);
//		    repository.deleteById(empno);
	}

	public CoachVO getOneCoach(Integer cId) {
		Optional<CoachVO> optional = repository.findById(cId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<CoachVO> getAll() {
		return repository.findAll();
	}

}
