package com.shibuyaxpress.photobackend.controllers

import com.shibuyaxpress.photobackend.ColorRepository
import com.shibuyaxpress.photobackend.models.Color
import com.shibuyaxpress.photobackend.response_models.ColorResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@CrossOrigin(origins = ["*"])
@RequestMapping("/api/colors")
class ColorController(private var colorRepository: ColorRepository) {

    @GetMapping
    fun getAllColors(): List<Color> {
        return colorRepository.findAll()
    }

    @PostMapping
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    fun addColor(@RequestBody response: ColorResponse): Color {
        var color = Color(
                name = response.name,
                hex = response.hex
        )
        return colorRepository.save(color)
    }

    @PutMapping
    fun updateColor(color: Color): Color {
        return colorRepository.save(color)
    }

    @DeleteMapping
    fun deleteColor(color: Color) {
        colorRepository.delete(color)
    }
}