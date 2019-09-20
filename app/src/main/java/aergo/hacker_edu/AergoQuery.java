package aergo.hacker_edu;

import android.util.Log;

import java.math.BigInteger;

import hera.api.model.AccountAddress;
import hera.api.model.AccountState;
import hera.api.model.Aer;
import hera.api.model.Block;
import hera.api.model.BlockHash;
import hera.api.model.BlockchainStatus;
import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.client.AergoClient;
import hera.wallet.Wallet;

public class AergoQuery {
	
	
	//wallet으로 잔고 조회 

	public static void getBalance(Wallet wallet, String address) {

		//조회할 주소 설정
		AccountAddress accountAddress = AccountAddress.of(address);
		//Account 상태 조회
		AccountState accountState = wallet.getAccountState(accountAddress);
		System.out.println("==== Wallet Used ===== ");
		System.out.println(">>>>>>>> 주소(Address) :: "+accountState.getAddress().getEncoded().toString());
		System.out.println(">>>>>>>> 잔고(Balance) :: " + accountState.getBalance().toString());

	}

	// 1 aergo = 1,000,000,000 gaer = 1,000,000,000,000,000,000 aer
	// aer to gear
	// 잔액 조회를 하는데 aer를 가져와서 gear로 환산해주는 함수
	public static String getBalance_(Wallet wallet, String address) {
		//조회할 주소 설정
		AccountAddress accountAddress = AccountAddress.of(address);
		//Account 상태 조회
		AccountState accountState = wallet.getAccountState(accountAddress);
		BigInteger balance = accountState.getBalance().getValue();

		BigInteger aerToGear = balance.divide(new BigInteger("1000000000"));
		Log.d("TEST BigInteger", "value is" + aerToGear.toString());
		return String.format("%,d", aerToGear);
	}
	
	//aergoClient로 잔고 조회
	/*
	public static void getBalance(AergoClient aergClient, String address) {
		
		//조회할 주소 설정
		AccountAddress accountAddress = AccountAddress.of(address);
		
		//Account 상태 조회
		AccountState accountState = aergClient.getAccountOperation().getState(accountAddress);
		
		System.out.println("==== AergoClient Used ===== ");
		System.out.println(">>>>>>>> 주소(Address) :: "+accountState.getAddress().getEncoded().toString());
		System.out.println(">>>>>>>> 잔고(Balance) :: " + accountState.getBalance().toString());
		
	}
	*/
	
	//Wallet Transaction 조회
	public static Transaction getTransactionInfo(Wallet wallet, String txHash) {

		//조회할 tx설정
		TxHash tx = new TxHash(txHash);

		//트랜잭션 조회
		Transaction transactionInfo = wallet.getTransaction(tx);

		System.out.println("==== Wallet Used ===== ");
		System.out.println(">>>>>>>> 송신자 주소:: "+transactionInfo.getSender().toString());
		System.out.println(">>>>>>>> 수신자 주소:: "+transactionInfo.getRecipient().toString());
		System.out.println(">>>>>>>> 전송 토큰량:: "+transactionInfo.getAmount().getValue().toString());
		System.out.println(">>>>>>>> TransactionHash :: "+transactionInfo.getHash().toString());
		System.out.println(">>>>>>>> blockhash:: "+transactionInfo.getBlockHash().toString());
		System.out.println(">>>>>>>> payload:: "+new String(transactionInfo.getPayload().getValue()));


		return transactionInfo;

	}

	
	//AergoClient Transaction 조회
	public static Transaction getTransactionInfo(AergoClient aergClient, String txHash) {
		//조회할 tx설정
		TxHash tx = new TxHash(txHash);

		//트랜잭션 조회
		Transaction transactionInfo = aergClient.getTransactionOperation().getTransaction(tx);

		System.out.println("==== AergClient Used ===== ");
		System.out.println(">>>>>>>> 송신자 주소:: "+transactionInfo.getSender().toString());
		System.out.println(">>>>>>>> 수신자 주소:: "+transactionInfo.getRecipient().toString());
		System.out.println(">>>>>>>> 전송 토큰량:: "+transactionInfo.getAmount().getValue().toString());
		System.out.println(">>>>>>>> TransactionHash :: "+transactionInfo.getHash().toString());
		System.out.println(">>>>>>>> blockhash:: "+transactionInfo.getBlockHash().toString());
		System.out.println(">>>>>>>> payload:: "+new String(transactionInfo.getPayload().getValue()));

		return transactionInfo;

	}
	
	//wallet block 조회
	public static void getBlockInfo(Wallet wallet, String blockHash){
		
		//조회할 블록 세팅
		BlockHash block = new BlockHash(blockHash);
		     
		//aergoClient를 통해 블록정로를 조회합니다. 
		Block blockinfo = wallet.getBlock(block);
		
		//블록체인 상태 조회
		BlockchainStatus blockchainStatus = wallet.getBlockchainStatus();
		
		
		System.out.println("==== Wallet Used ===== ");
		System.out.println(">>>>>>>> 최신블록해시:: "+blockchainStatus.getBestBlockHash());
		System.out.println(">>>>>>>> 최신블록높이:: "+blockchainStatus.getBestHeight());
		System.out.println(">>>>>>>> 현재 합의알고리즘:: "+blockchainStatus.getConsensus());
		
		System.out.println(">>>>>>>> 블록높이:: "+blockinfo.getBlockNumber());
		System.out.println(">>>>>>>> 이전블록hash:: "+blockinfo.getPreviousHash());
		
		System.out.println(">>>>>>>> 블록hash:: "+blockinfo.getHash());
		System.out.println(">>>>>>>> tx개수:: " +blockinfo.getTransactions().size());
		System.out.println(">>>>>>>> chainId:: " +blockinfo.getChainId());

			
	}
	
	//AergoClient  block 조회
	public static void getBlockInfo(AergoClient aergoClient, String blockHash){
		
		//조회할 블록 세팅
		BlockHash block = new BlockHash(blockHash);
		     
		//aergoClient를 통해 블록정로를 조회합니다. 
		Block blockinfo = aergoClient.getBlockOperation().getBlock(block);
		
		//블록체인 상태 조회
		BlockchainStatus blockchainStatus = aergoClient.getBlockchainOperation().getBlockchainStatus();
		
		System.out.println("==== AergoClient Used ===== ");
		System.out.println(">>>>>>>> 최신블록해시:: "+blockchainStatus.getBestBlockHash());
		System.out.println(">>>>>>>> 최신블록높이:: "+blockchainStatus.getBestHeight());
		System.out.println(">>>>>>>> 현재 합의알고리즘:: "+blockchainStatus.getConsensus());
		
		System.out.println(">>>>>>>> 블록높이:: "+blockinfo.getBlockNumber());
		System.out.println(">>>>>>>> 이전블록hash:: "+blockinfo.getPreviousHash());
		
		System.out.println(">>>>>>>> 블록hash:: "+blockinfo.getHash());
		System.out.println(">>>>>>>> tx개수:: " +blockinfo.getTransactions().size());
		System.out.println(">>>>>>>> 블록생성시각:: " +blockinfo.getTimestamp());
		System.out.println(">>>>>>>> chainId:: " +blockinfo.getChainId());
		
			
	}
}
