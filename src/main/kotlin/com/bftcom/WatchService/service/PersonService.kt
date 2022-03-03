package com.bftcom.WatchService.service

import com.bftcom.WatchService.model.Person

interface PersonService {
    fun getAll(): List<Person>

    fun create(person: Person): Int

    fun findByNameAndLastName(name: String, lastName: String): Person?

    fun deleteAll()
}