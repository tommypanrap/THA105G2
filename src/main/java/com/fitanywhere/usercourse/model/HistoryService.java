package com.fitanywhere.usercourse.model;

import com.fitanywhere.course.model.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    @Autowired
    private HistoryServiceRepository repository;

    public void recordView(Integer uId, Integer crId){
        repository.addCourseHistory(uId, crId);
    }

    public List<UserCourseDTO> getView(String uId){
        return repository.getCourseHistory(uId);
    }

}
