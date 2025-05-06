package com.flywire.exercise.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywire.exercise.model.Employee;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.flywire.exercise.util.JsonUtil;
import java.text.SimpleDateFormat;
import java.io.IOException;
import java.io.File;

@Service
public class EmployeeService 
{

    private List<Employee> employees;

    @PostConstruct
    public void init() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            // InputStream is = getClass().getClassLoader().getResourceAsStream("json/data.json");
            File file = new File("src/main/resources/json/data.json");
            // employees = mapper.readValue(is, new TypeReference<List<Employee>>() {});
            employees = mapper.readValue(file, new TypeReference<List<Employee>>() {});
        } catch (Exception e) {
            e.printStackTrace();
            employees = Collections.emptyList();
        }
    }

    // ✅ Active employees sorted by last name
    public List<Employee> getActiveEmployeesSortedByLastName() {
        return employees.stream()
                .filter(Employee::isActive)
                .sorted(Comparator.comparing(e -> {
                    String[] parts = e.getName().split(" ");
                    return parts[parts.length - 1]; // last name
                }))
                .collect(Collectors.toList());
    }

    // ✅ Inactive employees sorted by last name
    public List<Employee> getInactiveEmployeesSortedByLastName() {
    return employees.stream()
            .filter(e -> !e.isActive())
            .sorted(Comparator.comparing(e -> {
                String[] parts = e.getName().split(" ");
                return parts[parts.length - 1]; // last name
            }))
            .collect(Collectors.toList());
    }

    // ✅ Get one employee by ID
    public Employee getEmployeeById(int id) {
        return employees.stream()
                .filter(emp -> emp.getId() == id)
                .findFirst()
                .orElse(null);
    }

    // ✅ Get employee with names of their direct reports
    public Map<String, Object> getEmployeeWithDirectReports(int id) {
        Employee employee = getEmployeeById(id);
        if (employee == null) {
            return null;
        }

        List<String> directReportNames = employee.getDirectReports().stream()
                .map(this::getEmployeeById)
                .filter(Objects::nonNull)
                .map(Employee::getName)
                .collect(Collectors.toList());

        Map<String, Object> result = new HashMap<>();
        result.put("employee", employee);
        result.put("directReports", directReportNames);

        return result;
    }

    public List<Employee> getEmployeesHiredInRange(String startDateStr, String endDateStr) 
    {
        SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
        try {
            Date start = sdf.parse(startDateStr);
            Date end = sdf.parse(endDateStr);

            return employees.stream()
                    .filter(emp -> {
                        try {
                            Date hireDate = sdf.parse(emp.getHireDate());
                            return !hireDate.before(start) && !hireDate.after(end);
                        } catch (ParseException e) {
                            return false;
                        }
                    })
                    .sorted((e1, e2) -> {
                        try {
                            Date d1 = sdf.parse(e1.getHireDate());
                            Date d2 = sdf.parse(e2.getHireDate());
                            return d2.compareTo(d1); // descending
                        } catch (ParseException e) {
                            return 0;
                        }
                    })
                    .collect(Collectors.toList());

        } catch (ParseException e) {
            throw new IllegalArgumentException("Invalid date format. Use MM/dd/yyyy");
        }
    }

    public synchronized Employee createEmployee(Employee newEmployee) 
    {
        // Validation
        if (newEmployee.getName() == null || newEmployee.getName().isBlank()) {
            throw new IllegalArgumentException("Employee name is required.");
        }
        if (newEmployee.getPosition() == null || newEmployee.getPosition().isBlank()) {
            throw new IllegalArgumentException("Position is required.");
        }
        if (employees.stream().anyMatch(e -> e.getId() == newEmployee.getId())) {
            throw new IllegalArgumentException("Employee ID already exists.");
        }

        // Assign defaults
        newEmployee.setActive(true);
        newEmployee.setHireDate(new SimpleDateFormat("MM/dd/yyyy").format(new java.util.Date()));
        if (newEmployee.getDirectReports() == null) {
            newEmployee.setDirectReports(List.of());
        }

        // Add and persist
        employees.add(newEmployee);
        try {
            // String path = getClass().getClassLoader().getResource("src/main/resources/json/data.json").getPath();
            String path = "src/main/resources/json/data.json"; 
            JsonUtil.writeToFile(employees, path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write to data.json", e);
        }

        return newEmployee;
    }

    public synchronized boolean deleteEmployeeById(int id) {
    boolean removed = employees.removeIf(emp -> emp.getId() == id);
    if (removed) {
        try {
            String path = new File("src/main/resources/json/data.json").getAbsolutePath();
            JsonUtil.writeToFile(employees, path);
        } catch (IOException e) {
            throw new RuntimeException("Failed to update JSON file after deletion.", e);
        }
    }
    return removed;
    }

    public Employee updateEmployee(int id, Employee updatedEmployee) {
    List<Employee> employees = loadEmployees(); // Load from JSON or wherever you're storing
    boolean found = false;

    for (int i = 0; i < employees.size(); i++) {
        if (employees.get(i).getId() == id) {
            updatedEmployee.setId(id); // Ensure ID remains unchanged
            employees.set(i, updatedEmployee);
            found = true;
            break;
        }
    }

    if (!found) {
        throw new NoSuchElementException("Employee not found with ID: " + id);
    }

    saveEmployees(employees); // Persist to file
    return updatedEmployee;
    }


}
