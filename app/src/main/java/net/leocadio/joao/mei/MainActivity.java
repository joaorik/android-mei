package net.leocadio.joao.mei;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.RadioGroup;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static final String ARQUIVO_DE_GRAVACAO = "ArquivoDeGravacao";
    private NumberPicker mNumberPicker;
    private RadioGroup mRadioGroup;
    private EditText mEditTextValor;
    private TextView mTextViewSaldo;
    private Button mButtonConfirmar, mButtonPersonalizar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRadioGroup = findViewById(R.id.radioGroup);
        mNumberPicker = findViewById(R.id.numberPicker);
        mEditTextValor = findViewById(R.id.etValor);
        mTextViewSaldo = findViewById(R.id.tvSaldo);
        mButtonConfirmar = findViewById(R.id.buttonConfirmar);
        mButtonPersonalizar = findViewById(R.id.buttonPersonalizar);

        mNumberPicker.setMinValue(2015);
        mNumberPicker.setMaxValue(2030);

        mNumberPicker.setOnValueChangedListener(mValorAlteradoListener);

        mButtonConfirmar.setOnClickListener(mButtonConfirmarClickListener);

        mButtonPersonalizar.setOnClickListener(mButtonPersonalizarClickListener);

        exibirSaldo(mNumberPicker.getValue());
    }

    private NumberPicker.OnValueChangeListener mValorAlteradoListener = new NumberPicker.OnValueChangeListener() {
        @Override
        public void onValueChange(NumberPicker numberPicker, int oldValue, int newValue) {
            exibirSaldo(newValue);
        }
    };

    private View.OnClickListener mButtonConfirmarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (!mEditTextValor.getText().toString().isEmpty()) {
                float valor = Float.parseFloat(mEditTextValor.getText().toString());
                int ano = mNumberPicker.getValue();

                switch (mRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioAdicionar:
                        adicionarValor(ano, valor);
                    break;

                    case R.id.radioExcluir:
                        excluirValor(ano, valor);
                    break;
                }

                exibirSaldo(ano);
            }
        }
    };

    private View.OnClickListener mButtonPersonalizarClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getBaseContext(), PersonalizarActivity.class);
            startActivity(intent);
        }
    };

    private void adicionarValor(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);

        float novoValor = valorAtual + valor;

        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }

    private void excluirValor(int ano, float valor) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        float valorAtual = sharedPreferences.getFloat(String.valueOf(ano), 0);

        float novoValor = valorAtual - valor;

        if (novoValor < 0) {
            novoValor = 0;
        }

        sharedPreferences.edit().putFloat(String.valueOf(ano), novoValor).apply();
    }

    private void exibirSaldo(int ano) {
        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        float saldo = sharedPreferences.getFloat(String.valueOf(ano), 0);

        mTextViewSaldo.setText(String.format("R$ %.2f", saldo));
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(ARQUIVO_DE_GRAVACAO, Context.MODE_PRIVATE);

        String nomeFantasia = sharedPreferences.getString("nomeFantasia", null);

        if (nomeFantasia != null) {
            setTitle(nomeFantasia);
        }
    }

}
