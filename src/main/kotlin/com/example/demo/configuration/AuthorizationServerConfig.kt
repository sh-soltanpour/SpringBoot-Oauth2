package com.example.demo.configuration

import com.example.demo.auth.CustomTokenEnhancer
import org.springframework.context.annotation.Configuration
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer
import org.springframework.http.HttpMethod
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter
import org.springframework.security.oauth2.provider.token.DefaultTokenServices
import org.springframework.context.annotation.Primary
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore
import org.springframework.security.oauth2.provider.token.TokenStore




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
        clients!!.inMemory()
                .withClient("admin")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .authorities("ROLE_ADMIN")
                .scopes("read", "write", "admin")
                .accessTokenValiditySeconds(5000)
                .secret("admin-secret")
                .refreshTokenValiditySeconds(50000)
                .and()
                .withClient("client")
                .authorizedGrantTypes("password", "refresh_token")
                .authorities("ROLE_CLIENT")
                .scopes("read")
                .accessTokenValiditySeconds(5000)
                .secret("client-secret")
                .refreshTokenValiditySeconds(50000)
    }

    @Throws(Exception::class)
    override fun configure(endpoints: AuthorizationServerEndpointsConfigurer?) {
        endpoints!!
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
    }

    /* JWT TOKENS CONFIG */
    @Bean
    fun tokenStore(): TokenStore {
        return JwtTokenStore(accessTokenConverter())
    }

    @Bean
    fun accessTokenConverter(): JwtAccessTokenConverter {
        val converter = JwtAccessTokenConverter()
        converter.setSigningKey("123")
        return converter
    }

    @Bean
    @Primary
    fun tokenServices(): DefaultTokenServices {
        val defaultTokenServices = DefaultTokenServices()
        defaultTokenServices.setTokenStore(tokenStore())
        defaultTokenServices.setSupportRefreshToken(true)
        return defaultTokenServices
    }
}