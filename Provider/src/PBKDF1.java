import java.security.MessageDigest; 
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class PBKDF1 {
	public static void main(String srgs[]) throws Exception{
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");

		String pass = "password"; 
		byte[] p = Utils.toByteArray(pass);
		byte[] s = new byte[]
		{0x78, 0x57, (byte) 0x8e, 0x5a, 0x5d, 0x63, (byte) 0xcb, 0x06};
		int c = 1000;
		int dkLen = 16;
		
		hash.update(p);
		hash.update(s); //p��s�� concat����
		byte[] out = hash.digest();
		
		for(int i = 0; i < c-1; i++) {
			out = hash.digest(out);     // c-1��ŭ hash �ݺ��ϸ鼭 out�� ����
		}
		
		System.out.print("DK = " + Utils.toHexString(out, dkLen)); //dkLen��ŭ�� DK���
	}
}
