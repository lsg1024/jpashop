package jpabook.jpashop.api;

import jpabook.jpashop.domin.Address;
import jpabook.jpashop.domin.Order;
import jpabook.jpashop.domin.OrderStatus;
import jpabook.jpashop.repository.OrderRepository;
import jpabook.jpashop.repository.OrderSearch;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryDto;
import jpabook.jpashop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {

    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

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

    @GetMapping("/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {

        List<SimpleOrderDto> result = orderRepository.findAllByString(new OrderSearch()).stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return result;
    }

    // 모든 쿼리를 패치 조인으로 한번에 호출 -> 다른 부분에서도 활용할 수 있는 재사용성이 있다
    @GetMapping("/api/v3/simple-orders")
    public List<SimpleOrderDto> orderV3() {

        List<SimpleOrderDto> result = orderRepository.findAllWithMemberDelivery().stream()
                .map(SimpleOrderDto::new)
                .collect(Collectors.toList());

        return result;
    }

    // 필요한 부분만 쿼리로 호출 -> 재사용이 어려운 점이 있다
    // Repository는 순수한 엔티티를 조회하는데 사용해야 하므로 쿼리 DTO 부분을 분리해주었다
    @GetMapping("/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> ordersV4() {
        return orderSimpleQueryRepository.findOrderDtos();
    }

    @Data
    static class SimpleOrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;

        public SimpleOrderDto(Order order) {
            orderId = order.getId();
            name = order.getMember().getName();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
            address = order.getMember().getAddress();
        }
    }

}
