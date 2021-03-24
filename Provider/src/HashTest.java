
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
		
		
				
		// update�� digest �Լ��� ��� �̿��Ͽ�
		// out = hash(�ڽ��� �й�||�ڽ��� �к� (����)||�ڽ��� �̸� (����)) ��� ��� (||�� ���ڸ� �̾���δٴ� ǥ��(concatenation) �̰� ������ �Է��ϴ� ���ڴ� �ƴ�)
		// e.g., 
		// �ڽ��� �й��� 20201111111 �̰�, �кδ� software �׸��� �̸��� hyojinjo ��� �Ʒ��� ���� ��ȣ���� �ؽ� �Լ����� ���ؾ� ��
		// out = hash(20201111111softwarehyojinjo) 
		
		hash.update(Utils.toByteArray(hash_input1));
		hash.update(Utils.toByteArray(hash_input2));
		hash.update(Utils.toByteArray(hash_input3));
		
				
		byte out[] = hash.digest();
		
		System.out.println("md  = " + Utils.toHexString(out)); // �ؽ��Լ� ������� �����
		
	
	}

	
}
