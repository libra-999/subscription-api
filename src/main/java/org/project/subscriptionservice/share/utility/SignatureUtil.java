package org.project.subscriptionservice.share.utility;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

public class SignatureUtil {

    public static KeyPair generateKeyPair() throws NoSuchAlgorithmException, InvalidAlgorithmParameterException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("EC");
        keyPairGenerator.initialize(new ECGenParameterSpec("secp256r1"));
        return keyPairGenerator.generateKeyPair();
    }

    public static String sign(byte[] data, PrivateKey privateKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature sign = Signature.getInstance("SHA256withECDSA");
        sign.initSign(privateKey);
        sign.update(data);
        byte[] signature = sign.sign();
        return Base64.getEncoder().encodeToString(signature);
    }

    public static boolean verify(byte[] data, String base64, PublicKey publicKey) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        Signature ver = Signature.getInstance("SHA256withECDSA");
        ver.initVerify(publicKey);
        ver.update(data);
        byte[] verify = Base64.getDecoder().decode(base64);
        return ver.verify(verify);
    }
}
