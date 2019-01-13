//BEN_CORRECTION : Non respect des standards Java de nommage des packages.
//                 De plus, il faut lui ajouter le préfix d'application, soit "ca.csf.pobj.tp3" dans
//                 votre cas.
package Tasks;

//BEN_REVIEW : J'ai ignoré ce fichier, car ceci est mon code.

public class WebServiceResult<T> {

    private T result;
    private boolean isServerError;
    private boolean isConnectivityError;

    public static <T> WebServiceResult<T> ok(T result) {
        return new WebServiceResult<>(result, false, false);
    }

    public static <T> WebServiceResult<T> serverError() {
        return new WebServiceResult<>(null, true, false);
    }

    public static <T> WebServiceResult<T> connectivityError() {
        return new WebServiceResult<>(null, false, true);
    }

    private WebServiceResult(T result, boolean isServerError, boolean isConnectivityError) {
        this.result = result;
        this.isServerError = isServerError;
        this.isConnectivityError = isConnectivityError;
    }

    public T getResult() {
        return result;
    }

    public boolean isServerError() {
        return isServerError;
    }

    public boolean isConnectivityError() {
        return isConnectivityError;
    }
}
