package com.shibuyaxpress.photobackend

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.config.annotation.CorsRegistry

import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@SpringBootApplication
class PhotoBackendApplication

@Bean
fun corsConfigurer(): WebMvcConfigurer? {
	return object : WebMvcConfigurerAdapter() {
		override fun addCorsMappings(registry: CorsRegistry) {
			registry.addMapping("/**").allowedOrigins("*")
		}
	}
}
fun main(args: Array<String>) {
	runApplication<PhotoBackendApplication>(*args)
}

