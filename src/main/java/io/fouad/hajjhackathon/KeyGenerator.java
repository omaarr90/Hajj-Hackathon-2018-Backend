package io.fouad.hajjhackathon;

import com.google.crypto.tink.CleartextKeysetHandle;
import com.google.crypto.tink.JsonKeysetWriter;
import com.google.crypto.tink.KeysetHandle;
import com.google.crypto.tink.aead.AeadConfig;
import com.google.crypto.tink.aead.AeadKeyTemplates;

public class KeyGenerator
{
    public static void main(String[] args) throws Exception
    {
        AeadConfig.register();
        KeysetHandle keysetHandle = KeysetHandle.generateNew(AeadKeyTemplates.AES256_GCM);
        CleartextKeysetHandle.write(keysetHandle, JsonKeysetWriter.withOutputStream(System.out));
    }
}