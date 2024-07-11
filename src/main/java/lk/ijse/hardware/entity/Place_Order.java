package lk.ijse.hardware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Place_Order {
    private Order order;
    private List<Order_Detail> odList;

}
