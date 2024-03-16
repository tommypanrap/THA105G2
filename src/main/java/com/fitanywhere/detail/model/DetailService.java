package com.fitanywhere.detail.model;

import com.fitanywhere.course.model.CourseService;
import com.fitanywhere.course.model.CourseVO;
import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service("detailService")
public class DetailService {

    @Autowired
    DetailRepository repository;

    @Autowired
    OrderService orderSvc;

    @Autowired
    CourseService courseSvc;


    public void addDetail(DetailVO detailVO) {
        repository.save(detailVO);

    }

    public void deleteDetail(Integer deId) {
        if (repository.existsById(deId)) {
            repository.deleteById(deId);
        }

    }

    public void updateDetail(DetailVO detailVO) {
        repository.save(detailVO);

    }


    public List<DetailVO> getAll() {
        return repository.findAll();
    }

    public DetailVO getOneDetail(Integer deId) {

        return repository.findById(deId).orElse(null);
    }

    // 用訂單編號查找該訂單明細
    public List<DetailVO> getDetailsByOrderId(Integer odId) {


        return repository.findDetailsByOdId(odId);
    }

    //用訂單編號查找該訂單明細的details的crIds並獲取Course資訊
    public List<DetailDTO> getDetailDTOByOrderId(Integer odId) {
        List<Integer> crIdsByOdId = repository.findCrIdsByOdId(odId);
        List<DetailDTO> list = crIdsByOdId.stream().map(crId -> {
            CourseVO courseVO = courseSvc.getOneCourse(crId);
            return new DetailDTO(courseVO.getCrCover(), courseVO.getCrTitle(), courseVO.getCrPrice());
        }).collect(Collectors.toList());
        return list;
    }
}
