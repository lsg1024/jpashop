package jpabook.jpashop.api;

import jpabook.jpashop.domin.Order;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;

    // 현재 상태에서는 프록시 객체에는 비어있고 DB 데이터는 order 만 불러오기 때문에 호출되지 않는다
    // 또한 Hibernate 를 통해 호출에 성공하더라도 lazy load 값은 null 처리되서 나온다
    @GetMapping("/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> all = orderRepository.findAllByString(new OrderSearch());
        for (Order order : all) {
            order.getMember().getName(); // Lazy 강제 초기화
            order.getDelivery().getAddress(); // Lazy 강제 초기화

        }
        return all;
    }

}