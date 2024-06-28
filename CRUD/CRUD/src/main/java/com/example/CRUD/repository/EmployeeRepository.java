package com.example.CRUD.repository;

import com.example.CRUD.entity.Employee;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class EmployeeRepository  {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Employee> findAll() {
        return jdbcTemplate.query("SELECT * FROM employee", new EmployeeRowMapper());
    }

    public Employee findById(Long id) {
        return jdbcTemplate.queryForObject("SELECT * FROM employee WHERE id = ?", new EmployeeRowMapper(), id);
    }

    public int save(Employee employee) {
        return jdbcTemplate.update("INSERT INTO employee (name, department, salary) VALUES (?, ?, ?)",
                employee.getName(), employee.getDepartment(), employee.getSalary());
    }

    public int update(Employee employee) {
        return jdbcTemplate.update("UPDATE employee SET name = ?, department = ?, salary = ? WHERE id = ?",
                employee.getName(), employee.getDepartment(), employee.getSalary(), employee.getId());
    }

    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM employee WHERE id = ?", id);
    }

    private static class EmployeeRowMapper implements RowMapper<Employee> {
        @Override
        public Employee mapRow(ResultSet rs, int rowNum) throws SQLException {
            Employee employee = new Employee();
            employee.setId(rs.getLong("id"));
            employee.setName(rs.getString("name"));
            employee.setDepartment(rs.getString("department"));
            employee.setSalary(rs.getDouble("salary"));
            return employee;
        }
    }

//    public List<Employee> list(){
//        return jdbcTemplate.query("select * from employee",new EmployeeRowMapper());
//    }
//    public Employee findbyid(Long id){
//        return jdbcTemplate.queryForObject("select * from employee where id=?",new EmployeeRowMapper(),id);
//    }
//    public int saveemp(Employee employee){
//        return jdbcTemplate.update("",employee.getId());
//    }

}