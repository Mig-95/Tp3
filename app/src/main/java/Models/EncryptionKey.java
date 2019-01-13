//BEN_CORRECTION : Non respect des standards Java de nommage des packages.
//                 De plus, il faut lui ajouter le préfix d'application, soit "ca.csf.pobj.tp3" dans
//                 votre cas.
package Models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EncryptionKey {

    private final int id;
    private final String outputCharacters;
    private final String inputCharacters;

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

    //BEN_CORRECTION : Non respect des standards Java de nommage des fonctions. Devrait être en "camelCase"
    //                 et non pas en "PascalCase".
    //BEN_CORRECTION : Duplication de code facile à régler (voir EncryptText).
    public String DecryptText(String stringToDecrypt) {

        StringBuilder stringToConstruct = new StringBuilder();
        //BEN_CORRECTION : Variable du nom de "c" imprécis.
        for (Character c : stringToDecrypt.toCharArray()) {

            int indexOf = this.outputCharacters.indexOf(c);
            stringToConstruct.append(this.inputCharacters.charAt(indexOf));
        }

        return stringToConstruct.toString();
    }

    //BEN_CORRECTION : Non respect des standards Java de nommage des fonctions. Devrait être en "camelCase"
    //                 et non pas en "PascalCase".
    //BEN_CORRECTION : Duplication de code facile à régler (voir DecryptText).
    public String EncryptText(String stringToEncrypt) {

        StringBuilder stringToDeConstruct = new StringBuilder();
        //BEN_CORRECTION : Variable du nom de "c" imprécis.
        for (Character c : stringToEncrypt.toCharArray()) {

            int indexOf = this.inputCharacters.indexOf(c);
            stringToDeConstruct.append(this.outputCharacters.charAt(indexOf));
        }

        return stringToDeConstruct.toString();
    }
}
