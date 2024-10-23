package cz.moro.usermanagment

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity

@SpringBootApplication
@EnableMethodSecurity
class UsermanagmentApplication

fun main(args: Array<String>) {
	runApplication<UsermanagmentApplication>(*args)
}
