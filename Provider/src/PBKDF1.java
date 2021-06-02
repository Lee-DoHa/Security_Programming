import java.security.MessageDigest; 
import java.security.Security;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;

public class PBKDF1 {
	public static void main(String srgs[]) throws Exception{
		
		

		String p = "password"; 
		byte[] s = new byte[] {0x78, 0x57, (byte) 0x8e, 0x5a, 0x5d, 0x63, (byte) 0xcb, 0x06};
		int c = 1000;
		int dkLen = 16;
		
		Security.addProvider(new BouncyCastleProvider());
		MessageDigest hash = MessageDigest.getInstance("SHA1", "BC");
		
		byte[] input = new byte[p.length() + s.length];
		
		System.arraycopy(Utils.toByteArray(p), 0, input, 0, p.length());
		System.arraycopy(s, 0, input, p.length(), s.length);
		
		System.out.println("Input : " + Utils.toHexString(input));
		
		hash.update(input);
		
				
		for(int i = 0; i < c-1; i++) {
			byte t[] = hash.digest();
			hash.update(t);
		}
		byte output[] = hash.digest();
		byte[] result = new byte[16];
		
		System.arraycopy(output, 0, result, 0, dkLen);
		
		System.out.println("output : " + Utils.toHexString(output));
		System.out.println("result : " + Utils.toHexString(result));
	}
}
