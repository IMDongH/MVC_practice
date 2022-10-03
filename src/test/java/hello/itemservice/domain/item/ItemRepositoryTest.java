package hello.itemservice.domain.item;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

public class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach//테스트 끝날때마다 저장소 초기화
    void afterEach(){
        itemRepository.clearStore();
    }

    @Test
    void save(){
        //given
        Item item = new Item("itemA",10000,10);
        //when
        Item saveItem = itemRepository.save(item);
        //then
        Item findItem = itemRepository.findById(saveItem.getId());

        assertThat(findItem).isEqualTo(saveItem);
    }


    @Test
    void findAll(){
        //given
        Item item1 = new Item("itemA",10000,10);
        Item item2 = new Item("itemB",10000,10);

        itemRepository.save(item1);
        itemRepository.save(item2);
        //when
        List<Item> itemList = itemRepository.findAll();
        //then
        assertThat(itemList.size()).isEqualTo(2);
        assertThat(itemList).contains(item1,item2);
    }
    @Test
    void updateItem(){
        //given
        Item item = new Item("itemA",10000,10);
        Item savedItem = itemRepository.save(item);
        Long id = savedItem.getId();
        //when
        Item updateItem = new Item("itemB",10000,10);
        itemRepository.update(id, updateItem);
        //then
        Item findItem = itemRepository.findById(id);
        assertThat(findItem.getItemName()).isEqualTo(updateItem.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateItem.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateItem.getQuantity());


    }

}
