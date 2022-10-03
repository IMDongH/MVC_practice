package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.PostConstruct;
import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

//    @Autowired// 생성자가 하나만 있으면 이줄도 생략 가능
//    public BasicItemController(ItemRepository itemRepository) {
//        this.itemRepository = itemRepository;
//    }

    @GetMapping
    public String items(Model model){
        List<Item> items = itemRepository.findAll();
        model.addAttribute("items",items);
        return "basic/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute("item",findItem);
        return "basic/item";
    }

    @GetMapping("/add")
    public String addForm(){
        return "basic/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(
            @RequestParam String itemName,
            @RequestParam Integer price,
            @RequestParam Integer quantity,
            Model model
    ){
        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);
        itemRepository.save(item);
        model.addAttribute("item",item);
        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV2(
            @ModelAttribute("item") Item item,//v1 과 같은 역할
            Model model
    ){
        itemRepository.save(item);
//        model.addAttribute("item",item); //@ModelAttribute 에서 자동 추가 해주기 때문에 생략 가능하다.

        return "basic/item";
    }

//    @PostMapping("/add")
    public String addItemV3(
            @ModelAttribute Item item,//v1 과 같은 역할
            Model model
    ){
        itemRepository.save(item);

        return "basic/item";
    }
//    @PostMapping("/add")//PRG post redirect get - post 후 redirect 로 get 을 요청 하는것 post 로 남아있을 경우 새로고침 하면 post 가 호출되어 상품이 계속 등록되는 문제가 발생함.
    public String addItemV4(
            @ModelAttribute Item item,//v1 과 같은 역할
            Model model
    ){
        itemRepository.save(item);

        return "redirect:/basic/items/" + item.getId();
    }

    @PostMapping("/add")
    public String addItemV5(
            @ModelAttribute Item item,//v1 과 같은 역할
            RedirectAttributes redirectAttributes
    ){
        Item saveItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId",saveItem.getId());
        redirectAttributes.addAttribute("status",true);//redirect 에 안들어간것은 쿼리 파라미터로 들어간다.
        return "redirect:/basic/items/{itemId}";
    }
    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model){
        Item findItem = itemRepository.findById(itemId);
        model.addAttribute(findItem);
        return "basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String saveItem(@ModelAttribute Item item,@PathVariable Long itemId){
        itemRepository.update(itemId,item);
        return "redirect:/basic/items/{itemId}";
    }


    @PostConstruct
    public void init(){//테스트용 데이터
        itemRepository.save(new Item("itemA",10000,10));
        itemRepository.save(new Item("itemB",20000,20));
    }

}
