package com.rmpt.citizencard.reader.api.common.filter

import com.rmpt.citizencard.reader.api.config.AppProperties
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class HeaderFilter : OncePerRequestFilter() {

    @Autowired
    private lateinit var appProperties: AppProperties

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if (appProperties.requiredHeader != null) {
            val requiredHeader = appProperties.requiredHeader!!
            val receivedHeaderValue = request.getHeader(requiredHeader.name)
            if (requiredHeader.value != receivedHeaderValue) {
                response.status = HttpStatus.FORBIDDEN.value()
                return
            }
        }
        filterChain.doFilter(request, response)
    }
}
