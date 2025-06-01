package backend.mapper;

import backend.VO.OrderVO;
import backend.VO.OrderWithUser;
import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface OrderMapper {
    List<OrderVO> getOrders(Integer id);

    Boolean deleteOrder(Integer orderId);

    Boolean addOrder(Integer userId, Integer productId, LocalDate time);

    OrderWithUser getOrdersWithUser(String username);
}
