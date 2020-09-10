package example.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Override
    protected void configure(final HttpSecurity httpSecurity) throws Exception {

        httpSecurity
                .csrf().disable()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers(HttpMethod.GET,
                        "/",
                        "/swagger-resources/**",
                        "/v2/api-docs/**",
                        "/*.html",
                        "/favicon.ico",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/actuator/**",
                        "/swagger-ui/**",
                        "/swagger-ui/index.html**",
                        "/webjars/**"
                )
                .permitAll()
                .antMatchers(HttpMethod.GET,
                        "/api/client/**"
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/api/client/**"
                ).permitAll()
                .antMatchers(HttpMethod.PUT,
                        "/api/client/**"
                ).permitAll()
                .antMatchers(HttpMethod.DELETE,
                        "/api/client/**"
                ).permitAll()
                .antMatchers(HttpMethod.POST,
                        "/h2/**"
                ).permitAll()
                .antMatchers(HttpMethod.GET,
                        "/h2/**"
                ).permitAll()
                .anyRequest().authenticated();
        httpSecurity.headers().cacheControl();
    }

}