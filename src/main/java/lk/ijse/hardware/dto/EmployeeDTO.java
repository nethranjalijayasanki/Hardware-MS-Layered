package lk.ijse.hardware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class EmployeeDTO {
    private String e_id;
    private String name;
    private  String address;
    private String tel;
    private String email;
    private double salary;
}
