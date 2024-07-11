package lk.ijse.hardware.dto;

import lk.ijse.hardware.entity.Order;
import lk.ijse.hardware.entity.Order_Detail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@NoArgsConstructor
@AllArgsConstructor
@Data
public class PlacOrderDTO {
    private Order order;
    private List<Order_Detail> odList;
}
