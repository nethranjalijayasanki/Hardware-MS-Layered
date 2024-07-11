package lk.ijse.hardware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class OrderDetailDTO {
    private String o_id;
    private String i_id;
    private int qty;
    private double unit_price;
}
