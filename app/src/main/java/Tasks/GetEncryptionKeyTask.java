package Tasks;

import android.os.AsyncTask;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import Models.EncryptionKey;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GetEncryptionKeyTask extends AsyncTask<Integer, Void, EncryptionKey> {

    private static final String URL = "https://m1t2.csfpwmjv.tk/api/key/%d";
    private List<Listener> listeners = new Vector<>();

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    protected EncryptionKey doInBackground(Integer... integers) {
        if (integers.length != 1) {
            throw new IllegalArgumentException("You must provide a number");
        }
        EncryptionKey encryptionKey = new EncryptionKey(0,"","");
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
                encryptionKey = mapper.readValue(executeBody, EncryptionKey.class);

            } else {
                //todo erreur de serveur. Avertir nos listeners dans "onPostExecute"
            }
        } catch (IOException e) {
            e.printStackTrace();
            //todo erreur de connectivité. Avertir nos listeners dans "onPostExecute"
        }

        return encryptionKey;
    }

    @Override
    protected void onPostExecute(EncryptionKey encryptionKey) {
        for (Listener listener: listeners) {
            listener.onEncryptionKeyReceive(encryptionKey);
        }
    }

    public interface Listener {
        void onEncryptionKeyReceive(EncryptionKey encryptionKey);

    }
}
