package com.works.sevendays.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired DriverManagerDataSource db;

    //user login -> sql
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .jdbcAuthentication()
                .dataSource(db)
                .usersByUsernameQuery(" select email, password, status from rest_user where email = ? ")
                .authoritiesByUsernameQuery(" select email, role from rest_user_role where email = ? ")
                .passwordEncoder(passwordEncoder());
    }


    //user role -> role
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers("/product/**")
                .hasRole("product")
                .antMatchers("/customer/**").hasRole("customer")
                .antMatchers("/library/**").hasRole("library")
                .and()
                .csrf().disable()
                .formLogin().disable();

        http.headers().frameOptions().disable(); // h2-console access
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        PasswordEncoder encoder = new PasswordEncoder() {
            @Override
            public String encode(CharSequence rawPassword) {
                return Util.MD5(rawPassword.toString());
            }

            @Override
            public boolean matches(CharSequence rawPassword, String encodedPassword) {
                return Util.MD5(rawPassword.toString()).equals(encodedPassword);
            }
        };
        return encoder;
    }

}
