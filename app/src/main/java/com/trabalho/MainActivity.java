package com.trabalho;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Elementos da interface
    EditText edtPeso, edtAltura;
    TextView tvIMCValor, tvIMCStatus;
    ImageView imgResultado;
    ImageView imgDesnutricao, imgNormal, imgSobrepeso;
    Button btnCalcular, btnReset;
    // Armazenar as cores padrão dos textos
    int corPadraoStatus;
    int corPadraoValor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Liga os componentes da interface com o código (findViewById)
        edtPeso = findViewById(R.id.edtPeso);
        edtAltura = findViewById(R.id.edtAltura);
        tvIMCValor = findViewById(R.id.tvIMCValor);
        tvIMCStatus = findViewById(R.id.tvIMCStatus);
        imgResultado = findViewById(R.id.imgResultado);
        btnCalcular = findViewById(R.id.btnCalcular);
        btnReset = findViewById(R.id.btnReset);
        imgDesnutricao = findViewById(R.id.imgDesnutricao);
        imgNormal = findViewById(R.id.imgNormal);
        imgSobrepeso = findViewById(R.id.imgSobrepeso);
        // salva a cor padrão definida no XML
        corPadraoStatus = tvIMCStatus.getCurrentTextColor();
        corPadraoValor = tvIMCValor.getCurrentTextColor();
        // Define ações dos botões
        btnCalcular.setOnClickListener(v -> calcularIMC());
        btnReset.setOnClickListener(v -> resetarCampos());
    }
        //Realiza o cálculo do IMC e atualiza a interface com o resultado
    @SuppressLint("DefaultLocale")
    private void calcularIMC() {
        String pesoStr = edtPeso.getText().toString();
        String alturaStr = edtAltura.getText().toString();
        // Verifica se os campos estão preenchidos
        if (pesoStr.isEmpty() || alturaStr.isEmpty()) {
            Toast.makeText(this, "Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }

        double peso = Double.parseDouble(pesoStr);
        double altura = Double.parseDouble(alturaStr);
        // Evita divisão por zero
        if (altura == 0) {
            Toast.makeText(this, "Altura inválida!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Cálculo do IMC
        double imc = peso / (altura * altura);
        tvIMCValor.setText(String.format("%.1f", imc));
        // Verifica a faixa de classificação do IMC e atualiza o layout
        if (imc < 18.5) {
            tvIMCStatus.setText("Desnutrição");
            tvIMCStatus.setTextColor(Color.parseColor("#CCAD00")); // amarelo
            tvIMCValor.setTextColor(Color.parseColor("#CCAD00")); // amarelo
            imgResultado.setImageResource(R.drawable.desnutricao);
            atualizarBordaIMC("Desnutrição");
        } else if (imc < 24.9) {
            tvIMCStatus.setText("Peso Normal");
            tvIMCStatus.setTextColor(Color.parseColor("#32CD32")); // verde
            tvIMCValor.setTextColor(Color.parseColor("#32CD32")); // verde
            imgResultado.setImageResource(R.drawable.saudavel);
            atualizarBordaIMC("Normal");
        } else {
            tvIMCStatus.setText("Sobrepeso");
            tvIMCStatus.setTextColor(Color.parseColor("#CCAD00")); // amarelo
            tvIMCValor.setTextColor(Color.parseColor("#CCAD00")); // amarelo
            imgResultado.setImageResource(R.drawable.obesi);
            atualizarBordaIMC("Sobrepeso");
        }
    }
    // Atualiza a borda das imagens de classificação
    private void atualizarBordaIMC(String status) {
        // Todos serão definidos como inativos primeiro
        imgDesnutricao.setBackgroundResource(R.drawable.image_border_inactive);
        imgNormal.setBackgroundResource(R.drawable.image_border_inactive);
        imgSobrepeso.setBackgroundResource(R.drawable.image_border_inactive);

        // Ativa apenas o correspondente ao status do IMC
        switch (status) {
            case "Desnutrição":
                imgDesnutricao.setBackgroundResource(R.drawable.image_border_active);
                break;
            case "Normal":
                imgNormal.setBackgroundResource(R.drawable.image_border_active);
                break;
            case "Sobrepeso":
                imgSobrepeso.setBackgroundResource(R.drawable.image_border_active);
                break;
        }
    }
    //  Restaura todos os campos e imagens para o estado inicial
    private void resetarCampos() {
        // Limpa e restaura os textos de entrada para os valores Padrão
        edtPeso.setText("");
        edtAltura.setText("");
        tvIMCValor.setText("0.0");
        tvIMCStatus.setText("Classificação");
        imgResultado.setImageResource(R.drawable.polaroide);

        // volta para as cores original definida no XML
        tvIMCStatus.setTextColor(corPadraoStatus);
        tvIMCValor.setTextColor(corPadraoValor);
        // Remove destaque de todas as imagens
        imgDesnutricao.setBackgroundResource(R.drawable.image_border_inactive);
        imgNormal.setBackgroundResource(R.drawable.image_border_inactive);
        imgSobrepeso.setBackgroundResource(R.drawable.image_border_inactive);

    }
}


