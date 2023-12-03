package jpabook.jpashop.domin;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    @JsonIgnore
    @OneToOne(mappedBy = "delivery", fetch = LAZY)
    private Order order;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING) // STRING and ORDINAL 두가지 상태가 존재하는데 ORDINAL 경우에는 숫자로 값을 표현해 중간에 새로운 값이 생기면 기존 값이 밀리는 현상이 생겨 오류를 유발하기에 절대 사용하면 안된다
    private DeliveryStatus status; // READY, COMP
}
