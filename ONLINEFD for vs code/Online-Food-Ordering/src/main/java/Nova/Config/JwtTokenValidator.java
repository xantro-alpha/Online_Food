////Sochiye JWT token ek party ka digital entry pass hai. Aur JWT validator uss party ka security guard hai.
////JWT validator ka kaam yeh sunishchit (ensure) karna hai ki jo digital pass (token) server
////ko mila hai, woh asli, valid, aur un-tampered hai, taaki system secure rahe. Agar sab sahi hai,
////toh "Welcome!", warna "Entry Denied!".

package Nova.Config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.crypto.SecretKey;
import java.awt.*;
import java.io.IOException;
import java.security.Key;
import java.util.List;

public class JwtTokenValidator  extends OncePerRequestFilter {


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String jwt = request.getHeader(JwtConstant.JWT_HEADER);

        ///Bearer token
        //// The full authorization header is expected in the format "Bearer <token>".
//// This line strips the "Bearer " part (the first 7 characters) to isolate the actual token string.
//// Authorization header mein token "Bearer " ke saath aata hai (e.g., "Bearer eyJ...").
//// Yeh line "Bearer " prefix ko hatakar sirf actual token nikaalti hai.
        if(jwt!=null){
            jwt = jwt.substring(7);

            try {
                SecretKey key= Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
               /// Claims claims= Jwts.parser().setSigningKey(Key).build().parseClaimsJws(jwt).getBody();
                Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(jwt).getPayload();

                String email=String.valueOf(claims.get("email"));
                String authorities=String.valueOf((claims.get("authorities")));


/// role_customer, role_admin ,
///Maan lo authorities ek simple text (String) hai, jisme user ke saare roles comma (,) se alag karke likhe hain.
///Example: String authorities = "ROLE_ADMIN,ROLE_USER";
               /// Toh yeh line:
               /// List<GrantedAuthority> auth = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
///..bas itna kaam karti hai:
///Yeh uss text "ROLE_ADMIN,ROLE_USER" ko todkar, uske har hisse ("ROLE_ADMIN" aur
///"ROLE_USER") ko ek special permission object banati hai aur un sabko ek list (auth) mein daal deti hai.
///In short: Yeh line user ke roles waali simple text string ko ek proper "permissions ki list" mein
///badal deti hai, jise Spring Security samajh sakta hai aur use kar sakta hai.

                List<GrantedAuthority> auth= AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
                Authentication authentication=new UsernamePasswordAuthenticationToken(email,null,auth);
                SecurityContextHolder.getContext().setAuthentication(authentication);


            }

////Socho aapka code ek security guard (try block) hai jo ek digital ID card (JWT token) ko check kar raha hai.
////catch block ka matlab:
///
/// catch (Exception e): Agar try block mein token validation ke dauraan koi bhi problem
            /// aati hai—jaise token ka signature galat hai, token expire ho chuka hai, ya token a-symmetric hai (tampered)—toh program crash hone ke bajaye iss catch block mein aa jaata hai. 👮‍♂️

/// throw new BadCredentialsException("invalid token......"): Yahan hum ek nayi, saaf-suthri exception phenk rahe hain. Hum system ko ye nahi bata rahe ki "signature galat tha" ya "token expire ho gaya tha". Hum bas itna bol rahe hain, "Bhai, jo token diya hai, woh kharab hai (Bad Credentials), isliye entry band."
///
/// Aisa Kyun Karte Hain? (Why is it done this way?)
///
/// Yeh ek achhi security practice hai. Agar hum attacker ko asli reason bata denge (e.g., "Signature failed"), toh usse system ke baare mein hints mil sakte hain. BadCredentialsException use karke hum bas ek simple "access denied" message dete hain, jisse system secure rehta hai.

            catch (Exception e){
                throw new BadCredentialsException("invalid token......");
            }
        }

        filterChain.doFilter(request,response);


    }
}
