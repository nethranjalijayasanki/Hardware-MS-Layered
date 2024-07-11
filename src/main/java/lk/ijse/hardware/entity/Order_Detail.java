package lk.ijse.hardware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Order_Detail {
    private String o_id;
    private String i_id;
    private int qty;
    private double unit_price;

}
