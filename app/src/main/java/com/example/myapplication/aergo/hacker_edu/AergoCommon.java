package com.example.myapplication.aergo.hacker_edu;

import java.util.concurrent.TimeUnit;

import hera.client.AergoClient;
import hera.client.AergoClientBuilder;
import hera.key.AergoKey;
import hera.key.AergoKeyGenerator;
import hera.wallet.Wallet;
import hera.wallet.WalletBuilder;
import hera.wallet.WalletType;

public class AergoCommon {



    //AergoClient
    public static AergoClient getAergoClient(String endpoint) {

        AergoClient aergoClient = new AergoClientBuilder()
                .withNonBlockingConnect() //netty
                .withEndpoint(endpoint) //접속주소 입력
                .withTimeout(1000L, TimeUnit.MILLISECONDS) // 타임아웃 설정
                .withRetry(3, 5, TimeUnit.SECONDS) // 재접속
                .build();

        return aergoClient;
    }


    //wallet
    public static Wallet getAergoWallet(String endpoint) {

        Wallet wallet = new WalletBuilder()
                .withNonBlockingConnect() //netty
                .withEndpoint(endpoint) //접속주소 입력
                .withRefresh(3, 1000L, TimeUnit.MILLISECONDS) //nonce체크 시간
                .withTimeout(1000L, TimeUnit.MILLISECONDS) // 타임아웃 설정
                .withRetry(3, 5, TimeUnit.SECONDS) // 재접속
                .build(WalletType.Naive); // 월렛타입


        return wallet;

    }

    // 키 생성 함수
    public static AergoKey createAergoKey() {
        //키생성
        AergoKey key = new AergoKeyGenerator().create();

        //생성된 키와 비빌번호를 토대로 privateKey추출, password 부분은 필요한 값으로 수정하여 사용
        String encPrivatKey = key.export("password").toString();

        System.out.println(">>>>>>>> private encKey :: "+ encPrivatKey);
        System.out.println(">>>>>>>> Address :: "+ key.getAddress());

		/*
		 * 사용자로부터 키와 패스워드를 받을경우 키로 import
		AergoKey key_2 = null;
		key = AergoKey.of("encPrivatKey", "password");
		 */

        return key;
    }

}

