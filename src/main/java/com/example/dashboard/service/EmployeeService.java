package com.example.dashboard.service;

import com.example.dashboard.model.Employee;
import com.example.dashboard.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee createEmployee(Employee employee) {
        return employeeRepository.save(employee);
    }

    public Employee updateEmployee(Long id, Employee updatedEmployee) {
        return employeeRepository.findById(id)
                .map(employee -> {
                    employee.setName(updatedEmployee.getName());
                    employee.setPosition(updatedEmployee.getPosition());
                    employee.setDepartment(updatedEmployee.getDepartment());
                    employee.setSalary(updatedEmployee.getSalary());
                    employee.setDateOfBirth(updatedEmployee.getDateOfBirth());
                    employee.setDateOfJoining(updatedEmployee.getDateOfJoining());
                    employee.setActive(updatedEmployee.isActive());
                    employee.setGender(updatedEmployee.getGender());
                    employee.setPerformanceScore(updatedEmployee.getPerformanceScore());
                    return employeeRepository.save(employee);
                })
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void deleteEmployee(Long id) {
        employeeRepository.deleteById(id);
    }

    // New method to handle employee insights
    public Map<String, Object> getEmployeeInsights() {
        long totalEmployees = employeeRepository.count();
        long activeEmployees = employeeRepository.countByActive(true);
        List<Map<String, Object>> departmentData = employeeRepository.getDepartmentWiseCount();
        List<Map<String, Object>> genderRatio = employeeRepository.getGenderRatio();

        Map<String, Object> insights = new HashMap<>();
        insights.put("totalEmployees", totalEmployees);
        insights.put("activeEmployees", activeEmployees);
        insights.put("departmentData", departmentData);
        insights.put("genderRatio", genderRatio);

        return insights;
    }
}
