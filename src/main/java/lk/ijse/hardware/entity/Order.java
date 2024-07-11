package lk.ijse.hardware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Order {
    private String o_id;
    private Date date;
    private String c_id;


}
