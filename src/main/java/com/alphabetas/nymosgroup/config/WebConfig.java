package com.alphabetas.nymosgroup.config;

import com.alphabetas.nymosgroup.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class WebConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    UserDetailsServiceImpl detailsService;

    @Autowired
    public void ConfigGlobal(AuthenticationManagerBuilder builder) throws Exception{
        builder.userDetailsService(detailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers(""/**).permitAll()
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .antMatchers("/chat/**", "/chats/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers(HttpMethod.POST, "/chats/**").access("hasAnyRole('ROLE_ADMIN', 'ROLE_USER')")
                .antMatchers("/", "/login", "/registration").permitAll()
                .anyRequest().authenticated()
                .and()
                .logout()/*.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))*/.logoutSuccessUrl("/login")
                .and()
                .exceptionHandling().accessDeniedPage("/403")
                .and()
                .rememberMe().tokenRepository(tokenRepository())
                .tokenValiditySeconds(7 * 24 * 60 * 60);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/img/**")
                .antMatchers("/css/**")
                .antMatchers("/js/**");
    }

    @Bean
    public PersistentTokenRepository tokenRepository(){
        JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
        db.setDataSource(dataSource);
        return db;
    }
}
