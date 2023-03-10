package com.sparta.springsecurity.config;


import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 Security 지원을 가능하게 함
public class WebSecurityConfig {

    // 아래의 SecurityFilterChain보다 더 우선적으로 걸리는 설정
    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // h2-console 사용 및 resources 접근 허용 설정
        /*
            Security는 모든 요청을 다 인증하기 때문에 CSS나 JS image 파일같은건 일일이 인증하지 않게 하려고
            web.ignoring() 쓰면 이러한 경로로 들어오는 것은 인증처리하는걸 무시한다고 설정
            아래 메서드처럼 .permitAll로 하나하나 설정하는 방법도 있고 이렇게 한방에 할 수도 있다.
         */

        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toH2Console())
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // CSRF 설정
        http.csrf().disable();
        // .permitAll()로 저기 url들은 인증 안하고 실행 되게 한다.
        http.authorizeRequests()
//                .antMatchers(HttpMethod.GET, "/api/user").hasAnyRole(ADMIN)// 이렇게 api 지정하거나
//                .antMatchers("/h2-console/**").permitAll()
//                .antMatchers("/css/**").permitAll()
//                .antMatchers("/js/**").permitAll()
//                .antMatchers("/images/**").permitAll()
//                .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                .anyRequest().authenticated();  //  이 외의 URL요청들은 다 인증을 하겠다.

        // Custom 로그인 페이지 사용
        http.formLogin().loginPage("/api/user/login-page").permitAll();

        return http.build();
    }

}