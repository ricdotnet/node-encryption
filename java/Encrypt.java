import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class Encrypt {
  Cipher cipher;
  SecretKey secretKey;
  FileInputStream fileInputStream;
  FileOutputStream fileOutputStream;

  public Encrypt () {
    try {
      cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
      secretKey = new SecretKeySpec(HexBin.encode(readFileContent("../key.txt").getBytes()).getBytes(), "AES");

    } catch (NoSuchPaddingException | NoSuchAlgorithmException exception) {
      exception.printStackTrace();
    }
  }

  void doEncryption () throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
    cipher.init(ENCRYPT_MODE, secretKey);
    byte[] encrypted = cipher.doFinal(readFileContent("../data.txt").getBytes(StandardCharsets.UTF_8));

    writeFileContent("../data.txt.encrypted", HexBin.encode(encrypted));
  }

  void doDecryption () throws InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
    cipher.init(DECRYPT_MODE, secretKey);
    byte[] decrypted = cipher.doFinal(HexBin.decode(readFileContent("../data.txt.encrypted")));

    writeFileContent("../data.txt.decrypted", new String(decrypted));
  }

  // read file xxx.txt
  String readFileContent (String fileName) {
    String content = "";
    try {
      fileInputStream = new FileInputStream(fileName);
      byte[] bytes = new byte[fileInputStream.available()];
      fileInputStream.read(bytes);
      fileInputStream.close();
      content = new String(bytes);
    } catch (IOException exception) {
      exception.printStackTrace();
    }

    return content;
  }

  void writeFileContent (String fileName, String content) {
    try {
      fileOutputStream = new FileOutputStream(fileName);
      fileOutputStream.write(content.getBytes());
      fileOutputStream.close();
    } catch (IOException exception) {
      exception.printStackTrace();
    }
  }

  public static void main (String[] args) throws IllegalBlockSizeException, BadPaddingException, InvalidKeyException, UnsupportedEncodingException {
    Encrypt encryptor = new Encrypt();
    Scanner scanner = new Scanner(System.in);

    System.out.println("Would you like to encrypt or decrypt? (e/d) ");
    System.out.println("To generate a new key, enter 'generate:key'");
    String input = scanner.nextLine();
    if (input.equals("e")) {
      encryptor.doEncryption();
      System.out.println("Encryption complete!");
      System.out.println("Encrypted file: data.txt.encrypted");
    }

    if (input.equals("d")) {
      encryptor.doDecryption();
      System.out.println("Decryption complete!");
      System.out.println("Decrypted file: data.txt.decrypted");
    }

    if (input.equals("generate:key")) {
      System.out.println("Generating new key...");
      byte[] key = new byte[4];
      for (int i = 0; i < 4; i++) {
        key[i] = (byte) (Math.random() * 26 + 97);
      }
      encryptor.writeFileContent("../key.txt", HexBin.encode(key));
    }
  }
}
