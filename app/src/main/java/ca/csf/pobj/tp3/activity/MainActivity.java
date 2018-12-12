package ca.csf.pobj.tp3.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.text.InputFilter;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Random;

import Models.EncryptionKey;
import Tasks.GetEncryptionKeyTask;
import ca.csf.pobj.tp3.R;
import ca.csf.pobj.tp3.utils.view.CharactersFilter;
import ca.csf.pobj.tp3.utils.view.KeyPickerDialog;

public class MainActivity extends AppCompatActivity {

    private static final int KEY_LENGTH = 5;
    private static final int MAX_KEY_VALUE = (int) Math.pow(10, KEY_LENGTH) - 1;
    private static final String ID_KEY = "ID_KEY";
    private static final String OUTPUTCHAR_KEY = "OUTPUTCHAR_KEY";
    private static final String INPUTCHAR_KEY = "INPUTCHAR_KEY";

    private View rootView;
    private EditText inputEditText;
    private TextView outputTextView;
    private TextView currentKeyTextView;
    private ProgressBar progressBar;
    private EncryptionKey encryptionKey;
    private Random rand = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rootView = findViewById(R.id.rootView);
        progressBar = findViewById(R.id.progressbar);
        inputEditText = findViewById(R.id.input_edittext);
        inputEditText.setFilters(new InputFilter[]{new CharactersFilter()});
        outputTextView = findViewById(R.id.output_textview);
        currentKeyTextView = findViewById(R.id.current_key_textview);
        fetchSubstitutionCypherKey(rand.nextInt(MAX_KEY_VALUE));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(ID_KEY, encryptionKey.getId());
        outState.putString(OUTPUTCHAR_KEY, encryptionKey.getOutputCharacters());
        outState.putString(INPUTCHAR_KEY, encryptionKey.getInputCharacters());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        encryptionKey = new EncryptionKey(savedInstanceState.getInt(ID_KEY), savedInstanceState.getString(OUTPUTCHAR_KEY), savedInstanceState.getString(INPUTCHAR_KEY));
    }

    private void showKeyPickerDialog(int key) {
        KeyPickerDialog.make(this, KEY_LENGTH)
                .setKey(key)
                .setConfirmAction(this::fetchSubstitutionCypherKey)
                .show();
    }

    private void showCopiedToClipboardMessage() {
        Snackbar.make(rootView, R.string.text_copied_output, Snackbar.LENGTH_SHORT).show();
    }

    private void showConnectionError() {
        Snackbar.make(rootView, R.string.text_connectivity_error, Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.text_activate_wifi, (view) -> showWifiSettings())
                .show();
        hideProgressBar();

    }

    private void showServerError() {
        Snackbar.make(rootView, R.string.text_server_error, Snackbar.LENGTH_INDEFINITE)
                .show();
        hideProgressBar();
    }

    public void showKeyMissingError() {
        Snackbar.make(rootView, R.string.key_not_downloaded_error, Snackbar.LENGTH_INDEFINITE)
                .show();
    }

    private void showWifiSettings() {
        Intent intent = new Intent(Settings.ACTION_WIFI_SETTINGS);
        startActivity(intent);
    }

    private void fetchSubstitutionCypherKey(int key) {
        showProgressBar();
        GetEncryptionKeyTask.run(this::onEncryptionKeyReceive, this::showServerError, this::showConnectionError, key);
    }

    @SuppressWarnings("ConstantConditions")
    private void putTextInClipboard(String text) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newPlainText(getResources().getString(R.string.clipboard_encrypted_text), text));
    }

    public void onEncryptButtonClicked(View view) {
        if (this.encryptionKey == null) {
            showKeyMissingError();
        }
        else {
            outputTextView.setText(encryptionKey.EncryptText(inputEditText.getText().toString()));
        }
    }

    public void onDecryptButtonClicked(View view) {
        if (this.encryptionKey == null) {
            showKeyMissingError();
        }
        else {
            outputTextView.setText(encryptionKey.DecryptText(inputEditText.getText().toString()));
        }
    }

    public void onKeySelectButtonClicked(View view) {
        if (encryptionKey == null) {
            showKeyMissingError();
        }
        else {
            showKeyPickerDialog(encryptionKey.getId());
        }
    }

    public void onCopyButtonClicked(View view) {
        putTextInClipboard(outputTextView.getText().toString());
        showCopiedToClipboardMessage();
    }

    private void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    private void hideProgressBar() {
        progressBar.setVisibility(View.INVISIBLE);
    }

    public void onEncryptionKeyReceive(EncryptionKey encryptionKey) {
        this.encryptionKey = encryptionKey;
        currentKeyTextView.setText(String.format(getString(R.string.text_current_key), encryptionKey.getId()));
        hideProgressBar();
    }
}
