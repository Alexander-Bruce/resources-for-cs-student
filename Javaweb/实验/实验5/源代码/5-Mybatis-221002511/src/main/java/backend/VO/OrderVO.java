package backend.VO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderVO {
    private Integer orderId;

    private Integer productId;

    private String productName;

    private Integer finish;

    private Float price;

    private String time;
}
