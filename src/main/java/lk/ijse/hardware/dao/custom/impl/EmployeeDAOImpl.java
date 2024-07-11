package lk.ijse.hardware.dao.custom.impl;

import lk.ijse.hardware.dao.SQLUtil;
import lk.ijse.hardware.dao.custom.EmployeeDAO;
import lk.ijse.hardware.db.DbConnection;
import lk.ijse.hardware.entity.Employee;
import lk.ijse.hardware.entity.Transport;
import lk.ijse.hardware.tm.EmployeeTm;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAOImpl implements EmployeeDAO {

    @Override
    public boolean add(Employee employee) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("INSERT INTO employees VALUES( ?, ?, ?, ?, ?,?)",employee.getE_id(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getSalary());

    }

    public boolean update(Employee employee) throws SQLException {
        return SQLUtil.execute("UPDATE employees SET name = ?, address = ?, tel = ?, email = ? ,salary = ?   WHERE e_id = ?",employee.getE_id(),employee.getName(),employee.getAddress(),employee.getTel(),employee.getEmail(),employee.getSalary());

    }

    public boolean delete(String id) throws SQLException {
        return SQLUtil.execute("DELETE FROM employees WHERE e_id = ?");
    }

    public ArrayList<Employee> getAll() throws SQLException {
        ArrayList<Employee> allEmp = new ArrayList<>();
        ResultSet rst=SQLUtil.execute("SELECT * FROM employees");

        while (rst.next()){
            Employee employee=new Employee(rst.getString("e_id"),rst.getString("name"),rst.getString("address"),rst.getString("tel"),rst.getString("email"),rst.getDouble("salary"));
            allEmp.add(employee);

        }
        return allEmp;

    }
    @Override
    public Employee searchByID(String id) throws SQLException, ClassNotFoundException {
        ResultSet rst=SQLUtil.execute("SELECT * FROM employees WHERE e_id = ?");
        rst.next();

        return new Employee(rst.getString("e_id"),rst.getString("name"),rst.getString("address"),rst.getString("tel"),rst.getString("email"),rst.getDouble("salary"));

    }

    @Override
    public ArrayList<String> getIDs() throws SQLException, ClassNotFoundException {
        ResultSet resultSet = SQLUtil.execute("SELECT e_id FROM employee");

        ArrayList<String> idList = new ArrayList<>();
        while (resultSet.next()) {
            idList.add(resultSet.getString(1));
        }
        return idList;

    }

    @Override
    public String generateNewID() throws SQLException, ClassNotFoundException {
        ResultSet rst = SQLUtil.execute("SELECT e_id FROM employees ORDER BY e_id DESC LIMIT 1");
        if (rst.next()) {
            String id = rst.getString("id");
            String transportId  = String.valueOf(Integer.parseInt(id.replace("E00-", "")) + 1);
            return String.format("E00-%03d", transportId);
        } else {
            return "E00-001";
        }
    }


    @Override
    public Employee searchByTel(String tell) throws SQLException, ClassNotFoundException {
        return null;
    }


    @Override
    public boolean exist(String id) throws SQLException, ClassNotFoundException {
        return false;
    }

}
