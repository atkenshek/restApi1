package com.example.restapi1.testproperties

import com.example.restapi1.business.service.UserServiceImpl
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import spock.mock.DetachedMockFactory

@Configuration
class MockConfig {
    private DetachedMockFactory mockFactory = new DetachedMockFactory()

    @Bean
    @Primary
    UserServiceImpl mockIpRequestService() {
        return mockFactory.Mock(UserServiceImpl)
    }
}
