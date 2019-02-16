import com.sun.org.apache.xml.internal.security.exceptions.Base64DecodingException;
import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jcajce.provider.digest.SHA3;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Main {
    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, NoSuchPaddingException, InvalidAlgorithmParameterException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException, Base64DecodingException {
        String mensaje = "La contraseña del usuario es supersecreta";

        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(mensaje.getBytes());
        byte[] digest = md.digest();
        String resulttadoMD5 = new String(digest);
        System.out.println("MD5 ->"+resulttadoMD5);
        System.out.println("MD5 (legible)->"+DatatypeConverter
                .printHexBinary(digest).toUpperCase());

        md = MessageDigest.getInstance("SHA-1");
        md.update(mensaje.getBytes());
        digest = md.digest();
        String resulttadoSHA1 = new String(digest);
        System.out.println("SHA-1 ->"+resulttadoSHA1);
        System.out.println("SHA-1 (legible)->"+DatatypeConverter
                .printHexBinary(digest).toUpperCase());

        SHA3.Digest512 digest512 = new SHA3.Digest512();
        digest512.update(mensaje.getBytes());
        digest = digest512.digest();
        String resulttadoSHA3 = new String(digest);
        System.out.println("SHA3_512 ->"+resulttadoSHA3);
        System.out.println("SHA3_512 (legible)->"+DatatypeConverter
                .printHexBinary(digest).toUpperCase());

        SHA3.Digest384 digest384 = new SHA3.Digest384();
        digest512.update(mensaje.getBytes());
        digest = digest384.digest();
        String resulttadoSHA384 = new String(digest);
        System.out.println("SHA3_384 ->"+resulttadoSHA384);
        System.out.println("SHA3_384 (legible)->"+DatatypeConverter
                .printHexBinary(digest).toUpperCase());

        String key_segurrissima = "1234admin1234adm"; // <-- la clave tiene que ser de 16 bytes/caracteres (128 bits)¬¬
        String semilla="1234567890123456"; //<-- y esto de 16 Bytes/caracteres

        IvParameterSpec ivParameterSpec = new IvParameterSpec(semilla.getBytes("UTF-8"));
        SecretKeySpec secretKeySpec = new SecretKeySpec(key_segurrissima.getBytes("UTF-8"), "AES");

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivParameterSpec);

        byte[] encrypted = cipher.doFinal(mensaje.getBytes());
        System.out.println("AES ->"
                + DatatypeConverter
                .printHexBinary(encrypted).toUpperCase());
        Cipher cipher2 = Cipher.getInstance("AES/CBC/PKCS5PADDING");
        cipher2.init(Cipher.DECRYPT_MODE, secretKeySpec, ivParameterSpec);
        byte[] original = cipher2.doFinal(encrypted);
        System.out.println("AES ORIGINAL ->"+ new String(original)+ " ");

    }
}
