package backend.Service.impl;

import backend.Service.OrderService;
import backend.VO.OrderVO;
import backend.entity.User;
import backend.mapper.OrderMapper;
import backend.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<OrderVO> getOrdersByUsername(String username) {
        User user = userMapper.getUser(username);
        if(user == null) return null;
        List<OrderVO> orderVOList =  orderMapper.getOrders(user.getId());

        System.out.println(orderMapper.getOrdersWithUser(username));

        return orderVOList;
    }

    @Override
    public Boolean deleteOrder(Integer orderId) {
        return orderMapper.deleteOrder(orderId);
    }

    @Override
    public Boolean addOrder(String username, Integer orderId) {
        return orderMapper.addOrder(userMapper.getUser(username).getId(), orderId, LocalDate.now());
    }
}
