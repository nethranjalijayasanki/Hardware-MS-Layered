package lk.ijse.hardware.bo.custom;

import lk.ijse.hardware.bo.SuperBO;
import lk.ijse.hardware.dto.CustomerDTO;
import lk.ijse.hardware.dto.EmployeeDTO;

import java.sql.SQLException;
import java.util.ArrayList;

public interface EmployeeBO extends SuperBO {
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException;
    public ArrayList<String> getEmployeeIDs() throws SQLException, ClassNotFoundException;
    public boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException;
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchByID(String id) throws SQLException, ClassNotFoundException;
    public EmployeeDTO searchByTel(String tell) throws SQLException, ClassNotFoundException;
    public String generateNewID() throws SQLException, ClassNotFoundException;
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException;
}
