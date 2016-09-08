package ar.edu.utn.frsf.isi.dam;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;


public class MainActivity extends AppCompatActivity implements SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private TextView dias;
    private SeekBar miSeek;
    private TextView interes;
    private EditText importe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dias = (TextView) findViewById(R.id.dias);
        importe = (EditText) findViewById(R.id.etImporte);
        miSeek = (SeekBar) findViewById(R.id.seekBar);
        miSeek.setOnSeekBarChangeListener(this);
        dias.setText("Dias:" + miSeek.getProgress());
        interes = (TextView) findViewById(R.id.interes);
        Button salida =(Button) findViewById(R.id.btAHacerPlazoFijo);
        salida.setOnClickListener(this);

    }
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        dias.setText("Dias:" + progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }
    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        dias.setText("Dias: " + miSeek.getProgress());
        interes.setText("$"+calcularInteres());
    }

    public float calcularInteres(){
        int monto, dias;
        float tasa, interes;

        monto = Integer.parseInt(importe.getText().toString());

        SeekBar diasAux = (SeekBar) findViewById(R.id.seekBar);
        dias = diasAux.getProgress();

        if(monto < 5000){
            if(dias<30){
                tasa= Float.parseFloat(getResources().getString(R.string.tasaMinimaMenor30));
            }
            else{
                tasa= Float.parseFloat(getResources().getString(R.string.tasaMinimaMayor30));
            }
        }else {
            if(monto < 99999){
                if(dias<30){
                    tasa= Float.parseFloat(getResources().getString(R.string.tasaMediaMenor30));
                }
                else{
                    tasa= Float.parseFloat(getResources().getString(R.string.tasaMediaMayor30));
                }
            }
            else{
                if(dias<30){
                    tasa= Float.parseFloat(getResources().getString(R.string.tasaMaximaMenor30));
                }
                else{
                    tasa= Float.parseFloat(getResources().getString(R.string.tasaMaximaMayor30));
                }
            }
        }

        interes = (float) (monto * (Math.pow(1.0+tasa,dias/360.0)-1));
        return interes;
    }


    public void onClick(View v){
       TextView texto =(TextView) findViewById(R.id.msjsalida);
       if(importe.getText().toString().trim().length()==0){
           texto.setText("Error.Debe completar el campo importe" );
           texto.setTextColor(getResources().getColor( R.color.msjError));
        }else{
           texto.setText("Plazo fijo realizado. Recibirá" + interes.getText() + " al vencimiento!!");
           texto.setTextColor(getResources().getColor( R.color.msjCorrecto));
       }
        }
    

}
