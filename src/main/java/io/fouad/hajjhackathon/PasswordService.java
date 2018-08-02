package io.fouad.hajjhackathon;

import com.google.crypto.tink.Aead;
import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetReader;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadFactory;
import org.mindrot.jbcrypt.BCrypt;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

@ApplicationScoped
public class PasswordService
{
    private static final String FIXED_ASSOCIATED_DATA = "W4i5Er3bDSP0";
    private Aead aead;

    @PostConstruct
    public void init() throws Exception
    {
        AeadConfig.register();
        InputStream keysetInputStream = Thread.currentThread()
                                  .getContextClassLoader()
                                  .getResourceAsStream("io/fouad/hajjhackathon/tink_keyset.json");

        KeysetHandle keysetHandle = CleartextKeysetHandle.read(
                JsonKeysetReader.withInputStream(keysetInputStream));

        aead = AeadFactory.getPrimitive(keysetHandle);
    }

    public byte[] hashAndEncryptPassword(String plainTextPassword) throws GeneralSecurityException
    {
        String passwordHash = BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());

        return aead.encrypt(passwordHash.getBytes(StandardCharsets.UTF_8),
                            FIXED_ASSOCIATED_DATA.getBytes(StandardCharsets.UTF_8));
    }

    public boolean verifyPassword(String plainPassword, byte[] encryptedPassword) throws GeneralSecurityException
    {
        String passwordHash = new String(aead.decrypt(encryptedPassword,
                        FIXED_ASSOCIATED_DATA.getBytes(StandardCharsets.UTF_8)), StandardCharsets.UTF_8);

        return BCrypt.checkpw(plainPassword, passwordHash);
    }
}
