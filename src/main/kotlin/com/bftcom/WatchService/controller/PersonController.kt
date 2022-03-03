package com.bftcom.WatchService.controller

import com.bftcom.WatchService.service.PersonService
import com.bftcom.WatchService.model.Person
import org.springframework.context.event.EventListener
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

class PersonController {

    @RestController
    @RequestMapping("/person")
    class PersonController(
        private val personService: PersonService,
    )
    {
        @GetMapping
        fun getAll(): List<Person> = personService.getAll()

        @EventListener
        fun main(args: Array<String>){
            println("Hello World!")
        }
    }
}