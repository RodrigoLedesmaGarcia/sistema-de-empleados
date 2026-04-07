package com.spring.www.sistema_de_empleados.repository;

import com.spring.www.sistema_de_empleados.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
}
