package backend.VO;

import backend.entity.Order;
import backend.entity.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderWithUser {

    private Integer id;

    private String username;

    private String password;

    private List<Order> order;

    private List<Product> product;
}
