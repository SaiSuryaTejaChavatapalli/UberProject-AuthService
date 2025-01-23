package com.example.sst.filters;

import com.example.sst.services.JwtService;
import com.example.sst.services.UserDetailsServiceImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.NegatedRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final RequestMatcher uriMatcher=new AntPathRequestMatcher("/api/v1/auth/validate", HttpMethod.GET.name());

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
        System.out.println("Incoming Token:"+token);
        if(token==null){
            // User has not provided any jwt token, hence the request should not go forward.
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
        String email= jwtService.extractEmail(token);
        System.out.println("Incoming email:"+email);
        if(email!=null){
            UserDetails userDetails=userDetailsService.loadUserByUsername(email);
            if(jwtService.validateToken(token,userDetails.getUsername())){
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null);
                // Make the Request Spring boot compatible request
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                // Make Spring remembers this credentials, and access anywhere
                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            }
        }
        filterChain.doFilter(request,response); // similar to middleware next method, calling next middleware
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        RequestMatcher matcher=new NegatedRequestMatcher(uriMatcher);
        return  matcher.matches(request);
    }
}
