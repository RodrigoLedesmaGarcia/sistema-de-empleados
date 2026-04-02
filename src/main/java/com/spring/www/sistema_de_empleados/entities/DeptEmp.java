package com.spring.www.sistema_de_empleados.entities;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class DeptEmp {

    @EmbeddedId
    private DeptEmpId id;

    @Column(name = "from_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @Column(name = "to_date", nullable = false)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    public DeptEmp() {
    }

    public DeptEmp(DeptEmpId id, LocalDate fromDate, LocalDate toDate) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
    }

    public DeptEmpId getId() {
        return id;
    }

    public void setId(DeptEmpId id) {
        this.id = id;
    }

    public LocalDate getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDate fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDate getToDate() {
        return toDate;
    }

    public void setToDate(LocalDate toDate) {
        this.toDate = toDate;
    }
}
