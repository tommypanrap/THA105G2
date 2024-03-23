package com.fitanywhere.usercourse.model;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class HistoryServiceRepository {

    // StringRedisTemplate 繼承 RedisTemplate 配置了默認序列化
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    CourseService courseSvc;
    private static final String UserCourseKey = "user:course:view:";

    public void addCourseHistory(Integer uId, Integer crId){
        String UserCourseKey = "user:"+ uId +":course:view";

        redisTemplate.opsForZSet().add(UserCourseKey, crId.toString(),System.currentTimeMillis());
    }

    public List<UserCourseDTO> getCourseHistory(String uId){
        String UserCourseKey = "user:"+ uId +":course:view";

        Set<String> courseIds = redisTemplate.opsForZSet().reverseRange(UserCourseKey, 0 , -1);

        List<Integer> crId = courseIds.stream()
                .map(Integer::parseInt)
                .collect(Collectors.toList());

        List<UserCourseDTO> courseHistory = crId.stream()
                .map(courseSvc::getOneCourse)
                .filter(Objects::nonNull)
                .map(course -> new UserCourseDTO(
                        course.getCrId(),
                        course.getCrTitle(),
                        course.getCrCover(),
                        course.getCrPrice(),
                        course.getCrSubtitle()
                ))
                .collect(Collectors.toList());

        return courseHistory;
    }

}
