package dev.tmanti.backend.utilities;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CryptoUtils {

    private static byte[] hash_secret_key = new byte[16];
    private static byte[] jwt_secret_key = new byte[16];
    private static final char[] HEX_ARRAY = "0123456789ABCDEF".toCharArray();

    public CryptoUtils(byte[] hash_key, byte[] jwt_key){
        hash_secret_key = hash_key;
        jwt_secret_key = jwt_key;
    }

    public CryptoUtils(String hash_key, String jwt_key){
        hash_secret_key = StringtoBytes(hash_key);
        jwt_secret_key = StringtoBytes(jwt_key);
    }

    public static String Hash(String toHash) {
        KeySpec spec = new PBEKeySpec(toHash.toCharArray(), hash_secret_key, 65536, 128);
        SecretKeyFactory factory;
        byte[] hash = null;
        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hash = factory.generateSecret(spec).getEncoded();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (hash == null) return "";
        return BytesToHex(hash);
    }

    public static String BytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = HEX_ARRAY[v >>> 4];
            hexChars[j * 2 + 1] = HEX_ARRAY[v & 0x0F];
        }
        return new String(hexChars);
    }

    public static byte[] StringtoBytes(String hex){
        int len = hex.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hex.charAt(i), 16) << 4)
                    + Character.digit(hex.charAt(i+1), 16));
        }
        return data;
    }

    public static String createJWT(UUID id, int priv){
        Algorithm algo = Algorithm.HMAC256(jwt_secret_key);

        Map<String, Object> claims = new HashMap<>();
        //System.out.println("ID: " + id.toString());
        claims.put("id", id.toString());
        claims.put("priv", priv);

        String token = JWT.create()
                .withPayload(claims)
                .sign(algo);

        return token;
    }

    public static int authorizeJWT(String token){
        int auth = -1;

        try{
            //System.out.println("Token: "+ token);
            Algorithm algo = Algorithm.HMAC256(jwt_secret_key);
            JWTVerifier verifier = JWT.require(algo)
                    .build();
            DecodedJWT jwt = verifier.verify(token);
            Map<String, Claim> claims = jwt.getClaims();
            Claim id_claim = claims.get("id");
            String id_str = id_claim.asString();
            UUID id = UUID.fromString(id_str);
            Claim priv_claim = claims.get("priv");
            int priv = priv_claim.asInt();

            DatabaseInteface db = DatabaseInteface.getInstance();
            User user = db.GetUser(id);
            if(user != null && priv == user.getPrivilege()){
                auth = user.getPrivilege();
            }
        } catch(JWTVerificationException e){
            e.printStackTrace();
        }

        return auth;
    }

}
