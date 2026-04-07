package com.spring.www.sistema_de_empleados.repository;

import com.spring.www.sistema_de_empleados.entities.DeptEmp;
import com.spring.www.sistema_de_empleados.entities.DeptEmpId;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptEmpRepository extends JpaRepository<DeptEmp, DeptEmpId> {

    @Modifying
    @Transactional
    @Query("delete from DeptEmp de where de.id.empNo = :empNo")
    void deleteByEmpNo(@Param("empNo") Integer empNo);
}
