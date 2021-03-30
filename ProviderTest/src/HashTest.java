
import java.security.MessageDigest;
import java.security.Security;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Hex;
public class HashTest  {
	
	public static void main(String args[]) throws Exception
	{
		Security.addProvider(new BouncyCastleProvider());
		
		
		MessageDigest hash = MessageDigest.getInstance("SHA256", "BC");
		
		String hash_input1 = "20170793";
		String hash_input2 = "Software";
		String hash_input3 = "LeeJongKeun";
		
		
				
		// update와 digest 함수를 모두 이용하여
		// out = hash(자신의 학번||자신의 학부 (영문)||자신의 이름 (영문)) 결과 출력 (||은 문자를 이어붙인다는 표시(concatenation) 이고 실제로 입력하는 문자는 아님)
		// e.g., 
		// 자신의 학번이 20201111111 이고, 학부는 software 그리고 이름이 hyojinjo 라면 아래와 같은 암호학적 해쉬 함수값을 구해야 함
		// out = hash(20201111111softwarehyojinjo) 
		
		hash.update(Utils.toByteArray(hash_input1));
		hash.update(Utils.toByteArray(hash_input2));
		hash.update(Utils.toByteArray(hash_input3));
		
				
		byte out[] = hash.digest();
		
		System.out.println("md  = " + Utils.toHexString(out)); // 해쉬함수 결과값을 출력함
		
	
	}

	
}
