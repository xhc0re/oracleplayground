package io.codenamite.oracleplayground.service

import io.codenamite.oracleplayground.common.OracleObjectMapper
import io.codenamite.oracleplayground.model.Employee
import io.codenamite.oracleplayground.repository.EmployeeRepository
import jakarta.annotation.PostConstruct
import org.hibernate.dialect.OracleTypes
import org.springframework.jdbc.core.SqlOutParameter
import org.springframework.jdbc.core.SqlParameter
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcCall
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.util.LinkedCaseInsensitiveMap
import java.math.BigDecimal
import java.sql.Timestamp
import java.sql.Types

@Service
class EmployeeService(
    private val employeeRepository: EmployeeRepository,
    private val namedParameterJdbcTemplate: NamedParameterJdbcTemplate,
    private val oracleObjectMapper: OracleObjectMapper,
) {
    private lateinit var simpleJdbcCall: SimpleJdbcCall
    @PostConstruct
    private fun init() {
        this.simpleJdbcCall = SimpleJdbcCall(namedParameterJdbcTemplate.jdbcTemplate)
            .withProcedureName("get_employees")
            .declareParameters(
                SqlParameter("p_department_id", Types.NUMERIC),
                SqlOutParameter("p_cursor", OracleTypes.CURSOR)
            )
    }

    fun findEmployeesByDepartment(departmentId: Long) = employeeRepository.findByDepartmentId(departmentId)

    @Transactional
    fun updateSalaries(percent: Double, departmentId: Long) {
        val params = MapSqlParameterSource()
        params.addValue("percent", percent)
        params.addValue("departmentId", departmentId)

        namedParameterJdbcTemplate.execute(
            "BEGIN update_salaries(:percent, :departmentId); END;",
            params
        ) { ps -> ps.execute() }
    }

    @Transactional
    fun countEmployees(departmentId: Long): Int {
        val sql = "SELECT count_employees(:departmentId) FROM dual"
        val params = MapSqlParameterSource()
            .addValue("departmentId", departmentId)
        return namedParameterJdbcTemplate.queryForObject(sql, params, Int::class.java)!!
    }

    @Transactional(readOnly = true)
    fun getEmployees(departmentId: Long): List<Employee> {
        val params = MapSqlParameterSource().apply {
            addValue("p_department_id", departmentId)
        }

        val result = simpleJdbcCall.execute(params)
        val cursor = result["p_cursor"] as List<Map<String, Any>>

        return cursor.map { emp ->
            oracleObjectMapper.getOracleObjectMapper().convertValue(emp, Employee::class.java)
        }
    }
}