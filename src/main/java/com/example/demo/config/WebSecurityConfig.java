package com.example.demo.config;

import com.example.demo.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.cors() //기본 CORS 설정
                .and()
                .csrf().disable()   //CSRF 사용하지 않으므로 비활성화
                .httpBasic().disable()  //토큰을 사용하므로 basic 인증 비활성화
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)//세션기반이 아님을 선언
                .and()
                .authorizeRequests().antMatchers("/", "/auth/**").permitAll()//적혀있는 경로 접근시에는 인증 안해도 됨
                .anyRequest().authenticated();  //그 이외에는 모두 인증

        httpSecurity.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }
}
