package com.bftcom.WatchService.config

import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.AsyncConfigurerSupport

import org.springframework.scheduling.annotation.EnableAsync


@Configuration
@EnableAsync
@EnableAutoConfiguration
class AsyncConfiguration : AsyncConfigurerSupport() {
}