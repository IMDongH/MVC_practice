package hello.itemservice.domain.item;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

//@Data
// 위험함 게터 세터 이외에도 많은 것들을 제공하기 때문에 , DTO 에서는 사용해도 된다.

@Getter
@Setter
public class Item {
    private Long id;
    private String itemName;
    private Integer price;
    private Integer quantity;
    //Integer 은 null 값이 들어갈 수 있음 int 는 x

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
