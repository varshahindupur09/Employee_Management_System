package com.flywire.exercise.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flywire.exercise.model.Employee;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class JsonUtil {
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void writeToFile(List<Employee> employees, String filePath) throws IOException {
        mapper.writerWithDefaultPrettyPrinter().writeValue(new File(filePath), employees);
    }
}
