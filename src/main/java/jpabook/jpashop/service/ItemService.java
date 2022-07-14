package jpabook.jpashop.service;

import jpabook.jpashop.domain.item.Book;
import jpabook.jpashop.domain.item.Item;
import jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        itemRepository.save(item);
    }

    /**
     * 준영속 엔티티를 수정하는 방법 중 변경 감지 기능 사용
     * 영속성 컨텍스트에서 엔티티를 다시 조회한 후에 데이터를 수정하는 방법
     * 트랜잭션 안에서 엔티티를 다시 조회, 변경할 값 선택 -> 트랜잭션 커밋 시점에 변경 감지(Dirty Checking)가
     * 동작해서 데이터베이스에 UPDATE SQL 실행
     * 변경 감지 기능을 사용하면 원하는 속성만 선택해서 변경할 수 있지만, 병합을 시도하면 모든 속성이 변경된다.
     * 병합시 값이 없으면 null로 업데이트 할 위험도 있다.
     * 귀찮더라도 변경 감지 기능을 사용하는 것을 권장.
     */
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity) {
        Item findItem = itemRepository.findOne(itemId);
        /**
         * 넘길 파라미터가 많으면 서비스 계층에 Dto를 따로 만들어서 파라미터로 넘겨줘도 됨.
         */
        findItem.setName(name);
        findItem.setPrice(price);
        findItem.setStockQuantity(stockQuantity);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
