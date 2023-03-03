package com.rmpt.citizencard.reader.api.config

import com.rmpt.citizencard.reader.CitizenCardReader
import com.rmpt.citizencard.reader.peidlib.PteidlibLoader
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.servlet.config.annotation.EnableWebMvc
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import javax.annotation.PostConstruct
import javax.annotation.PreDestroy

@Configuration
@EnableWebMvc
open class MainConfig : WebMvcConfigurer {

    private val log = LoggerFactory.getLogger(MainConfig::class.java)

    @Autowired
    private lateinit var appProperties: AppProperties

    @PostConstruct
    fun setup() {
        PteidlibLoader.load()
    }

    @PreDestroy
    fun destroy() {
        PteidlibLoader.destroy()
    }

    @Bean
    open fun citizenCardReader(): CitizenCardReader = CitizenCardReader()

    @Bean
    open fun corsFilter(): CorsFilter {
        val corsConfiguration = CorsConfiguration()
        if (appProperties.cors != null) {
            corsConfiguration.allowedOrigins = appProperties.cors!!.split(";")
        }
        if (appProperties.requiredHeader != null) {
            corsConfiguration.allowedHeaders = listOf(appProperties.requiredHeader!!.name)
        }
        corsConfiguration.allowedMethods = listOf("GET", "OPTIONS")
        val urlBasedCorsConfigurationSource = UrlBasedCorsConfigurationSource()
        urlBasedCorsConfigurationSource.registerCorsConfiguration("/**", corsConfiguration)
        return CorsFilter(urlBasedCorsConfigurationSource)
    }

    @Bean
    open fun filterChain(httpSecurity: HttpSecurity): SecurityFilterChain =
        httpSecurity
            .httpBasic().disable()
            .authorizeRequests().anyRequest().anonymous().and()
            .csrf().disable()
            .cors().and()
            .build()

    @Bean
    open fun authManager(httpSecurity: HttpSecurity): AuthenticationManager =
        // just to avoid the annoying "Using generated security password" warning
        httpSecurity
            .getSharedObject(AuthenticationManagerBuilder::class.java)
            .inMemoryAuthentication()
            .and()
            .build()
}
