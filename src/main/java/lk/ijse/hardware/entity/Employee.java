package lk.ijse.hardware.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class Employee {
    private String e_id;
    private String name;
    private  String address;
    private String tel;
    private String email;
    private double salary;


}
