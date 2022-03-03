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
) : PersonService, ApplicationListener<ApplicationReadyEvent> {
    override fun getAll(): List<Person> = personRepository.getAll()

    override fun create(person: Person): Int =
        personRepository.create(person.name,person.lastName)


    private fun Path.watch() : WatchService {
        //Create a watch service
        val watchService = this.fileSystem.newWatchService()

        //Register the service, specifying which events to watch
        register(watchService,
            StandardWatchEventKinds.ENTRY_CREATE,
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.OVERFLOW,
            StandardWatchEventKinds.ENTRY_DELETE)

        //Return the watch service
        return watchService
    }

    override fun onApplicationEvent(event: ApplicationReadyEvent) {
        val folder = ("C:\\Users\\Денис\\Desktop\\demo-master\\WatchService\\Persons")
        val path = Paths.get(folder)

        val watcher = path.watch()
        println("Press ctrl+c to exit")

        while(true){
            val key = watcher.take()
            var jsonString:String = ""
            key.pollEvents().forEach { it ->
                var filename = folder +"//" + it.context().toString()
                if (it.context().toString().contains(".json")){
                    jsonString = File(filename).readText()
                    val personArray = Klaxon().parseArray<Person>(jsonString)
                    personArray?.forEach{iter ->
                        if (personRepository.findByNameAndLastName(iter.name,iter.lastName) == null){
                            personRepository.create(iter.name,iter.lastName)
                            println("add Person{ ${iter.name}, ${iter.lastName} }")
                        }else{
                            println("ignoring Person{ ${iter.name}, ${iter.lastName} }")
                        }
                    }

                }
                when(it.kind().name()){
                    "ENTRY_CREATE" -> println("${it.context()} was created")
                    "ENTRY_MODIFY" -> println("${it.context()} was modified")
                    "OVERFLOW" -> println("${it.context()} overflow")
                    "ENTRY_DELETE" -> println("${it.context()} was deleted")
                }
            }
            key.reset()
        }
    }

}