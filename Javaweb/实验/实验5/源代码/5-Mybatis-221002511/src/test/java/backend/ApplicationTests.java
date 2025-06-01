package backend;

import backend.Service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ApplicationTests {

    @Autowired
    private OrderServiceImpl orderService;

    @Test
    void contextLoads() {

        orderService.getOrdersByUsername("Alexander");

    }

}
