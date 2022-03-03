package com.bftcom.WatchService.repository

import com.bftcom.WatchService.model.Person

interface PersonRepository {
    fun getAll(): List<Person>

    fun findByNameAndLastName(name: String, lastName: String): Person?

    fun create(name: String, lastName: String):Int
}