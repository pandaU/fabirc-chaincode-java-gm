package org.hyperledger.fabric.twgc.gm.sm3;

import org.bouncycastle.crypto.digests.SM3Digest;

import java.util.Arrays;


public class SM3Util {

    private SM3Util() {

    }


    public static byte[] hash(SM3Digest digest, byte[] srcData) {
        digest.update(srcData, 0, srcData.length);
        byte[] hashVal = new byte[digest.getDigestSize()];
        digest.doFinal(hashVal, 0);
        return hashVal;
    }


    public static boolean verify(SM3Digest digest, byte[] srcData, byte[] sm3HashVal) {
        byte[] hashVal = hash(digest, srcData);
        return Arrays.equals(hashVal, sm3HashVal);
    }
}
