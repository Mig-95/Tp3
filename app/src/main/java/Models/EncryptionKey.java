package Models;

public class EncryptionKey {

    public final int id;
    public final String outputCharacters;
    public final String inputCharacters;


    public EncryptionKey(int id, String outputCharacters, String inputCharacters) {
        this.id = id;
        this.outputCharacters = outputCharacters;
        this.inputCharacters = inputCharacters;
    }
}
