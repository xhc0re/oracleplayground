package io.codenamite.oracleplayground.common

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.KotlinFeature
import com.fasterxml.jackson.module.kotlin.KotlinModule
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class OracleObjectMapper {
    private val objectMapper: ObjectMapper = ObjectMapper()
        .registerModule(
            KotlinModule.Builder()
                .configure(KotlinFeature.NullToEmptyCollection, true)
                .configure(KotlinFeature.NullToEmptyMap, true)
                .configure(KotlinFeature.NullIsSameAsDefault, true)
                .configure(KotlinFeature.SingletonSupport, true)
                .configure(KotlinFeature.StrictNullChecks, false)
                .build()
        )
        .registerModule(JavaTimeModule())
        .registerModule(SimpleModule().addDeserializer(LocalDate::class.java, TimestampToLocalDateDeserializer()))

    fun getOracleObjectMapper(): ObjectMapper {
        return objectMapper
    }
}
