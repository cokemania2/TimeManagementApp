package aergo.hacker_edu;

import hera.api.model.Transaction;
import hera.api.model.TxHash;
import hera.wallet.Wallet;


public class SampleMain_sangyoon {

    //테스트넷 접속 주소
    protected static String endpoint = "testnet.aergo.io:7845";

    //개인키
    protected static String encPrivateKey = "485XeGg5e8xrFsvEZFrmRoPrAsgEnzw5bLtFWoTaVGbR2mCeAewoAr3eNNFPm5FFuAHZXBz9z"; //관리자 키

    //송신자 주소
    //protected static String fromAddress = "AmMDaD8pFMiL5i8Bcqpvy7sZj6L8pcHEiXJm5zmYp8dkJF2cKojD"; //관리자 주소

    //패스워드 설정
    protected static String password = "password";

    //수신자 주소
    //protected static String toAddress = "AmMbSKr6YV8XA6Xvt3USamFcgnEDesPn6mLyVbNCwJamhGby82YE"; //상윤 주소
    protected static String toAddress = "AmPfZP5Jr8YvsZDdaDwbCnEvutASJJ5WZ1j87v5mZ6ukX3FXQKb9";
    //수수료 정책
    protected static String fee = "0";

    public static void main(String[] args) {
        run();
    }

    public static void run() {

        //1. Key생성(Account)
        //AergoCommon.createAergoKey();

        //2. 잔고 조회
        //getBalances();

        //3 sendTransaction  & Transaction, Block 조회 with wallet
        sendTransaction();

    }



    //잔고 조회
    /*
    public static void getBalances() {

//		AergoClient aergoClient = AergoCommon.getAergoClient(endpoint);
//
//		AergoQuery.getBalance(aergoClient, fromAddress);
//
//		aergoClient.close();


        Wallet wallet = aergo.hacker_edu.AergoCommon.getAergoWallet(endpoint);

         aergo.hacker_edu.AergoQuery.getBalance(wallet, fromAddress);

        //client 종료
        wallet.close();

    }*/


    //TX 전송 및 조회 used wallet.send
    public static void sendTransaction() {
        //client 생성
        Wallet wallet = AergoCommon.getAergoWallet(endpoint);

        //전송 토큰
        String amount = "1";

        //paylaod data
        String payload = "Time:11:58:00_Msg:I'm_SangYoon";
        TxHash txhash = AergoTransaction.sendTransaction(wallet, toAddress, password, encPrivateKey, payload, amount, fee);

        //comfirm을 위해 대기
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        //트랜잭션 조회
        Transaction transactionInfo = aergo.hacker_edu.AergoQuery.getTransactionInfo(wallet, txhash.getEncoded());

        //tx 사이즈
        System.out.println("#tx size : " + transactionInfo.getRawTransaction().toString().length() / 2);

        //블록 조회
        AergoQuery.getBlockInfo(wallet, transactionInfo.getBlockHash().toString());


        //client 종료
        wallet.close();

    }

}
