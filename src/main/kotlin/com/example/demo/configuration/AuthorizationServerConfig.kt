package com.example.demo.configuration

import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter


@Configuration
@EnableAuthorizationServer
class AuthorizationServerConfig : AuthorizationServerConfigurerAdapter() {

    @Autowired
    private val authenticationManager: AuthenticationManager? = null

    @Throws(Exception::class)
    override fun configure(security: AuthorizationServerSecurityConfigurer?) {
        security!!
                .tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients()
    }

    @Throws(Exception::class)
    override fun configure(clients: ClientDetailsServiceConfigurer?) {
        clients!!.inMemory().withClient("android-client")
                .authorizedGrantTypes("client-credentials", "password", "refresh_token")
                .authorities("ROLE_CLIENT", "ROLE_ANDROID_CLIENT")
                .scopes("read", "write", "trust")
                .resourceIds("oauth2-resource")
                .accessTokenValiditySeconds(5000)
                .secret("android-secret").refreshTokenValiditySeconds(50000)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!.authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
    }
}