package com.example.springkotlintemplate.Config.Security

import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.HttpStatusEntryPoint

@EnableWebSecurity
class SecurityConfig {
    val PUBLIC_URL= arrayOf("/login","/api/v1/**")
    val PRIVATE_URL= arrayOf("/api/v2/**")

    @Bean
    fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain{
        httpSecurity
            .csrf().disable()
            .httpBasic().disable()
            .setAuthorizeHttpRequest()
            .setOAuth2Login()
        return httpSecurity.build()
    }

    private fun HttpSecurity.setAuthorizeHttpRequest(): HttpSecurity {
        setPublicRequest()
        setPrivateRequest()
        authorizeHttpRequests{auth -> auth
            .anyRequest().authenticated()
        }
        exceptionHandling{ except -> except
            .authenticationEntryPoint(HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED))
        }
        return this
    }

    private fun HttpSecurity.setPrivateRequest() {
        authorizeHttpRequests{auth -> auth
            .antMatchers(*PRIVATE_URL).authenticated()
        }
    }

    private fun HttpSecurity.setPublicRequest() {
        authorizeHttpRequests{auth -> auth
            .antMatchers(*PUBLIC_URL).permitAll()
        }
    }

    private fun HttpSecurity.setOAuth2Login(): HttpSecurity {
        oauth2Login{oauth2-> oauth2
            .defaultSuccessUrl("/",true)
        }
        return this
    }

}


