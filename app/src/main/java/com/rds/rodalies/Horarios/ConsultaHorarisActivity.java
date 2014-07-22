package com.rds.rodalies.Horarios;

/*Basada en la app de * Jon Segador  */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.rds.rodalies.R;

import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;

import java.net.URL;
import java.util.Vector;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class ConsultaHorarisActivity extends Activity {

    Thread t;
    ProgressDialog dialog;

    TextView info_transbordo;
    String fulldate;

    TableLayout tl;
    Vector<ParsedHorarioDataSet> test;

    String final_dia;
    String final_mes;
    String annio;

    // URL completa al script que parsea la web de renfe para obtener los horarios
    String parser_url = "http://e-virtual.es/rodalies_parser/parser.php";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta_horarios);

        tl = (TableLayout)findViewById(R.id.myTableLayout);

        final int nucleo_id = getIntent().getIntExtra("nucleo_id", 0);
        final int station1_id = getIntent().getIntExtra("station1_id", 0);
        final int station2_id = getIntent().getIntExtra("station2_id", 0);

        final String station1_label = getIntent().getStringExtra("station1_name");
        final String station2_label = getIntent().getStringExtra("station2_name");

        TextView info_stations = (TextView) findViewById(R.id.info_stations);
        TextView info_date = (TextView) findViewById(R.id.info_date);
        info_transbordo = (TextView) findViewById(R.id.info_transbordo);

        final_dia = getIntent().getStringExtra("day");
        final_mes = getIntent().getStringExtra("month");
        annio = getIntent().getStringExtra("year");

        Button change_btn = (Button) findViewById(R.id.btn_change);
        change_btn.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
                Intent intent = new Intent(getApplicationContext(), ConsultaHorarisActivity.class);
                intent.putExtra("nucleo_id", nucleo_id);
                intent.putExtra("station1_id", station2_id);
                intent.putExtra("station2_id", station1_id);

                intent.putExtra("station1_name", station2_label);
                intent.putExtra("station2_name", station1_label);

                intent.putExtra("day", final_dia);
                intent.putExtra("month", final_mes);
                intent.putExtra("year", annio);

                startActivity(intent);

            }
        });

        info_stations.setText(station1_label + " - " + station2_label);
        info_date.setText(final_dia + "/" + final_mes + "/" + annio);

        fulldate = annio + final_mes + final_dia;

        showDialog(0);
        t=new Thread() {
            public void run() {
                loadSchedule(nucleo_id, station1_id, station2_id, fulldate);
            }
        };
        t.start();

    }


    public void loadSchedule(int nucleo_id, int station1_id, int station2_id, String fulldate) {


        try {

            URL url = new URL(parser_url + "?nucleo_id=" + nucleo_id + "&station1_id=" + station1_id + "&station2_id=" + station2_id + "&day=" + final_dia+ "&month=" + final_mes+ "&year=" + annio);

            SAXParserFactory spf = SAXParserFactory.newInstance();
            SAXParser sp = spf.newSAXParser();

            XMLReader xr = sp.getXMLReader();
            HorarioHandler myExampleHandler = new HorarioHandler();
            xr.setContentHandler(myExampleHandler);

            xr.parse(new InputSource(url.openStream()));

            test = myExampleHandler.getParsedExampleDataSets();

            Message myMessage=new Message();
            myMessage.obj="SUCCESS";
            handler.sendMessage(myMessage);

        } catch (Exception e) {
            //Log.e("Renfe", "Error", e);
        }

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            String loginmsg=(String)msg.obj;
            if(loginmsg.equals("SUCCESS")) {

                int i=0;

                for(ParsedHorarioDataSet v:test)
                {

                    TableRow tr = new TableRow(ConsultaHorarisActivity.this);

                     //MODIFICAR COLORS!!!
                    if(i%2!=0)
                        tr.setBackgroundColor(Color.argb(0xFF, 0xD9, 0xE2, 0xF3));

                    TextView a = new TextView(ConsultaHorarisActivity.this);
                    a.setText(v.getHoraSalida());
                    a.setTextSize(18);
                    a.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    a.setPadding(35, 3, 3, 3);
                    a.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView b = new TextView(ConsultaHorarisActivity.this);
                    b.setText(v.getHoraLlegada());
                    b.setTextSize(18);
                    b.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    b.setPadding(45, 3, 3, 3);
                    b.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView d = new TextView(ConsultaHorarisActivity.this);
                    d.setText(v.getTiempo());
                    d.setTextSize(18);
                    d.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    d.setPadding(35, 3, 3, 3);
                    d.setGravity(Gravity.CENTER_HORIZONTAL);

                    TextView e = new TextView(ConsultaHorarisActivity.this);
                    e.setText(v.getLineaSalida());
                    e.setTextSize(18);
                    e.setTextColor((Color.argb(0xFF, 0, 0, 0)));
                    e.setPadding(35, 3, 3, 3);
                    e.setGravity(Gravity.CENTER_HORIZONTAL);


                    if(!v.getTransbordo().equals("")){

                        char letra;
                        String texto="",nuevoTexto="", resto="";
                        //Borrando los espacios en blanco
                        texto=v.getTransbordo().toLowerCase();
                        //cogiendo un string a partir de la segunda letra
                        resto=texto.substring(1);
                        //cogiendo la primera letra en un char
                        letra=texto.charAt(0);
                        //poniendo dicha letra en mayusculas
                        letra = Character.toUpperCase(letra);
                        //concatenando todo
                        nuevoTexto=letra+resto + " (Línea " + v.getTransbordoLinea() + ")";

                        info_transbordo.setVisibility(View.VISIBLE); //0
                        info_transbordo.setText("Transbordo en:\n" + nuevoTexto);
                    }
                    else{
                        info_transbordo.setHeight(1);
                    }

                    tr.addView(a);
                    tr.addView(b);
                    tr.addView(d);
                    tr.addView(e);

                    tl.addView(tr);

                    i++;

                }

                // No siempre es culpa de mi aplicacion
                if(i==0){
                    Toast.makeText(getApplicationContext(), R.string.errorRenfe, Toast.LENGTH_SHORT).show();
                }


                removeDialog(0);
            }
        }
    };

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case 0: {
                dialog = new ProgressDialog(this);
                dialog.setMessage(getString(R.string.cargando));
                dialog.setIndeterminate(true);
                dialog.setCancelable(true);
                return dialog;
            }
        }
        return null;
    }


}
