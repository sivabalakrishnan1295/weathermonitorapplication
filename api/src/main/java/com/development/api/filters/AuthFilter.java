package com.development.api.filters;

import com.development.api.Constants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        String AuthHeader = httpRequest.getHeader("Authorization");

        if(AuthHeader != null){
            String[] AuthHeaderArray = AuthHeader.split("Bearer");
            if(AuthHeaderArray.length >1 && AuthHeaderArray[1] != null){
                String token = AuthHeaderArray[1];
                try{
                    Claims claims = Jwts.parser().setSigningKey(Constants.SECRET_KEY_API)
                            .parseClaimsJws(token).getBody();
                    httpRequest.setAttribute("User_id",Integer.parseInt(claims.get("User_id").toString()));
                }catch (Exception e){
                    httpResponse.sendError(HttpStatus.FORBIDDEN.value(),e.getMessage());
                }
            }else {
                httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Auth token must be Bearer");
                return ;

            }
        }else{
            httpResponse.sendError(HttpStatus.FORBIDDEN.value(),"Auth token must be provided");
            return ;
        }

        filterChain.doFilter(servletRequest,servletResponse);
    }
}
