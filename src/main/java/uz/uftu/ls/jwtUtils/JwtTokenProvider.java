package uz.uftu.ls.jwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import uz.uftu.ls.domain.dto.LoginResponseDTO;
import uz.uftu.ls.domain.dto.ResponseDTO;
import uz.uftu.ls.domain.entity.User;
import uz.uftu.ls.exceptions.UserException;
import uz.uftu.ls.repository.UserRepository;

import java.util.Base64;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    @Value("${jwt.token.secret}")
    private String secret;
    @Value("${jwt.token.validity}")
    private long validityMilliSecond;

    public JwtTokenProvider(UserDetailsService userDetailsService, UserRepository userRepository) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public ResponseDTO<LoginResponseDTO> createToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserException("Bunday foydalanuvch mavjud emas");
        }
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", user.getRole());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityMilliSecond);
        ResponseDTO<LoginResponseDTO> responseDto = new ResponseDTO<>();
        responseDto.setSuccess(true);
        responseDto.setMessage("Token yaratildi");
        responseDto.setRecordsTotal(1L);
        LoginResponseDTO loginResponseDto = new LoginResponseDTO();
        loginResponseDto.setId(user.getId());
        loginResponseDto.setFirstName(user.getFirstName());
        loginResponseDto.setLastName(user.getLastName());
        loginResponseDto.setUsername(user.getUsername());
        loginResponseDto.setRole(user.getRole());
        loginResponseDto.setToken(Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret).compact());
        loginResponseDto.setFileStorage(user.getFileStorage());
        responseDto.setData(loginResponseDto);
        return responseDto;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return !claimsJws.getBody().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUser(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String getUser(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }


}