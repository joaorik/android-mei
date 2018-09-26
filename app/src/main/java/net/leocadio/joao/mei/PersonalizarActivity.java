package net.leocadio.joao.mei;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PersonalizarActivity extends AppCompatActivity {

    private Button mButtonCadastrar;
    private EditText mEditTextNomeFantasia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personalizar);

        mEditTextNomeFantasia = findViewById(R.id.editTextNomeFantasia);

        mButtonCadastrar = findViewById(R.id.buttonCadastrar);
        mButtonCadastrar.setOnClickListener(mButtonCadastrarListener);
    }

    private View.OnClickListener mButtonCadastrarListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            String nomeFantasia = mEditTextNomeFantasia.getText().toString();

            if (!nomeFantasia.isEmpty()) {
                getSharedPreferences(MainActivity.ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE).edit().putString("nomeFantasia", nomeFantasia).apply();
            }
        }
    };

}
