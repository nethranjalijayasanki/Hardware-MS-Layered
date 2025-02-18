package lk.ijse.hardware.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class SupplierDTO {
    private String s_id;
    private String name;
    private String company;
    private String  tel;
    private String email;
}
