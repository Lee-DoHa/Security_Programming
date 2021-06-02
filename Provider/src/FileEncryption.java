
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;


public class FileEncryption {

public static void main(String args[]) throws Exception
{
	Security.addProvider(new BouncyCastleProvider());
	

	byte[]        keyBytes = new byte[] {
            0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07,
            0x08, 0x09, 0x0a, 0x0b, 0x0c, 0x0d, 0x0e, 0x0f,
            };

	byte[]          ivBytes = new byte[] {
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00,
            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};

	SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
	IvParameterSpec iv = new IvParameterSpec(ivBytes);

	
	Cipher cipher = null;

		

    cipher =  Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
    cipher.init(Cipher.ENCRYPT_MODE, key, iv);
   
    int BUF_SIZE = 1024;       // buffer size 지정
   
    byte[] buffer = new byte[BUF_SIZE];
    
    FileInputStream fis = new FileInputStream("01.pdf");    // 암호화 할 파일 읽기
    FileOutputStream fos = new FileOutputStream("enc"); // 암호화 후, 출력할 파일
    int read = BUF_SIZE;
   
    while ((read = fis.read(buffer, 0, BUF_SIZE)) == BUF_SIZE) {   
 	    fos.write(cipher.update(buffer, 0, read));
    }
    fos.write(cipher.doFinal(buffer, 0, read));
    
    fis.close();  // 다 읽었으니, stream close 진행
   
    fos.close(); // 출력 끝났으니, stream close 진행
   	
	cipher.init(Cipher.DECRYPT_MODE, key, iv);
	fis = new FileInputStream("enc");  // 복호화를 진행할 파일 읽기
	fos = new FileOutputStream("dec.pdf"); // 복호화 후, 출력할 파일
	   
	while ((read = fis.read(buffer, 0, BUF_SIZE)) == BUF_SIZE) { // 파일 읽으면서 cipher.update진행
		fos.write(cipher.update(buffer, 0, read));
	}
	fos.write(cipher.doFinal(buffer, 0, read));
	
	fis.close();  // 다 읽은 후, stream close
	fos.close(); // 출력 후, stream close 진행
	
    }


}