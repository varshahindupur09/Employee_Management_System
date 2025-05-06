package com.flywire.exercise.controller;

import com.flywire.exercise.model.Employee;
import com.flywire.exercise.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import java.util.NoSuchElementException;
import org.springframework.web.bind.annotation.DeleteMapping;


@RestController
@RequestMapping("/api/employees")
public class EmployeeController 
{
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/active")
    public List<Employee> getActiveEmployeesSorted() {
        return employeeService.getActiveEmployeesSortedByLastName();
    }

    @GetMapping("/inactive")
    public List<Employee> getInactiveEmployeesSorted() {
        return employeeService.getInactiveEmployeesSortedByLastName();
    }

    @GetMapping("/hired")
    public ResponseEntity<?> getEmployeesHiredInRange(
        @RequestParam String startDate,
        @RequestParam String endDate) 
    {
        try {
            List<Employee> result = employeeService.getEmployeesHiredInRange(startDate, endDate);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getEmployeeWithReports(@PathVariable int id)
    {
        Map<String, Object> response = employeeService.getEmployeeWithDirectReports(id);
        if (response == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Employee with ID " + id + " not found"));
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<?> createEmployee(@RequestBody Employee employee) 
    {
        try 
        {
            Employee created = employeeService.createEmployee(employee);
            return ResponseEntity.status(HttpStatus.CREATED).body(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
public ResponseEntity<?> deleteEmployee(@PathVariable int id) {
    boolean success = employeeService.deleteEmployeeById(id);
    if (success) {
        return ResponseEntity.ok(Map.of("message", "Employee " + id + " deleted successfully."));
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(Map.of("error", "Employee with ID " + id + " not found."));
    }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee) {
        try {
            Employee employee = employeeService.updateEmployee(id, updatedEmployee);
            return ResponseEntity.ok(employee);
        } catch (NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Map.of("error", "Employee with ID " + id + " not found."));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "Unexpected error: " + e.getMessage()));
        }
    }



}
