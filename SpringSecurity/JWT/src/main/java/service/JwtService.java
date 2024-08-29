package service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
	
	private final String signatureKey = "f3ef4f303d10f3dff46420489545aad6e5c3dc2435731b92be9ca975baa6b393";
	private final int expiration = 300000; // 5 minutes in milliseconds
	
	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails) {
		return Jwts.builder()
				.setClaims(extraClaims)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setSubject(userDetails.getUsername())
				.signWith(getSignKey(), SignatureAlgorithm.HS256)
				.compact();
	}

	private Key getSignKey() {
		byte[] key = Decoders.BASE64.decode(signatureKey);
		return Keys.hmacShaKeyFor(key);
	}
	
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaims(token);
		return claims != null ? claimResolver.apply(claims) : null;
	}
	
	public String extractName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	
	public Claims extractAllClaims(String token) {
		try {
			return Jwts.parser()
					.setSigningKey(getSignKey())
					.build()
					.parseClaimsJws(token)
					.getBody();
		} catch (Exception e) {
			// Handle exception or log error
			return null;
		}
	}
	
	public boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}

	public boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractName(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
	}

}