//here we will configure our spring security

package Nova.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

@Configuration
@EnableWebSecurity

public class AppConfig {
    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.sessionManagement(managment -> managment.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(Authorize -> Authorize
                        .requestMatchers("/api/admin/**").hasAnyRole("RESTAURANT_OWNER","ADMIN") // what happens when we add this line is : only the peoples with the role can access this either it could be admin or owner
                        .requestMatchers("/api/**").authenticated() //this is for the user to access after providing jwt token he can access all the end points
                        .anyRequest().permitAll() //here any user doesn't matter who he is , he can access without any token or role

//Sochiye JWT token ek party ka digital entry pass hai. Aur JWT validator uss party ka security guard hai.
//JWT validator ka kaam yeh sunishchit (ensure) karna hai ki jo digital pass (token) server
//ko mila hai, woh asli, valid, aur un-tampered hai, taaki system secure rahe. Agar sab sahi hai,
//toh "Welcome!", warna "Entry Denied!".
                ).addFilterBefore(new JwtTokenValidator(), BasicAuthenticationFilter.class) //we are adding this to check if user have jwt token or not
                .csrf(csrf->csrf.disable())
                .cors(cors->cors.configurationSource(corsConfigurationSource()));






        return http.build();
        
    }

    private CorsConfigurationSource corsConfigurationSource() {
        return new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration cfg=new CorsConfiguration();

                //now in the below line we will provide all the frontend urls through which we can access this backend
                cfg.setAllowedOrigins(Arrays.asList(
                        "https://Nova-food.vercel.app",
                        "http://localhost:3000"
                ));
                cfg.setAllowedMethods(Collections.singletonList("*"));
                cfg.setAllowCredentials(true);
                cfg.setAllowedHeaders(Collections.singletonList("*"));
                cfg.setExposedHeaders(Arrays.asList("Authorization"));
                cfg.setMaxAge(3600L);

                return cfg;
            }
        };
    }
    //whenever we create any new user,  register with our platform
    //we are not gonna store password in oue data base
    //first we will BCRYPT This password then will store in our data base for security purpose
    //like that password will not be readable koi dekha bhi to samjhega nhi

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();

    }
}
