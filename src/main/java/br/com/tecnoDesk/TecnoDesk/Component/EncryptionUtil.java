package br.com.tecnoDesk.TecnoDesk.Component;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.springframework.stereotype.Component;
import java.util.Base64;

@Component
public class EncryptionUtil {

	    private static final String AES = "AES";
	    
	    private SecretKey secretKey;
	    
	    public EncryptionUtil() throws Exception {
	        this.secretKey = generateKey();
	    }
	    
	    private SecretKey generateKey() throws Exception {
	        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES);
	        keyGenerator.init(128);
	        return keyGenerator.generateKey();
	    }

	    public String encrypt(String data) throws Exception {
	        Cipher cipher = Cipher.getInstance(AES);
	        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
	        byte[] encryptedData = cipher.doFinal(data.getBytes());
	        return Base64.getEncoder().encodeToString(encryptedData);
	    }

	    public String decrypt(String encryptedData) throws Exception {
	        Cipher cipher = Cipher.getInstance(AES);
	        cipher.init(Cipher.DECRYPT_MODE, secretKey);
	        byte[] decryptedData = cipher.doFinal(Base64.getDecoder().decode(encryptedData));
	        return new String(decryptedData);
	    }
	}

