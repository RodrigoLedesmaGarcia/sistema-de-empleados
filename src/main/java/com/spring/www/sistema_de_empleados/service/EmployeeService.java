package com.spring.www.sistema_de_empleados.service;

import com.spring.www.sistema_de_empleados.entities.Employee;
import com.spring.www.sistema_de_empleados.entities.EmployeeCreateAndUpdateRequest;
import com.spring.www.sistema_de_empleados.utils.EmployeeProjection;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Optional;

public interface EmployeeService {

    Page<EmployeeProjection> findAllEmployees(
            Integer empNo,
            LocalDate birthDate,
            String firstName,
            String lastName,
            String gender,
            LocalDate hireDate,
            String deptNo,
            LocalDate fromDate,
            LocalDate toDate,
            int page,
            int size
    );

    Optional<Employee> findById(Integer empNo);

    void crearEmpleado(EmployeeCreateAndUpdateRequest request);

    void editarEmpleado(EmployeeCreateAndUpdateRequest request);

    void eliminarEmpleado(Integer empNo);


}
