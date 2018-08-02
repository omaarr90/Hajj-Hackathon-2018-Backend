package io.fouad.hajjhackathon;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.*;

@ApplicationScoped
public class JwtService
{
    private PrivateKey privateKey;

    @PostConstruct
    public void init()
    {
        try
        {
            InputStream is = Thread.currentThread()
                                   .getContextClassLoader()
                                   .getResourceAsStream("io/fouad/hajjhackathon/privatekey.pem");

            InputStreamReader isr = new InputStreamReader(is, StandardCharsets.UTF_8);
            BufferedReader br = new BufferedReader(isr);
            List<String> lines = new ArrayList<>();

            String line;
            while((line = br.readLine()) != null) lines.add(line);
            br.close();

            StringBuilder sb = new StringBuilder();
            for(int i = 1; i < lines.size() - 1; i++) sb.append(lines.get(i));
            byte[] privateKeyBytes = Base64.getDecoder().decode(sb.toString());

            PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(privateKeyBytes);
            KeyFactory kf = KeyFactory.getInstance("RSA");
            privateKey = kf.generatePrivate(spec);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    public String generateJwt(String username, String role)
    {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);

        return Jwts.builder()
                   .setSubject(username)
                   .setClaims(claims)
                   .signWith(SignatureAlgorithm.RS512, privateKey)
                   .compact();
    }

    public boolean verifyJwt(String compactJws)
    {
        if(compactJws == null) return false;

        try
        {
            Jwts.parser().setSigningKey(privateKey).parseClaimsJws(compactJws);
            return true;
        }
        catch(Exception e)
        {
            return false;
        }
    }

    public String extractUsername(String compactJws)
    {
        return Jwts.parser().setSigningKey(privateKey).parseClaimsJws(compactJws).getBody().getSubject();
    }

    public String extractRole(String compactJws)
    {
        return (String) Jwts.parser().setSigningKey(privateKey).parseClaimsJws(compactJws).getBody().get("role");
    }
}
