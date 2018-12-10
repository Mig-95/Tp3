package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionKey {

    private final int id;
    private final String outputCharacters;
    private final String inputCharacters;
    private String decrypted;
    private  String encrypted;

    @JsonCreator
    public EncryptionKey(@JsonProperty("id") int id, @JsonProperty("outputCharacters") String outputCharacters, @JsonProperty("inputCharacters") String inputCharacters) {
        this.id = id;
        this.outputCharacters = outputCharacters;
        this.inputCharacters = inputCharacters;
    }

    @JsonProperty("id")
    public int getId() {
        return id;
    }

    @JsonProperty("outputCharacters")
    public String getOutputCharacters() {
        return outputCharacters;
    }

    @JsonProperty("inputCharacters")
    public String getInputCharacters() {
        return inputCharacters;
    }

    public String DecryptText(String stringToDecrypt) {



       return decrypted;
    }

    public String EncryptText(String stringToEncrypt) {



        return encrypted;
    }
}
