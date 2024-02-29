package com.fitanywhere.ann.model;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service("annService")
public class AnnService {

	@Autowired
	AnnRepository repository;

	public void addAnn(AnnVO annVO) {
		repository.save(annVO);
	}

	public void updateAnn(AnnVO annVO) {
		repository.save(annVO);
	}

	public void deleteAnn(Integer anId) {
		if (repository.existsById(anId))
			repository.deleteByAnid(anId);
//		    repository.deleteById(empno);
	}

	public AnnVO getOneAnn(Integer anId) {
		Optional<AnnVO> optional = repository.findById(anId);
//		return optional.get();
		return optional.orElse(null);  // public T orElse(T other) : 如果值存在就回傳其值，否則回傳other的值
	}

	public List<AnnVO> getAll() {
		return repository.findAll();
	}

}
