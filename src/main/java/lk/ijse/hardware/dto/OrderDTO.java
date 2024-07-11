package lk.ijse.hardware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@NoArgsConstructor
@AllArgsConstructor
@Data

public class OrderDTO {
    private String o_id;
    private Date date;
    private String c_id;
}
