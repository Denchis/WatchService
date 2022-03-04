package com.bftcom.WatchService

import com.beust.klaxon.Klaxon
import com.bftcom.WatchService.model.Person
import com.bftcom.WatchService.repository.PersonRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.scheduling.annotation.Async
import java.io.File


@SpringBootTest
internal class JsonListenerTest {

    @Autowired
    lateinit var personRepository: PersonRepository

    @Test
    fun JsonListenerCreateTest(){
        personRepository.deleteAll()
        val str = "[\n" +
                "    {\n" +
                "        \"name\": \"Grisha\",\n" +
                "        \"lastName\": \"Gruzd\"\n" +
                "    }\n" +
                "]"

        val file = File("C:\\Users\\Денис\\Desktop\\demo-master\\WatchService\\test\\Bubi.json")
        file.createNewFile()
        file.writeText(str)
        file.copyTo(File("C:\\Users\\Денис\\Desktop\\demo-master\\WatchService\\Persons\\Bubi.json"))
        Thread.sleep(3000)
        val person = personRepository.findByNameAndLastName("Grisha", "Gruzd")
        assertNotNull(person)
        assertEquals("Grisha",person?.name)
        assertEquals("Gruzd",person?.lastName)
    }

}