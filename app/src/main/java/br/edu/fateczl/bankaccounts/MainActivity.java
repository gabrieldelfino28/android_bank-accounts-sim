package br.edu.fateczl.bankaccounts;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.chip.Chip;

import br.edu.fateczl.bankaccounts.control.EspecialController;
import br.edu.fateczl.bankaccounts.control.IComputeSaldo;
import br.edu.fateczl.bankaccounts.control.IFactoryConta;
import br.edu.fateczl.bankaccounts.control.PoupancaController;
import br.edu.fateczl.bankaccounts.model.ContaBancaria;

public class MainActivity extends AppCompatActivity {

    //UI elements
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch opSwitch;
    private TextView outputCompute;
    private TextView outputClienteData;
    private EditText inputVal;
    private EditText inCliente;
    private EditText inNumConta;
    private EditText inSaldo;
    private EditText inputGen; //Either limit or day
    private RadioButton rdEspec;
    private RadioButton rdPoup;
    private Chip btnUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        rdEspec = findViewById(R.id.rbEspecial);
        rdEspec.setOnClickListener(change -> changeHints());
        rdPoup = findViewById(R.id.rbPoup);
        rdPoup.setOnClickListener(change -> changeHints());

        inCliente = findViewById(R.id.inCliente);
        inNumConta = findViewById(R.id.inConta);
        inSaldo = findViewById(R.id.inSaldo);
        inputGen = findViewById(R.id.inGen);
        inputVal = findViewById(R.id.inputValor);

        outputCompute = findViewById(R.id.outputData);
        outputClienteData = findViewById(R.id.outputClient);

        opSwitch = findViewById(R.id.switch2);
        opSwitch.setOnClickListener(op -> switchOP());

        Button btnCalc = findViewById(R.id.btnCalc);
        Button btnCad = findViewById(R.id.btnCad);
        btnUpdate = findViewById(R.id.btnChip);
        btnUpdate.setVisibility(View.INVISIBLE);

        initialAccount(); //Sample account

        btnCad.setOnClickListener(create -> {
            try {
                outputCompute.setTextColor(getColor(R.color.black));
                insertConta();
            } catch (Exception e) {
                outputCompute.setText(e.getMessage());
                outputCompute.setTextColor(getColor(R.color.red));
            }
        });
        btnCalc.setOnClickListener(compute -> {
            try {
                outputCompute.setTextColor(getColor(R.color.black));
                computeSaldo();
            } catch (Exception e) {
                outputCompute.setText(e.getMessage());
                outputCompute.setTextColor(getColor(R.color.red));
            }
        });

        btnUpdate.setOnClickListener(update -> {
            try {
                outputCompute.setTextColor(getColor(R.color.black));
                updateSaldoPoupanca();
            }catch (Exception e){
                outputCompute.setText(R.string.errNull);
                outputCompute.setTextColor(getColor(R.color.red));
            }
        });
    }

    //Backend elements
    private IFactoryConta factoryConta;
    private ContaBancaria conta;

    private void insertConta() throws ArithmeticException{
        checkEmptyValues();
        checkTypeAccount();
        String cliente = inCliente.getText().toString();
        int numConta = Integer.parseInt(inNumConta.getText().toString());
        float saldo = Float.parseFloat(inSaldo.getText().toString());
        String limite_diaRend = inputGen.getText().toString();
        conta = factoryConta.createConta(cliente, numConta, saldo, limite_diaRend);
        dataPrinter(R.string.outputCad);
    }

    private void computeSaldo() throws ArithmeticException {
        if (inputVal.getText().toString().isEmpty()) {
            if(opSwitch.isChecked()) throw new ArithmeticException(getString(R.string.errDep));
            else                     throw new ArithmeticException(getString(R.string.errSaq));
        }
        float valor = Float.parseFloat(inputVal.getText().toString());
        if (opSwitch.isChecked()) {
            conta.depositar(valor);
            dataPrinter(R.string.outDep);
        } else {
            conta.sacar(valor);
            dataPrinter(R.string.outSaque);
        }
    }

    private void updateSaldoPoupanca() {
        IComputeSaldo newBalance = new PoupancaController();
        conta = newBalance.updateSaldo(conta);
        dataPrinter(R.string.outUpdate);
    }

    private void checkEmptyValues() throws ArithmeticException{
        if (inCliente.getText().toString().isEmpty()) throw new ArithmeticException(getString(R.string.errNull));
        if (inNumConta.getText().toString().isEmpty()) throw new ArithmeticException(getString(R.string.errNull));
        if (inSaldo.getText().toString().isEmpty()) throw new ArithmeticException(getString(R.string.errNull));
        if (inputGen.getText().toString().isEmpty()) throw new ArithmeticException(getString(R.string.errNull));
    }

    private void dataPrinter(int idString) {
        String dadosCliente = String.format(getString(R.string.outCreateCliente), conta.toString());
        outputClienteData.setText(dadosCliente);
        if (idString == R.string.outDep || idString == R.string.outSaque) {
            String saqueDados = String.format(getString(idString), conta.getSaldo());
            outputCompute.setText(saqueDados);
        } else if (idString == R.string.outUpdate) {
            String updateSaldo = String.format(getString(idString), conta.getSaldo());
            outputCompute.setText(updateSaldo);
        } else {
            outputCompute.setText(idString);
        }
    }

    private void initialAccount() {
        checkTypeAccount();
        try {
            conta = factoryConta.createConta("Osvaldo Cruz", 1239, 2000, "500");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void checkTypeAccount() {
        if (rdEspec.isChecked()) factoryConta = new EspecialController();
        if (rdPoup.isChecked()) factoryConta = new PoupancaController();
    }

    private void switchOP() {
        if (opSwitch.isChecked()) inputVal.setHint(R.string.valDep);
        else inputVal.setHint(R.string.valSaque);
    }

    private void changeHints() {
        if (rdEspec.isChecked()) {
            inputGen.setHint(R.string.inLimite);
            btnUpdate.setVisibility(View.INVISIBLE);
        }
        if (rdPoup.isChecked()) {
            inputGen.setHint(R.string.inDiaRen);
            btnUpdate.setVisibility(View.VISIBLE);
        }

    }
}