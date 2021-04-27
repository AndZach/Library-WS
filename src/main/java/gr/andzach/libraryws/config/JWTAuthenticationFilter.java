
package gr.andzach.libraryws.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties.Jwt;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import static gr.andzach.libraryws.config.SecurityConstants.*;

import gr.andzach.libraryws.model.User; //import io.jsonwebtoken.JwtBuilder;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	 @Autowired
	 private AuthenticationManager authenticationManager;

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl("/login");
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);

			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					creds.getUsername(), creds.getPassword(), new ArrayList<>()));

			return auth;
			
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chan,
			Authentication auth) throws IOException {
		List<String> claimList = new ArrayList<String>();
		claimList.add(auth.getName());
		String role = "ROLE_"+auth.getAuthorities().toString().replace("[", "").replace("]", "").toUpperCase();
//		claimList.add(String.valueOf( auth.getAuthorities());
		claimList.add(role);
		
		String token = JWT.create()
//				.withSubject(((User) auth.getPrincipal()).getUsername())
//				.withSubject("Authorities:"+auth.getAuthorities().toString())
				.withClaim("Claim", claimList)
//				.withClaim("Authority", auth.getAuthorities().toString())
//				.withClaim("Authorities", (List<GrantedAuthority>) auth.getAuthorities())
				.withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
				.sign(Algorithm.HMAC512(SECRET.getBytes()));

		String body = ((User) auth.getPrincipal()).getUsername() + " " + token;

		res.getWriter().write(body);
		res.getWriter().flush();

	}

}
