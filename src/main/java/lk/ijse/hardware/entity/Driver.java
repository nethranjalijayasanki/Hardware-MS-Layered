package lk.ijse.hardware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Driver {
    private String d_id;
    private String name;
    private String  tel;
    private String email;
    private String work_time;

}
