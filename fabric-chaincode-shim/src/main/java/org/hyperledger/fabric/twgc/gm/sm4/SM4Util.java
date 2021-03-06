package org.hyperledger.fabric.twgc.gm.sm4;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;

public class SM4Util {

    private static final String ALGORITHM_NAME = "SM4";
    private static KeyGenerator kg;

    public SM4Util() throws NoSuchProviderException, NoSuchAlgorithmException {
        if (Security.getProvider(BouncyCastleProvider.PROVIDER_NAME) == null) {
            Security.addProvider(new BouncyCastleProvider());
        }
        kg = KeyGenerator.getInstance(ALGORITHM_NAME, BouncyCastleProvider.PROVIDER_NAME);
    }

    /**
     * SM4加密
     *
     * @param cipher                cipher
     * @param input                 明文数据
     * @param sm4Key                SecretKeySpec
     * @param iv                    初始向量(ECB模式下传NULL), IV must be 16 bytes long
     * @return byte[]
     * @throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException
     */
    public byte[] encrypt(Cipher cipher, byte[] input, SecretKeySpec sm4Key, byte[] iv) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
            IvParameterSpec ivParameterSpec = null;
            if (iv != null) {
                ivParameterSpec = new IvParameterSpec(iv);
            }
            return sm4(input, sm4Key, cipher, ivParameterSpec, Cipher.ENCRYPT_MODE);
    }

    /**
     * SM4解密
     *
     * @param cipher                cipher
     * @param input                 密文数据
     * @param sm4Key                SecretKeySpec
     * @param iv                    初始向量(ECB模式下传NULL), IV must be 16 bytes long
     * @return byte[]
     * @throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException
     */
    public byte[] decrypt(Cipher cipher, byte[] input, SecretKeySpec sm4Key, byte[] iv) throws IllegalBlockSizeException, BadPaddingException, InvalidAlgorithmParameterException, InvalidKeyException {
            IvParameterSpec ivParameterSpec = null;
            if (iv != null) {
                ivParameterSpec = new IvParameterSpec(iv);
            }
            return sm4(input, sm4Key, cipher, ivParameterSpec, Cipher.DECRYPT_MODE);
    }


    private static byte[] sm4(byte[] input, SecretKeySpec sm4Key, Cipher cipher, IvParameterSpec ivParameterSpec, int mode) throws InvalidKeyException, InvalidAlgorithmParameterException, BadPaddingException, IllegalBlockSizeException {
        if (ivParameterSpec == null) {
            cipher.init(mode, sm4Key);
        } else {
            cipher.init(mode, sm4Key, ivParameterSpec);
        }
        return cipher.doFinal(input);
    }

    public static final int DEFAULT_KEY_SIZE = 128;

    public byte[] generateKey() {
        kg.init(DEFAULT_KEY_SIZE, new SecureRandom());
        return kg.generateKey().getEncoded();
    }
}