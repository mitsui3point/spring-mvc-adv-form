package hello.itemservice.domain.item;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ItemRepositoryTest {

    ItemRepository itemRepository = new ItemRepository();

    @AfterEach
    void afterEach() {
        itemRepository.clearStore();
    }

    @Test
    void save() {
        //given
        Item item = new Item("itemA", 10000, 10);

        //when
        Item savedItem = itemRepository.save(item);

        //then
        Item findItem = itemRepository.findById(item.getId());
        assertThat(findItem).isEqualTo(savedItem);
    }

    @Test
    void findAll() {
        //given
        Item item1 = new Item("item1", 10000, 10);
        Item item2 = new Item("item2", 20000, 20);

        itemRepository.save(item1);
        itemRepository.save(item2);

        //when
        List<Item> result = itemRepository.findAll();

        //then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(item1, item2);
    }

    @Test
    void updateItem() {
        //given
        Item item = new Item("item1", 10000, 10);

        Item savedItem = itemRepository.save(item);
        Long itemId = savedItem.getId();

        Boolean isOpen = true;
        List<String> regions = Arrays.asList("KOREA", "USA", "UK", "SPAIN");
        ItemType food = ItemType.FOOD;
        DeliveryCode deliveryFast = new DeliveryCode("FAST", "빠른 배송");

        //when

        Item updateParam = new Item("item2", 20000, 30);
        updateParam.setOpen(isOpen);
        updateParam.setRegions(regions);
        updateParam.setItemType(food);
        updateParam.setDeliveryCode(deliveryFast.getCode());
        itemRepository.update(itemId, updateParam);

        Item findItem = itemRepository.findById(itemId);

        //then
        assertThat(findItem.getItemName()).isEqualTo(updateParam.getItemName());
        assertThat(findItem.getPrice()).isEqualTo(updateParam.getPrice());
        assertThat(findItem.getQuantity()).isEqualTo(updateParam.getQuantity());

        assertThat(findItem.getOpen()).isEqualTo(updateParam.getOpen());
        assertThat(findItem.getRegions()).isEqualTo(updateParam.getRegions());
        assertThat(findItem.getItemType()).isEqualTo(updateParam.getItemType());
        assertThat(findItem.getDeliveryCode()).isEqualTo(updateParam.getDeliveryCode());
    }
}
