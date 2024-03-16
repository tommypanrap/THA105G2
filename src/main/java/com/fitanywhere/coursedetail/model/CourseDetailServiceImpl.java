package com.fitanywhere.coursedetail.model;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class CourseDetailServiceImpl {
    @Autowired
    private CourseDetailRepository repository;
//    @Autowired
    private SessionFactory sessionFactory;

    public void addCourseDetail(CourseDetailVO courseDetailVO){
        repository.save(courseDetailVO);
    }

    public void updateCourseDetail(CourseDetailVO courseDetailVO){
        repository.save(courseDetailVO);
    }

    public CourseDetailVO getOneCourse(Integer cdId){
        Optional<CourseDetailVO> optional = repository.findById(cdId);
        return optional.orElse(null);
    }

    public List<CourseDetailVO> getAll(){
        return repository.findAll();
    }

    // 獲取影片路徑
    public String getCourseVideoPath(Integer cdId) throws FileNotFoundException{
        String videoPath = repository.getCourseVideoPath(cdId);
        if(videoPath != null){
            return videoPath;
        }else{
            throw new FileNotFoundException("Video path not found for cdId: " + cdId);
        }
    }

    // 獲取影片路徑
    public  String getSaleVideoPath(Integer cdId) throws FileNotFoundException{
        String saleVideoPath = repository.getSaleVideoPath(cdId);
        if(saleVideoPath != null){
            return saleVideoPath;
        }else{
            throw new FileNotFoundException("sale video path not found for cdId: " + cdId);
        }
    }

    // 獲取多個單元影片
    public List<CourseDetailVO> findVideosByCourseId(Integer crId){
        return repository.findVideosByCourseId(crId);
    }

    public Integer getunitCount(Integer crId){
        try {
            return repository.getunitCount(crId);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


}
