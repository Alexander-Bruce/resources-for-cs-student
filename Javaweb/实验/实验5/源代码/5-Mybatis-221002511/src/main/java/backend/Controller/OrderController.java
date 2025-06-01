package backend.Controller;

import backend.Service.OrderService;
import backend.VO.OrderVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(value = "/orders")
    public List<OrderVO> getOrders(@RequestParam("username") String username) {
        List<OrderVO> orders = orderService.getOrdersByUsername(username);
        return orders;
    }

    @DeleteMapping(value = "/order")
    public Boolean deleteOrder(@RequestBody Map<String, Object> request) {
        Integer orderId = (Integer) request.get("orderId");

        return orderService.deleteOrder(orderId);
    }

    @PostMapping(value = "/order/add")
    public Boolean addOrder(@RequestBody Map<String, Object> request) {
        String username = (String) request.get("username");
        Integer orderId = (Integer) request.get("productId");

        return orderService.addOrder(username, orderId);
    }

}
