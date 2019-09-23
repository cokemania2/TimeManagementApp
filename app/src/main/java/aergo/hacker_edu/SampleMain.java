package aergo.hacker_edu;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.wallet.Wallet;

public class SampleMain {
	
	//테스트넷 접속 주소
	protected static String endpoint = "testnet.aergo.io:7845";
	//개인키
	protected static String encPrivateKey = "486tPdWFAiUBS98iv46Q2JSkrZyiuxaPmxzi2tXv9jmEomYnfoPi89rUdp55YrcnEf67j5Toc";
	//송신자 주소
	protected static String fromAddress = "AmLbknvUr9N7M5smiy7RwFtYqqSsjCW6HoHoAJTickEJukzPFMEP";
	//패스워드 설정
	protected static String password = "password";
	//수신자 주소
	protected static String toAddress = "AmNzmdLAWBHCGVQUS4DFRxBqaf7PEEXJseiKJm5dhmdBokPyRv5u";
	//수수료 정책
	protected static String fee = "0";

	public static void main(String[] args) {
		
		run();
	}

	public static void run() {
		
		
		//1. Key생성(Account)
		AergoCommon.createAergoKey();
		
		//2. 잔고 조회 
		//getBalances();
		
		//3 sendTransaction  & Transaction, Block 조회 with wallet
		//sendTransaction();
		
		
	}
	
	
	
	//잔고 조회 
	public static void getBalances() {
		
//		AergoClient aergoClient = AergoCommon.getAergoClient(endpoint);
//		
//		AergoQuery.getBalance(aergoClient, fromAddress);
//
//		aergoClient.close();

		Wallet wallet = AergoCommon.getAergoWallet(endpoint);
		
		AergoQuery.getBalance(wallet, fromAddress);
		
		//client 종료
		wallet.close();
	}

	
	//TX 전송 및 조회 used wallet.send
	public static void sendTransaction() {
		//client 생성 
		Wallet wallet = AergoCommon.getAergoWallet(endpoint);
		
		//전송 토큰  
		String amount = "1";
		
		//paylaod data
		String payload = "time_point";
	
		TxHash txhash = AergoTransaction.sendTransaction(wallet, toAddress, password, encPrivateKey, payload, amount, fee);
	
		
		//comfirm을 위해 대기
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		//트랜잭션 조회
		Transaction transactionInfo = AergoQuery.getTransactionInfo(wallet, txhash.getEncoded());
		
		//tx 사이즈
		System.out.println("#tx size : " + transactionInfo.getRawTransaction().toString().length() / 2);
		
		//블록 조회
		AergoQuery.getBlockInfo(wallet, transactionInfo.getBlockHash().toString());
		
		
		//client 종료
		wallet.close();
		
	}

	//원본아님. address=받을 사람 주소(학생), key=보내는 사람 주소(관리자)
	public static void sendTransaction(String address, String key, String payload) {
		//client 생성
		Wallet wallet = AergoCommon.getAergoWallet(endpoint);

		//paylaod data
		String amount = "1";

		TxHash txhash = AergoTransaction.sendTransaction(wallet, address, password, key, payload, amount, fee);


		//comfirm을 위해 대기
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//트랜잭션 조회
		Transaction transactionInfo = AergoQuery.getTransactionInfo(wallet, txhash.getEncoded());

		//tx 사이즈
		System.out.println("#tx size : " + transactionInfo.getRawTransaction().toString().length() / 2);

		//블록 조회
		AergoQuery.getBlockInfo(wallet, transactionInfo.getBlockHash().toString());

		//client 종료
		wallet.close();
	}
	//유저 주소, 관리자 키, 페이로드 만으로 txHash를 얻고 싶을때. 마찬가지로 원본 아님.
	public static TxHash toGetTxHash(String address, String key, String payload) {
		//client 생성
		Wallet wallet = AergoCommon.getAergoWallet(endpoint);

		//paylaod data
		String amount = "1";

		TxHash txhash = AergoTransaction.sendTransaction(wallet, address, password, key, payload, amount, fee);

		//comfirm을 위해 대기
		try {
			Thread.sleep(3000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//트랜잭션 조회
		Transaction transactionInfo = AergoQuery.getTransactionInfo(wallet, txhash.getEncoded());

		//tx 사이즈
		System.out.println("#tx size : " + transactionInfo.getRawTransaction().toString().length() / 2);

		//블록 조회
		AergoQuery.getBlockInfo(wallet, transactionInfo.getBlockHash().toString());

		//client 종료
		wallet.close();

		return txhash;
	}

	//DB에 txList 추가하는 부분
	public static void txListPush(final DatabaseReference ref, final String txhash) {
		// 해당 DB참조의 값변화리스너 추가 한번만 됨.
		ref.addListenerForSingleValueEvent(new ValueEventListener() {
			int count = 0;
			@Override
			public void onDataChange(DataSnapshot dataSnapshot) {
				for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
					String tmpHash;
					tmpHash = snapshot.getValue(String.class);
					count = count+1;
					Log.d("FirebaseTestActivity", "ValueEventListener : " + tmpHash);
				}
				String count_tostring = Integer.toString(count);
				ref.child(count_tostring).setValue(txhash);
			}

			@Override
			public void onCancelled(DatabaseError error) {
				// Failed to read value
				Log.w("Read Firebase database", "Failed to read value.", error.toException());
			}
		});
	}
}




