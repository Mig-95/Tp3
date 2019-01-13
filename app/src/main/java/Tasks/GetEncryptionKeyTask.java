//BEN_CORRECTION : Non respect des standards Java de nommage des packages.
//                 De plus, il faut lui ajouter le préfix d'application, soit "ca.csf.pobj.tp3" dans
//                 votre cas.
package Tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.function.Consumer;

import Models.EncryptionKey;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetEncryptionKeyTask extends AsyncTask<Integer, Void, WebServiceResult<EncryptionKey>> {

    private static final String URL = "https://m1t2.csfpwmjv.tk/api/key/%d";

    private final Consumer<EncryptionKey> onSuccess;
    private final Runnable onServerError;
    private final Runnable onConnectivityError;

    //BEN_REVIEW : Pour plus de lisibilité, mettre les paramètres de cette fonction sur plusieurs lignes.
    public static void run(Consumer<EncryptionKey> onSuccess, Runnable onServerError, Runnable onConnectivityError, Integer keyToFetch) {
        new GetEncryptionKeyTask(onSuccess, onServerError, onConnectivityError).execute(keyToFetch);
    }

    private GetEncryptionKeyTask(Consumer<EncryptionKey> onSuccess, Runnable onServerError, Runnable onConnectivityError) {
        this.onSuccess = onSuccess;
        this.onServerError = onServerError;
        this.onConnectivityError = onConnectivityError;
    }

    @Override
    protected WebServiceResult<EncryptionKey> doInBackground(Integer... integers) {
        if (integers.length != 1) throw new IllegalArgumentException("You must provide a number"); //BEN_REVIEW : Message d'erreur imprécis.

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(String.format(URL, integers[0]))
                .build();

        Call call = client.newCall(request);

        try {

            Response execute = call.execute();
            if (execute.isSuccessful()) {
                String executeBody = execute.body().string();

                ObjectMapper mapper = new ObjectMapper();
                EncryptionKey encryptionKey = mapper.readValue(executeBody, EncryptionKey.class);

                return WebServiceResult.ok(encryptionKey);

            } else {
                return WebServiceResult.connectivityError();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return WebServiceResult.connectivityError();
        }
    }

    @Override
    protected void onPostExecute(WebServiceResult<EncryptionKey> webServiceResult) {
        if (webServiceResult.isServerError()) {
            onServerError.run();
        }
        else if (webServiceResult.isConnectivityError()) {
            onConnectivityError.run();
        }
        else {
            onSuccess.accept(webServiceResult.getResult());
        }
    }
}
