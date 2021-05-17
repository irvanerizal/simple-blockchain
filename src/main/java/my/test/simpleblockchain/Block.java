package my.test.simpleblockchain;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Block {

    private static final Logger LOGGER = Logger.getLogger(Block.class.getName());

    private String hash;
    private String previousHash;
    private String data;
    private long timestamp;
    private int nonce;

    public Block(String data, String previousHash, long timeStamp) {
        this.previousHash = previousHash;
        this.data = data;
        this.timestamp = timeStamp;
        this.hash = calculateBlockHash();

    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getPreviousHash() {
        return previousHash;
    }

    public void setPreviousHash(String previousHash) {
        this.previousHash = previousHash;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String calculateBlockHash(){

        String dataToHash = previousHash
                + Long.toString(timestamp)
                + Integer.toString(nonce)
                + data;

        MessageDigest digest = null;
        byte[] bytes = null;
        try {

            digest = MessageDigest.getInstance("SHA-256");
            bytes = digest.digest(dataToHash.getBytes(StandardCharsets.UTF_8));

//        } catch (NoSuchAlgorithmException | UnsupportedEncodingException ex){
        } catch (NoSuchAlgorithmException ex){
            LOGGER.log(Level.SEVERE, ex.getMessage());
        }
        StringBuffer sb = new StringBuffer();
        for (byte b : bytes){
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String mineBlock(int prefix) {
        String prefixString = new String(new char[prefix]).replace('\0', '0');
        while (!hash.substring(0, prefix).equals(prefixString)){
            nonce ++;
            hash = calculateBlockHash();
        }
        return hash;
    }

    @Override
    public String toString() {
        return "Block{" +
                "hash='" + hash + '\'' +
                ", previousHash='" + previousHash + '\'' +
                ", data='" + data + '\'' +
                ", timestamp=" + timestamp +
                ", nonce=" + nonce +
                '}';
    }
}

