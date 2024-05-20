package io.codenamite.oracleplayground.model

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.persistence.*
import java.time.LocalDate

@Entity
@Table(name = "EMPLOYEES")
data class Employee(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonProperty("EMPLOYEE_ID")
    val employeeId: Long,

    @JsonProperty("FIRST_NAME")
    val firstName: String = "",

    @JsonProperty("LAST_NAME")
    val lastName: String = "",

    @JsonProperty("EMAIL")
    val email: String = "",

    @JsonProperty("PHONE_NUMBER")
    val phoneNumber: String? = null,

    @JsonProperty("HIRE_DATE")
    val hireDate: LocalDate = LocalDate.now(),

    @JsonProperty("JOB_ID")
    val jobId: String = "",

    @JsonProperty("SALARY")
    val salary: Double = 0.0,

    @JsonProperty("COMMISSION_PCT")
    val commissionPct: Double? = null,

    @JsonProperty("MANAGER_ID")
    val managerId: Long? = null,

    @JsonProperty("DEPARTMENT_ID")
    val departmentId: Long? = null
)
