package com.bftcom.WatchService.service

import com.beust.klaxon.JsonArray
import com.beust.klaxon.Klaxon
import com.beust.klaxon.Parser
import com.bftcom.WatchService.model.Person
import com.bftcom.WatchService.repository.PersonRepository
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.ApplicationListener
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.StandardWatchEventKinds
import java.nio.file.WatchService

@Service
class PersonServiceImpl(
    private val personRepository: PersonRepository
) : PersonService {
    override fun getAll(): List<Person> = personRepository.getAll()

    override fun create(person: Person): Int =
        personRepository.create(person.name,person.lastName)

    override fun findByNameAndLastName(name: String, lastName: String): Person? =
        personRepository.findByNameAndLastName(name,lastName)

    override fun deleteAll() {
        personRepository.deleteAll()
    }

}