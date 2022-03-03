package com.bftcom.WatchService.repository

import com.bftcom.WatchService.model.Person
import org.springframework.jdbc.core.RowMapper
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.support.GeneratedKeyHolder
import org.springframework.stereotype.Repository

@Repository
class PersonRepositoryImpl(
    private val jdbcTemplate: NamedParameterJdbcTemplate,
) : PersonRepository {
    override fun getAll(): List<Person> =
        jdbcTemplate.query("select * from Person", ROW_MAPPER)


    override fun findByNameAndLastName(name: String, lastName: String): Person? {
        return jdbcTemplate.query(
            "select * from Person where lastName = :lastName AND name = :name",
            mapOf(
                "name" to name,
                "lastName" to lastName,
            ),
            ROW_MAPPER,
        ).firstOrNull()
    }

    override fun create(name: String, lastName: String): Int {
        val keyHolder = GeneratedKeyHolder()
        jdbcTemplate.update(
            "insert into Person(name, lastName) values (:name, :lastName)",
            MapSqlParameterSource(
                mapOf(
                    "name" to name,
                    "lastName" to lastName,
                )
            ),
            keyHolder,
            listOf("id").toTypedArray()
        )
        return keyHolder.keys?.getValue("id") as Int
    }

    override fun deleteAll() {
        val id: Int = 0
        jdbcTemplate.update("delete from Person",
            mapOf(
                "id" to id,
            )
        )
    }


    companion object {
        val ROW_MAPPER = RowMapper<Person> { rs, _ ->
            Person(
                id = rs.getInt("id"),
                name = rs.getString("name"),
                lastName = rs.getString("lastName"),
            )
        }
    }
}