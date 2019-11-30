package HIS_E2.app_sanidad.model;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 *
 * @author Eduardo
 */
public class Cifrador {

	Base64.Encoder encoder = Base64.getEncoder();
	Base64.Decoder decoder = Base64.getDecoder();
	static boolean existingKey=false;
	static Cipher cipher=null;
	static Key aesKey=null;
	static String key = "";
	public static final int MODULO_CIFRADOR_CESAR = 62;

/**
 * Cifra un string dado.
 * @param a el string a cifrar.
 * @return el string dado.
 * @throws Exception si los datos son incorrectos.
 */
public static String cifrar(String a) throws Exception {

		if (!existingKey) {
			cipher = createCipher();
			existingKey = true;
		}
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(a.getBytes());
        byte[] encryptedB64 = Base64.getEncoder().encode(encrypted);
        return new String(encryptedB64);
	}

	/**
	 * Metodo para descifrar.
	 * @param string
	 * @return el string descifrado
	 * @throws Exception si los datos son incorrectos
	 */
	public static String descifrar(String string) throws Exception {
        byte[] encryptedB64 = Base64.getDecoder().decode(string);

        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        String decrypted = new String(cipher.doFinal(encryptedB64));

        return decrypted;
	}

	/**
	 * Crea un cifrador.
	 * @return un cifrador.
	 * @throws NoSuchAlgorithmException si no esta disponible el cifrador.
	 * @throws NoSuchPaddingException padding not existing.
	 * @throws IOException si ocurre una excepción I/O.
	 */
	public static Cipher createCipher()
			throws NoSuchAlgorithmException, NoSuchPaddingException, IOException {
	    key = "VE9SRkVLRVJLUExITFNSSVRQU0dJTkdOVFRIUEtST1I=";
	    key = decodeBase64(key);
	    key = decryptCaesar(key);
        aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        return cipher;
	}

	/**
	 * Cifra un hash.
	 * @param x string.
	 * @return el hash.
	 * @throws Exception sin falla el hash.
	 */
	public static String cifrarHash(String x) throws Exception{
		byte[] bytesOfMessage = x.getBytes("UTF-8");

		MessageDigest md = MessageDigest.getInstance("MD5");
		byte[] thedigest = md.digest(bytesOfMessage);
		return new String(thedigest);
	}

	/**
	 * Decodifica un string.
	 * @param texto el string.
	 * @return el string descifrado.
	 * @throws UnsupportedEncodingException si falla la codificación.
	 */
	public static String decodeBase64(String texto)
			throws UnsupportedEncodingException {
	    byte[] archivo = Base64.getDecoder().decode(texto);
	    return new String(archivo);
	}

	/**
	 * Devuelve un desencriptador.
	 * @param text el string.
	 * @return el desencriptador.
	 * @throws UnsupportedEncodingException si falla la codificación.
	 */
	public static String decryptCaesar(String text)
			throws UnsupportedEncodingException {
		String alphabet =
			"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
		String shiftKey = "MTQ=";
		int shiftKeyInt = Integer.parseInt(decodeBase64(shiftKey));
		String plainText = "";
		for (int i = 0; i < text.length(); i++) {
            int charPosition = alphabet.indexOf(text.charAt(i));
            int keyVal = (charPosition - shiftKeyInt) % MODULO_CIFRADOR_CESAR;
            if (keyVal < 0) {
                keyVal = alphabet.length() + keyVal;
            }
            char replaceVal = alphabet.charAt(keyVal);
            plainText += replaceVal;
        }
        return plainText;
    }
}
