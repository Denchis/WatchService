package com.bftcom.WatchService.repository

import com.bftcom.WatchService.model.Person
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PersonRepositoryImplTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun getAll() {
        personRepository.deleteAll()
        val id: Int = personRepository.create("Grisha","Gruzd")
        assertEquals(id,personRepository.getAll().get(0).id)
        assertEquals("Grisha",personRepository.getAll().get(0).name)
        assertEquals("Gruzd",personRepository.getAll().get(0).lastName)
    }

    @Test
    fun findByNameAndLastName() {
        personRepository.deleteAll()
        val id: Int = personRepository.create("Grisha","Gruzd")
        val person: Person? = personRepository.findByNameAndLastName("Grisha","Gruzd")
        assertNotNull(person)
        assertEquals(id,person?.id)
        assertEquals("Grisha", person?.name)
        assertEquals("Gruzd", person?.lastName)
    }

    @Test
    fun create() {
        val id: Int = personRepository.create("Grisha","Gruzd")
        val person:Person? = personRepository.findByNameAndLastName("Grisha","Gruzd")
        assertEquals(id,person?.id)
        assertEquals("Grisha", person?.name)
        assertEquals("Gruzd", person?.lastName)
    }
}