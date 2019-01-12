// in Windows the path to the file should be something like this:
//
// %HOMEDRIVE%%HOMEPATH%\.dbeaver4\General\.dbeaver-data-sources.xml

import java.io.*;
import java.util.Arrays;
import java.util.Base64;

public class DbeaverDecrypt
{    
    private static final byte[] PASSWORD_ENCRYPTION_KEY = "sdf@!#$verf^wv%6Fwe%$$#FFGwfsdefwfe135s$^H)dg".getBytes();
    private static final String CHARSET = "UTF8";
    
    public static void main(String[] args) throws Exception{
        DbeaverDecrypt simpleStringEncrypter = new DbeaverDecrypt();
        String password = args[0];
        System.err.println("\nEncrypted password: \"" + password + "\"");
        System.out.println(simpleStringEncrypter.decrypt(password));
    }
    
    public DbeaverDecrypt() {
    }

    private void xorStringByKey(byte[] plainBytes) throws UnsupportedEncodingException {
        int keyOffset = 0;

        for(int i = 0; i < plainBytes.length; ++i) {
            byte keyChar = PASSWORD_ENCRYPTION_KEY[keyOffset];
            ++keyOffset;
            if(keyOffset >= PASSWORD_ENCRYPTION_KEY.length) {
                keyOffset = 0;
            }

            plainBytes[i] ^= keyChar;
        }

    }

    public String decrypt(String encryptedString) throws Exception {
        if(encryptedString != null && encryptedString.trim().length() > 0) {
            try {
                byte[] e = Base64.getDecoder().decode(encryptedString);
                this.xorStringByKey(e);
                if(e[e.length - 2] == 0 && e[e.length - 1] == -127) {
                    return new String(e, 0, e.length - 2, "UTF8");
                } else {
                    throw new Exception("Invalid encrypted string");
                }
            } catch (Exception var3) {
                throw new Exception(var3);
            }
        } else {
            throw new IllegalArgumentException("Empty encrypted string");
        }
    }
}

