package backend.Service;

import backend.VO.OrderVO;

import java.util.List;

public interface OrderService {
    List<OrderVO> getOrdersByUsername(String username);

    Boolean deleteOrder(Integer orderId);

    Boolean addOrder(String username, Integer orderId);
}
