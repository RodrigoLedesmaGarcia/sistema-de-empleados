package com.spring.www.sistema_de_empleados.service;

import com.spring.www.sistema_de_empleados.entities.DeptEmp;
import com.spring.www.sistema_de_empleados.entities.DeptEmpId;
import com.spring.www.sistema_de_empleados.entities.Employee;
import com.spring.www.sistema_de_empleados.entities.EmployeeCreateAndUpdateRequest;
import com.spring.www.sistema_de_empleados.repository.DeptEmpRepository;
import com.spring.www.sistema_de_empleados.repository.EmployeeMonolithRepository;
import com.spring.www.sistema_de_empleados.utils.EmployeeProjection;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeMonolithRepository repository;

    private final DeptEmpRepository deptEmpRepository;

    public EmployeeServiceImpl(EmployeeMonolithRepository repository, DeptEmpRepository deptEmpRepository) {
        this.repository = repository;
        this.deptEmpRepository = deptEmpRepository;
    }


    /*
    𝕙𝕖𝕝𝕡𝕖𝕣 𝕡𝕒𝕣𝕒 𝕢𝕦𝕚𝕥𝕒𝕣 𝕖𝕤𝕡𝕒𝕔𝕚𝕠𝕤 𝕖𝕟 𝕥𝕖𝕩𝕥𝕠
     */
    private String quitarEspacios(String string){
        return (string == null) ? null : string.trim();
    }

    /*
    𝔹𝕦𝕤𝕔𝕒𝕣 𝕖𝕞𝕡𝕝𝕖𝕒𝕕𝕠 𝕔𝕠𝕟 𝕗𝕚𝕝𝕥𝕣𝕠𝕤
     */
    @Override
    public Page<EmployeeProjection> findAllEmployees(Integer empNo, LocalDate birthDate, String firstName, String lastName, String gender, LocalDate hireDate, String deptNo, LocalDate fromDate, LocalDate toDate, int page, int size) {
        Pageable pageable = PageRequest.of(size, page);

        return repository.findAllEmployees(
                empNo == null ? null : empNo.intValue(),
                birthDate,
                quitarEspacios(firstName),
                quitarEspacios(lastName),
                quitarEspacios(gender),
                hireDate,
                quitarEspacios(deptNo),
                fromDate,
                toDate,
                pageable

        );
    }


    /*
   𝕓𝕦𝕤𝕔𝕒𝕣 𝕖𝕞𝕡𝕝𝕖𝕒𝕕𝕠 𝕡𝕠𝕣 𝕚𝕕
     */

    @Override
    public Optional<Employee> findById(Integer empNo) {
        return repository.findById(empNo);
    }

    /*
    𝕔𝕣𝕖𝕒𝕣 𝕖𝕞𝕡𝕝𝕖𝕒𝕕𝕠 𝕟𝕦𝕖𝕧𝕠
     */
    @Override
    public void crearEmpleado(EmployeeCreateAndUpdateRequest request) {

        int nextEmpNo = repository.maxEmpNo() + 1;

        Employee employee = new Employee();
        employee.setEmpNo(nextEmpNo);
        employee.setBirthDate(request.getBirthDate());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setGender(request.getGender());
        employee.setHireDate(request.getHireDate());

        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setId(new DeptEmpId(nextEmpNo, request.getDeptNo()));
        deptEmp.setFromDate(request.getFromDate());
        deptEmp.setToDate(request.getToDate());

        employee.getDepartments().add(deptEmp);
        repository.save(employee);
    }

    @Override
    public void editarEmpleado(EmployeeCreateAndUpdateRequest request) {

        if (request.getEmpNo() == null){
            throw  new RuntimeException("ese empleado no existe");
        }

        Employee employee = repository.findById(request.getEmpNo()).orElseThrow( () -> new RuntimeException("ese emplado no existe"));

        employee.setBirthDate(request.getBirthDate());
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setGender(request.getGender());
        employee.setHireDate(request.getHireDate());

        if(employee.getDepartments() != null){
            employee.getDepartments().clear();
        }

        DeptEmp deptEmp = new DeptEmp();
        deptEmp.setId(new DeptEmpId(employee.getEmpNo(), request.getDeptNo()));
        deptEmp.setFromDate(request.getFromDate());
        deptEmp.setToDate(request.getToDate());

        employee.getDepartments().add(deptEmp);

        repository.save(employee);
    }


    /*
    𝕖𝕝𝕚𝕞𝕚𝕟𝕒𝕣 𝕖𝕞𝕡𝕝𝕖𝕒𝕕𝕠
     */
    @Override
    public void eliminarEmpleado(Integer empNo) {

        if(!repository.existsById(empNo)){
            throw  new RuntimeException("emplado no encontrado");
        }

        deptEmpRepository.deleteByEmpNo(empNo);

        repository.deleteById(empNo);
    }
}
