package io.codenamite.oracleplayground.repository

import io.codenamite.oracleplayground.model.Employee
import org.springframework.data.jpa.repository.JpaRepository

interface EmployeeRepository : JpaRepository<Employee, Long> {
    fun findByDepartmentId(departmentId: Long): List<Employee>
}