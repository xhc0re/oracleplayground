package io.codenamite.oracleplayground.controller

import io.codenamite.oracleplayground.model.Employee
import io.codenamite.oracleplayground.service.EmployeeService
import org.springframework.web.bind.annotation.*


@RestController
@RequestMapping("/employees")
class EmployeeController(
    private val employeeService: EmployeeService
) {

    @GetMapping("/department/{departmentId}")
    fun getEmployeesByDepartment(@PathVariable departmentId: Long): List<Employee> {
        return employeeService.findEmployeesByDepartment(departmentId)
    }

    @PostMapping("/update-salaries")
    fun updateSalaries(@RequestBody request: UpdateSalariesRequest) {
        employeeService.updateSalaries(request.percent, request.departmentId)
    }

    @GetMapping("/count/{departmentId}")
    fun countEmployees(@PathVariable departmentId: Long): Int {
        return employeeService.countEmployees(departmentId)
    }

    @GetMapping("/department/bydept/{departmentId}")
    fun getEmployees(@PathVariable departmentId: Long): List<Employee> {
        return employeeService.getEmployees(departmentId)
    }
}

data class UpdateSalariesRequest(
    val percent: Double,
    val departmentId: Long
)




