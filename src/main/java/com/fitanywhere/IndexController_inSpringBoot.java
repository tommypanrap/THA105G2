package com.fitanywhere;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import com.fitanywhere.order.model.OrderService;
import com.fitanywhere.order.model.OrderVO;

@Controller
public class IndexController_inSpringBoot {

    @Autowired
    OrderService orderSvc;

    @Value("${welcome.message}")
    private String message;

    private List<String> myList = Arrays.asList("Spring Boot Quickstart 官網 : https://start.spring.io", "IDE 開發工具",
            "直接使用(匯入)官方的 Maven Spring-Boot-demo Project + pom.xml",
            "直接使用官方現成的 @SpringBootApplication + SpringBootServletInitializer 組態檔",
            "依賴注入(DI) HikariDataSource (官方建議的連線池)", "Thymeleaf",
            "Java WebApp (<font color=red>快速完成 Spring Boot Web MVC</font>)");

    @GetMapping("/")
    public String indel(Model model) {
        model.addAttribute("message", message);
        model.addAttribute("myList", myList);

        return "index";
    }

    @GetMapping("/hello")
    public String indexWithParam(@RequestParam(name = "name", required = false, defaultValue = "") String name,
                                 Model model) {

        model.addAttribute("message", name);

        return "index";
    }
    // =========== 以下第57~62行是提供給
    // /src/main/resources/templates/back-end/emp/select_page.html 與 listAllEmp.html
    // 要使用的資料 ===================

    @GetMapping("/order/select_page")
    public String select_page(Model model) {
        return "back-end/order/select_page";
    }

    @GetMapping("/order/listAllOrder")
    public String listAllOrder(Model model) {
        return "back-end/order/listAllOrder";
    }

    @ModelAttribute("orderListData")
    protected List<OrderVO> referenceListData(Model model) {

        List<OrderVO> list = orderSvc.getAll();
        return list;
    }



}
