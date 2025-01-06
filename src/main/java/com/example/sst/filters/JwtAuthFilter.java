package com.example.sst.filters;

import com.example.sst.services.JwtService;
import com.example.sst.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

   private final JwtService jwtService;
   private final UserDetailsServiceImpl userDetailsService;
   public  JwtAuthFilter(JwtService jwtService, UserDetailsServiceImpl userDetailsService){
       this.jwtService=jwtService;
       this.userDetailsService=userDetailsService;
   }


//  Client-> Filter1-> Filter2 -> Controller
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token=null;
        if(request.getCookies()!=null){
            for (Cookie cookie: request.getCookies()){
                if(cookie.getName().equals("JwtToken")){
                    token=cookie.getValue();
                }
            }
        }

        if(token==null){
            // User has not provided any jwt token, hence the request should not go forward.
        }
        String email= jwtService.extractEmail(token);

        if(email!=null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null);

            }
        }

    }
}
