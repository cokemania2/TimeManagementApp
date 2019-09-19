package aergo.hacker_edu;

import hera.api.model.AccountAddress;
import hera.api.model.Aer;
import hera.api.model.Aer.Unit;
import hera.api.model.Authentication;
import hera.api.model.BytesValue;
import hera.api.model.Fee;
import hera.api.model.RawTransaction;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.key.AergoKey;
import hera.key.Signer;
import hera.transaction.NonceProvider;
import hera.transaction.SimpleNonceProvider;
import hera.wallet.Wallet;


public class AergoTransaction {
	
	
		// 트랜잭션 build & commit  
		public static TxHash buildCommitTransaction(Wallet wallet, String toAddress, String password, String encPrivateKey, String payload, String amount, String fee) {
			
			//전송할 주소
			AccountAddress accountAddress = AccountAddress.of(toAddress);
			
			//수수료를 지불한 wallet설정 
			AergoKey adminKey = null;
			
			//키세팅 
			adminKey = AergoKey.of(encPrivateKey, password);
			
			//사용할 지갑을 저장
			wallet.saveKey(adminKey, password);
			
			//해당 지갑을 사용하기 위해 인증객체 생성 
			Authentication auth = Authentication.of(adminKey.getAddress(), password);
			
			//지갑 사용을 위해 unlock 
			wallet.unlock(auth); 
			
			
			//트랜잭션 빌드
			System.out.println(">>>>>>>> 트랜잭션 빌드");
			RawTransaction rawTransaction = RawTransaction.newBuilder(
					wallet.getChainIdHash())  //blockchainID
			        .from(wallet.getAccount()) //지갑 주소 
			        .to(accountAddress) // 전송할 주소
			        .amount(amount, Unit.AER) // 토큰 전송 시 값 입력, 불필요할 경우 0으로 
			        .nonce(wallet.incrementAndGetNonce()) // nonce설
			        .fee(Fee.of(Aer.of("100", Unit.AER), 5)) // 수수료 
			        .payload(new BytesValue(payload.getBytes())) // 저장할 hash값 
			        .build();
		  
		    Transaction signedTransacion = wallet.sign(rawTransaction); // 설정된 지갑으 개인키로 트랜잭션 서명 
		    System.out.println(">>>>>>>> 트랜잭션 전송");
		    TxHash txHash = wallet.commit(signedTransacion); //서명된 트랙잭션을 전송함
		    
			return txHash;
		}
		
		//wallet.send 하기
		public static TxHash sendTransaction(Wallet wallet, String toAddress, String password, String encPrivateKey, String payload, String amount, String fee) {
			
			
			
			//전송할 주소
			AccountAddress accountAddress = AccountAddress.of(toAddress);
	
			//수수료를 지불한 wallet설정 
			AergoKey adminKey = null;
			
			//키세팅 
			adminKey = AergoKey.of(encPrivateKey, password);
			
			//사용할 지갑을 저장
			wallet.saveKey(adminKey, password);
			
			//해당 지갑을 사용하기 위해 인증객체 생성 
			Authentication auth = Authentication.of(adminKey.getAddress(), password);

			//지갑 사용을 위해 unlock 
			wallet.unlock(auth); 
			
			//chainId set
			wallet.cacheChainIdHash();
			
			System.out.println(">>>>>>>> 트랜잭션 Send");
			TxHash txHash = wallet.send(accountAddress , Aer.of(amount, Unit.GAER), Fee.of(Aer.of(fee, Unit.AER), 5), new BytesValue(payload.getBytes()));


			return txHash;
		}
		
		
		// 트랜잭션 build & commit  
		public static TxHash aergoClientBuildCommitTransaction(AergoClient aergoClient, String toAddress, String password, String encPrivateKey, String payload, String amount, String fee) {
			
			//전송할 주소
			AccountAddress accountAddress = AccountAddress.of(toAddress);
			
			//수수료 지불할 Account 키 설정 			
			Signer signer = AergoKey.of(encPrivateKey, password);
			
			//해당 지갑을 사용하기 위해 인증객체 생성 
			Authentication auth = Authentication.of(signer.getPrincipal(), password);
			
			//Account 사용을 위해 unlock 
			aergoClient.getKeyStoreOperation().unlock(auth);
			
			NonceProvider nonceProvider = new SimpleNonceProvider();

		    // bind nonce state
		    nonceProvider.bindNonce(aergoClient.getAccountOperation().getState(signer.getPrincipal()));

			//트랜잭션 빌드
			System.out.println(">>>>>>>> 트랜잭션 빌드");
			RawTransaction rawTransaction = RawTransaction.newBuilder(
					aergoClient.getBlockchainOperation().getChainIdHash())  //blockchainID
			        .from(signer.getPrincipal()) //지갑 주소 
			        .to(accountAddress) // 전송할 주소
			        .amount(amount, Unit.AER) // 토큰 전송 시 값 입력, 불필요할 경우 0으로 
			        .nonce(nonceProvider.incrementAndGetNonce(signer.getPrincipal())) // nonce설정
			        .fee(Fee.of(Aer.of("100", Unit.AER), 5)) // 수수료 
			        .payload(new BytesValue(payload.getBytes())) // 저장할 hash값 
			        .build();

			//트랜잭션 서명
		    Transaction signedTransacion = signer.sign(rawTransaction);
		    
		    System.out.println(">>>>>>>> 트랜잭션 전송");
		    
		    TxHash txHash = aergoClient.getTransactionOperation().commit(signedTransacion); //서명된 트랙잭션을 전송함
		    
			return txHash;
		}
		
		//aergo name service 
		public static TxHash createNameToAddr(AergoClient aergoClient, String password, String encPrivateKey, String name) {
			
			//Key 생성 			
			Signer signer = AergoKey.of(encPrivateKey, password);
			
			//해당 지갑을 사용하기 위해 인증객체 생성 
			Authentication auth = Authentication.of(signer.getPrincipal(), password);
			
			//Account 사용을 위해 unlock 
			aergoClient.getKeyStoreOperation().unlock(auth);
			
			
			 // create an nonce provider
		    final NonceProvider nonceProvider = new SimpleNonceProvider();

		    // bind nonce state
		    nonceProvider.bindNonce(aergoClient.getAccountOperation().getState(signer.getPrincipal()));
			
			//chainID 설정 
			aergoClient.cacheChainIdHash(aergoClient.getBlockchainOperation().getChainIdHash());
			
			//이름 설정 
			TxHash txHash = aergoClient.getAccountOperation().createName(signer, name, nonceProvider.incrementAndGetNonce(signer.getPrincipal()));
			
			//comfirm을 위해 대기
			try {
				Thread.sleep(3000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(">>>>> txHash ::: "+ txHash.getEncoded());
			
			System.out.println("Address 설정한 name의 Owner Address ::: "+ aergoClient.getAccountOperation().getNameOwner(name).getEncoded());
			

			return txHash;
			
		}
		
		
}
