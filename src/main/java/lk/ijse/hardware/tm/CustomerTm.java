package lk.ijse.hardware.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class CustomerTm {
    private String c_id;
    private String name;
    private String address;
    private String  tel;
    private  String email;
}
