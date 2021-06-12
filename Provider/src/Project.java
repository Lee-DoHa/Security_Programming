import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.security.Security;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
import java.util.Scanner;
import java.io.IOException;


public class Project {
	
	public static void fileEnc(String mode, byte[] salt, byte[] derivedKey, String path, String o_path) throws Exception{
		
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
			
		byte[]          ivBytes = new byte[] {
	            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00,
	            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
		
		SecretKeySpec key = new SecretKeySpec(derivedKey, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		Cipher cipher = null;
		cipher =  Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

		
	    FileInputStream fis = new FileInputStream(path);    // ��ȣȭ �� ���� �б�
	    FileOutputStream fos = new FileOutputStream(o_path); // ��ȣȭ ��, ����� ����
	    
		
		if(mode.equals("enc")) {
			File file = new File(path);
		    File file2 = new File(o_path);
		    long fileSize = file.length();
		    long encSize;
			
			int BUF_SIZE = (int)fileSize/100*5; 
			byte[] buffer = new byte[BUF_SIZE];
			int read = BUF_SIZE;
			cipher.init(Cipher.ENCRYPT_MODE, key, iv);
			
			
			hash.update(derivedKey);
			hash.update(salt);
			byte[] password_check = hash.digest();
			
			System.out.println(Utils.toHexString(password_check));
			
		    
		    fos.write(salt);
		    fos.write(password_check);
		    
		    while ((read = fis.read(buffer, 0, BUF_SIZE)) == BUF_SIZE) {   
		 	    fos.write(cipher.update(buffer, 0, read));
		 	    encSize = file2.length();
		 	    System.out.println("��ȣȭ ����� = " + encSize + "/" + fileSize);
		    }
		    fos.write(cipher.doFinal(buffer, 0, read));
		    
		    
		    System.out.println("��ȣȭ�� ���������� ó���Ǿ����ϴ�!");
		    
		    fis.close();  // �� �о�����, stream close ����
		    fos.close(); // ��� ��������, stream close ����
		}
	}
	
	public static void fileDec(String mode, byte[] salt, byte[] derivedKey, String path, String o_path) throws Exception{
		
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
			
		byte[]          ivBytes = new byte[] {
	            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00,
	            0x07, 0x06, 0x05, 0x04, 0x03, 0x02, 0x01, 0x00};
		
		SecretKeySpec key = new SecretKeySpec(derivedKey, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivBytes);
		Cipher cipher = null;
		cipher =  Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");

		int BUF_SIZE = 1024; 
		byte[] buffer = new byte[BUF_SIZE];
	    FileInputStream fis = new FileInputStream(o_path);    // ��ȣȭ �� ���� �б�
	    FileOutputStream fos = new FileOutputStream(path); // ��ȣȭ ��, ����� ����
	    int read = BUF_SIZE;
		
		if(mode.equals("dec")) {
			
			cipher.init(Cipher.DECRYPT_MODE, key, iv);
		
			hash.update(derivedKey);
			hash.update(salt);
			byte[] password_check2 = hash.digest();
			byte[] check = new byte[28];
			
			read = fis.read(check);   
			
			
			System.out.println(Utils.toHexString(check));
			System.out.println(Utils.toHexString(password_check2));
			
			
		    while ((read = fis.read(buffer, 0, BUF_SIZE)) == BUF_SIZE) {   
		 	    fos.write(cipher.update(buffer, 0, read));
		    }
		    fos.write(cipher.doFinal(buffer, 0, read));
		    
		    File file = new File(path);
		    long fileSize = file.length();
		    System.out.println(fileSize);
		    
		    fis.close();  // �� �о�����, stream close ����
		    fos.close(); // ��� ��������, stream close ����
		}
	}
	public static void main(String args[]) throws Exception
	{
		
		Security.addProvider(new BouncyCastleProvider());
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("��带 �Է��ϼ��� :  ");
		String mode = scanner.next();
		
		System.out.print("��й�ȣ�� �Է��ϼ��� :  ");
		String password = scanner.next();
		
		System.out.print("������ ��θ� �Է��ϼ��� :  ");
		String path = scanner.next();
		
		byte[] salt = new byte[] {0x78, 0x57, (byte) 0x8e, 0x5a, 0x5d, 0x63, (byte) 0xcb, 0x06};
		int c = 1000;
		int dkLen = 16;
		
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
		
		byte[] input = new byte[password.length() + salt.length];
		
		System.arraycopy(Utils.toByteArray(password), 0, input, 0, password.length());
		System.arraycopy(salt, 0, input, password.length(), salt.length);
		
		hash.update(input);
		
		for(int i = 0; i < c-1; i++) {
			byte t[] = hash.digest();
			hash.update(t);
		}
		byte output[] = hash.digest();
		byte[] keyBytes = new byte[16];
		
		System.arraycopy(output, 0, keyBytes, 0, dkLen);

		fileEnc(mode, salt, keyBytes, path, "enc");
		
		
		System.out.print("��带 �Է��ϼ��� :  ");
		mode = scanner.next();
		
		System.out.print("��й�ȣ�� �Է��ϼ��� :  ");
		password = scanner.next();
		
		System.out.print("������ ��θ� �Է��ϼ��� :  ");
		String path2 = scanner.next();
		
		fileDec(mode, salt, keyBytes, path2, "enc");
		
		
		

    }

}
