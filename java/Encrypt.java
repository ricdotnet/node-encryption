import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

import java.io.*;

public class Encrypt {
  Cipher cipher;
  FileInputStream fileInputStream;
  FileOutputStream fileOutputStream;

//  byte[] iv = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 1, 2, 3, 4, 5, 6};

  public Encrypt () {
    try {
//      IvParameterSpec ivParameterSpec = new IvParameterSpec(iv);
      cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");

      SecretKey secretKey = new SecretKeySpec(HexBin.encode(readFileContent("../key.txt").getBytes()).getBytes(), "AES");

      // decryption
//      cipher.init(DECRYPT_MODE, secretKey, ivParameterSpec);  // aes-ecb does not need initialization vector
      cipher.init(DECRYPT_MODE, secretKey);
      byte[] decrypted = cipher.doFinal(HexBin.decode(readFileContent("../data.txt.encrypted")));
//      System.out.println(new String(decrypted));
      writeFileContent("../data.txt.decrypted", new String(decrypted));

    } catch (NoSuchPaddingException | NoSuchAlgorithmException | InvalidKeyException
             | IllegalBlockSizeException | BadPaddingException exception) {
      exception.printStackTrace();
    }
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

  public static void main (String[] args) {
    new Encrypt();
  }
}
