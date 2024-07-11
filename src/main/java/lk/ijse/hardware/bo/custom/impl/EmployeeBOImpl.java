package lk.ijse.hardware.bo.custom.impl;

import lk.ijse.hardware.bo.custom.EmployeeBO;
import lk.ijse.hardware.dao.DAOFactory;
import lk.ijse.hardware.dao.custom.CustomerDAO;
import lk.ijse.hardware.dao.custom.EmployeeDAO;
import lk.ijse.hardware.dto.EmployeeDTO;
import lk.ijse.hardware.dto.TransportDTO;
import lk.ijse.hardware.entity.Employee;
import lk.ijse.hardware.entity.Transport;

import java.sql.SQLException;
import java.util.ArrayList;

public class EmployeeBOImpl implements EmployeeBO {
    EmployeeDAO employeeDAO=(EmployeeDAO) DAOFactory.getDaoFactory().getDAO(DAOFactory.DAOTypes.EMPLOYEE);
    @Override
    public ArrayList<EmployeeDTO> getAllEmployee() throws SQLException, ClassNotFoundException {

        ArrayList<EmployeeDTO> employeeDTOS = new ArrayList<>();
        ArrayList<Employee> employees = employeeDAO.getAll();

        for (Employee employee : employees) {
            EmployeeDTO employeeDTO = new EmployeeDTO(employee.getE_id(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getSalary());
            employeeDTOS.add(employeeDTO);
        }
        return employeeDTOS;
    }

    @Override
    public ArrayList<String> getEmployeeIDs() throws SQLException, ClassNotFoundException {
        return employeeDAO.getIDs();
    }

    @Override
    public boolean addEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.add(new Employee(employeeDTO.getE_id(),employeeDTO.getName(),employeeDTO.getAddress(),employeeDTO.getTel(),employeeDTO.getEmail(),employeeDTO.getSalary()));
    }

    @Override
    public boolean updateEmployee(EmployeeDTO employeeDTO) throws SQLException, ClassNotFoundException {
        return employeeDAO.update(new Employee(employeeDTO.getE_id(),employeeDTO.getName(),employeeDTO.getAddress(),employeeDTO.getTel(),employeeDTO.getEmail(),employeeDTO.getSalary()));
    }

    @Override
    public boolean deleteEmployee(String id) throws SQLException, ClassNotFoundException {
        return employeeDAO.delete(id);
    }

    @Override
    public EmployeeDTO searchByID(String id) throws SQLException, ClassNotFoundException {
       Employee employee=employeeDAO.searchByID(id);
       return new EmployeeDTO(employee.getE_id(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getSalary());
    }

    @Override
    public EmployeeDTO searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        return employeeDAO.generateNewID();
    }

    @Override
    public boolean existEmployee(String id) throws SQLException, ClassNotFoundException {
        return false;
    }
}
