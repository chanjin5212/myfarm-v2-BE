package com.myfarm.myfarm

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MyfarmApplication

fun main(args: Array<String>) {
    runApplication<MyfarmApplication>(*args)
}
