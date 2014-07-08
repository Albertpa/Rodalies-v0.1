/*Basada en la app de * Jon Segador  */
package com.rds.rodalies.Horarios;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.rds.rodalies.Horarios.ConsultaHorarisActivity;
import com.rds.rodalies.R;

import java.util.Calendar;

public class HorarisActivity extends Activity {


    private Button create_btn;
    private Spinner nucleo;
    private Spinner station1;
    private Spinner station2;

    private Spinner day;
    private Spinner month;
    private Spinner year;

    //private SharedPreferences mPreferences;

    private int nucleo_id = 50;
    private int station1_id = 0;
    private int station2_id = 0;

    private int nucleo_id_to_set = 0;
    private int station1_id_to_set = 0;
    private int station2_id_to_set = 0;

    boolean can_change = false;

    private String [][] actual_station = {
            { "0", "-" }
    };

    private String[][] station50 = {
            { "72400", "Aeroport" },
            { "79600", "Arenys de Mar" },
            { "79404", "Badalona" },
            { "77106", "Balenya-Els Hostalets" },
            { "77107", "Balenya-Tona-Seva" },
            { "78705", "Barbera del Valles" },
            { "78804", "Barcelona Arc de Triomf" },
            { "79009", "Barcelona El Clot-Arago" },
            { "78806", "Barcelona-La Sagrera-Meridiana" },
            { "71802", "Barcelona Passeig de Gracia" },
            { "78805", "Barcelona Placa de Catalunya" },
            { "78802", "Barcelona Sant Andreu Arenal" },
            { "79004", "Barcelona Sant Andreu Comtal" },
            { "71801", "Barcelona Sants" },
            { "79400", "Barcelona-Estaçio França" },
            { "71708", "Bellvitge" },
            { "79606", "Blanes" },
            { "77112", "Borgonya" },
            { "79412", "Cabrera de Mar-Vilassar de Mar" },
            { "71601", "Calafell" },
            { "79502", "Caldes D'Estrac" },
            { "79603", "Calella" },
            { "77301", "Campdevanol" },
            { "79601", "Canet de Mar" },
            { "79101", "Cardedeu" },
            { "78605", "Castellbell-Monistrol de M." },
            { "72210", "Castellbisbal" },
            { "71705", "Castelldefels" },
            { "77105", "Centelles" },
            { "78706", "Cerdanyola del Valles" },
            { "72503", "Cerdanyola-Universitat" },
            { "72303", "Cornella" },
            { "71604", "Cubelles" },
            { "71603", "Cunit" },
            { "79407", "El Masnou" },
            { "72211", "El Papiol" },
            { "71707", "El Prat de Llobregat" },
            { "72201", "El Vendrell" },
            { "72203", "Els Monjos" },
            { "77103", "Figaro" },
            { "71703", "Garraf" },
            { "71706", "Gava" },
            { "72208", "Gelida" },
            { "79100", "Granollers Centre" },
            { "77006", "Granollers-Canovelles" },
            { "79105", "Gualba" },
            { "79107", "Hostalric" },
            { "72202", "L'Arboc" },
            { "72305", "L'Hospitalet de Llobregat" },
            { "77114", "La Farga Bebié" },
            { "77102", "La Garriga" },
            { "72205", "La Granada" },
            { "79011", "La Llagosta" },
            { "77306", "La Molina" },
            { "77310", "La Tour de Carol" },
            { "72206", "Lavern-Subirats" },
            { "77100", "Les Franqueses del Valles" },
            { "79109", "Les Franqueses-Granollers Nord" },
            { "79102", "Llinars del Valles" },
            { "79200", "Macanet-Massanes" },
            { "79605", "Malgrat de Mar" },
            { "77110", "Manlleu" },
            { "78600", "Manresa" },
            { "72209", "Martorell" },
            { "79500", "Mataro" },
            { "72300", "Molins de Rei" },
            { "79006", "Mollet-Sant Fost" },
            { "77004", "Mollet-Santa Rosa" },
            { "78708", "Moncada I Reixac-Manresa" },
            { "78707", "Moncada I Reixac-Sta. Maria" },
            { "78800", "Montcada Bifucarcio" },
            { "79005", "Montcada I Reixac" },
            { "77002", "Montcada-Ripollet" },
            { "79405", "Montgat" },
            { "79406", "Montgat Nord" },
            { "79007", "Montmelo" },
            { "79408", "Ocata" },
            { "79103", "Palautordera" },
            { "77005", "Parets del Valles" },
            { "79604", "Pineda de Mar" },
            { "77304", "Planoles" },
            { "71704", "Platja Castelldefels" },
            { "79409", "Premia de Mar" },
            { "77309", "Puigcerda" },
            { "77303", "Ribes Freser" },
            { "79106", "Riells I Viabrea-Breda" },
            { "77200", "Ripoll" },
            { "72501", "Rubi" },
            { "78704", "Sabadell Centre" },
            { "78709", "Sabadell Nord" },
            { "78703", "Sabadell Sud" },
            { "79403", "Sant Adria de Besos" },
            { "79501", "Sant Andreu de Llavaneres" },
            { "79104", "Sant Celoni" },
            { "72502", "Sant Cugat del Valles" },
            { "72301", "Sant Feliu de Llobregat" },
            { "72302", "Sant Joan Despi" },
            { "77104", "Sant Marti de Centelles" },
            { "78610", "Sant Miquel de Gonteres" },
            { "79602", "Sant Pol de Mar" },
            { "77113", "Sant Quirze Besora" },
            { "72207", "Sant Sadurni D'Anoia" },
            { "71600", "Sant Vicenc de Calders" },
            { "78604", "Sant Vicenc de Castellet" },
            { "77003", "Santa Perpetua de Mogoda" },
            { "79608", "Santa Susanna" },
            { "71602", "Segur de Calafell" },
            { "71701", "Sitges" },
            { "78700", "Terrassa" },
            { "78710", "Terrassa Est" },
            { "79607", "Tordera" },
            { "77111", "Torello" },
            { "78801", "Torre del Baro" },
            { "77305", "Toses" },
            { "77307", "Urxt Alp" },
            { "78606", "Vacarisses" },
            { "78607", "Vacarisses-Torreblanca" },
            { "77109", "Vic" },
            { "71709", "Viladecans" },
            { "78609", "Viladecavalls" },
            { "72204", "Vilafranca del Penedes" },
            { "71700", "Vilanova I La Geltru" },
            { "79410", "Vilassar de Mar" }
    };

    private String[][] days = {
            { "01", "1" },
            { "02", "2" },
            { "03", "3" },
            { "04", "4" },
            { "05", "5" },
            { "06", "6" },
            { "07", "7" },
            { "08", "8" },
            { "09", "9" },
            { "10", "10" },
            { "11", "11" },
            { "12", "12" },
            { "13", "13" },
            { "14", "14" },
            { "15", "15" },
            { "16", "16" },
            { "17", "17" },
            { "18", "18" },
            { "19", "19" },
            { "20", "20" },
            { "21", "21" },
            { "22", "22" },
            { "23", "23" },
            { "24", "24" },
            { "25", "25" },
            { "26", "26" },
            { "27", "27" },
            { "28", "28" },
            { "29", "29" },
            { "30", "30" },
            { "31", "31" }
    };

    private String[][] months = {
            { "01", "Enero" },
            { "02", "Febrero" },
            { "03", "Marzo" },
            { "04", "Abril" },
            { "05", "Mayo" },
            { "06", "Junio" },
            { "07", "Julio" },
            { "08", "Agosto" },
            { "09", "Septiembre" },
            { "10", "Octubre" },
            { "11", "Noviembre" },
            { "12", "Diciembre" }
    };

    private String[][] years = {
            { "2012", "2012" },
            { "2013", "2013" },
            { "2014", "2014" },
            { "2015", "2015" }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_horaris);

        //mPreferences = getSharedPreferences("Renfe", MODE_PRIVATE);

        // nucleo = (Spinner) this.findViewById(R.id.spinner1);
        station1 = (Spinner) this.findViewById(R.id.spinner2);
        station2 = (Spinner) this.findViewById(R.id.spinner3);

        day = (Spinner) this.findViewById(R.id.spinner_day);
        month = (Spinner) this.findViewById(R.id.spinner_month);
        year = (Spinner) this.findViewById(R.id.spinner_year);



        Calendar c = Calendar.getInstance();

        String current_day = Integer.toString(c.get(Calendar.DATE));
        String current_month = Integer.toString(c.get(Calendar.MONTH));
        String current_year2 = Integer.toString(c.get(Calendar.YEAR));

        String current_year = "0";

        if(current_year2.equals("2012")){
            current_year = "0";
        }
        else if(current_year2.equals("2013")){
            current_year = "1";
        }
        else if(current_year2.equals("2014")){
            current_year = "2";
        }
        else if(current_year2.equals("2015")){
            current_year = "2";
        }
        else{
            current_year = "0";
        }

//R.layout.spinner_item
        ArrayAdapter<CharSequence> day_adapter = new ArrayAdapter<CharSequence>(
                getApplicationContext(), R.layout.spinner_item);

        for (int i = 0; i < days.length; i++)
            day_adapter.add(days[i][1]);

        day_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        day.setAdapter(day_adapter);

        ArrayAdapter<CharSequence> month_adapter = new ArrayAdapter<CharSequence>(
                getApplicationContext(), R.layout.spinner_item);

        for (int i = 0; i < months.length; i++)
            month_adapter.add(months[i][1]);

        month_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        month.setAdapter(month_adapter);

        ArrayAdapter<CharSequence> year_adapter = new ArrayAdapter<CharSequence>(
                getApplicationContext(), R.layout.spinner_item);

        for (int i = 0; i < years.length; i++)
            year_adapter.add(years[i][1]);

        year_adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
        year.setAdapter(year_adapter);


        day.setSelection(Integer.parseInt(current_day)-1);
        month.setSelection(Integer.parseInt(current_month));
        year.setSelection(Integer.parseInt(current_year));

        /*
        nucleos en principio tampoco se necesita

        ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < nucleos.length; i++)
            adapter.add(nucleos[i][1]);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        nucleo.setAdapter(adapter);

        nucleo.setOnItemSelectedListener(nucleoListener);
        */

        create_btn = (Button) findViewById(R.id.btn_view);
        create_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(station1_id == station2_id){
                    Toast.makeText(getApplicationContext(), R.string.estacionesIguales, Toast.LENGTH_SHORT).show();
                }

                else{

                    Intent intent = new Intent(getApplicationContext(), ConsultaHorarisActivity.class);

                    //50 es el codigo de Barcelona, y de las líneas Rx
                    intent.putExtra("nucleo_id", nucleo_id);
                    intent.putExtra("station1_id", station1_id);
                    intent.putExtra("station2_id", station2_id);

                    int pos1 = station1.getSelectedItemPosition();
                    String station1_name = actual_station[pos1][1];

                    int pos2 = station2.getSelectedItemPosition();
                    String station2_name = actual_station[pos2][1];

                    int cday = day.getSelectedItemPosition();
                    String day_key = days[cday][0];
                    int cmonth = month.getSelectedItemPosition();
                    String month_key = months[cmonth][0];
                    int cyear = year.getSelectedItemPosition();
                    String year_key = years[cyear][0];

                    intent.putExtra("day", day_key);
                    intent.putExtra("month", month_key);
                    intent.putExtra("year", year_key);

                    intent.putExtra("station1_name", station1_name);
                    intent.putExtra("station2_name", station2_name);


                    //ATENCIÓN, SE USAN SHARED PREFERENCES??

                    //SharedPreferences.Editor editor=mPreferences.edit();
                    //editor.putInt("nucleo", nucleo_id_to_set);
                    //editor.putInt("station1", station1_id_to_set);
                    //editor.putInt("station2", station2_id_to_set);
                    //editor.commit();

                    startActivity(intent);

                }

            }
        });

        //Bloque WTF
        /*
        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
                this, android.R.layout.simple_spinner_item);

        for (int i = 0; i < empty_nucleo.length; i++)
            adapter2.add(empty_nucleo[i][1]);

        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        station1.setAdapter(adapter2);
        station2.setAdapter(adapter2);

        station1.setOnItemSelectedListener(selectListener2);
        station2.setOnItemSelectedListener(selectListener3);

        */

        actual_station = station50;

        ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
                getApplicationContext(), R.layout.spinner_item);

        for (int i = 0; i < actual_station.length; i++)
            adapter2.add(actual_station[i][1]);

        adapter2.setDropDownViewResource(R.layout.spinner_dropdown_item);
        station1.setAdapter(adapter2);

        station2.setAdapter(adapter2);

        station1_id = Integer.parseInt(actual_station[0][0]);
        station2_id = Integer.parseInt(actual_station[0][0]);

        station1.setOnItemSelectedListener(selectListener2);
        station2.setOnItemSelectedListener(selectListener3);

        //ATENCIÓN, SE USAN SHARED PREFERENCES??
        /*
        boolean nucleo_set = mPreferences.contains("nucleo");
        boolean station1_set = mPreferences.contains("station1");
        boolean station2_set = mPreferences.contains("station2");

        if(nucleo_set && station1_set && station2_set){
            can_change = true;
            nucleo.setSelection(mPreferences.getInt("nucleo", 0));
        }
        */

    }


    /*
    No necesitamos menú
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.horaris, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    */

    /*

    private OnItemSelectedListener nucleoListener = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {

            if(nucleo.getSelectedItemPosition() > 0){

                int pos = nucleo.getSelectedItemPosition();
                nucleo_id_to_set = pos;
                nucleo_id = Integer.parseInt(nucleos[pos][0]);

                switch(pos){
                    case 1:
                        actual_station = station20;
                        break;
                    case 2:
                        actual_station = station50;
                        break;
                    case 3:
                        actual_station = station60;
                        break;
                    case 4:
                        actual_station = station31;
                        break;
                    case 5:
                        actual_station = station10;
                        break;
                    case 6:
                        actual_station = station32;
                        break;
                    case 7:
                        actual_station = station41;
                        break;
                    case 8:
                        actual_station = station62;
                        break;
                    case 9:
                        actual_station = station61;
                        break;
                    case 10:
                        actual_station = station30;
                        break;
                    case 11:
                        actual_station = station40;
                        break;
                    case 12:
                        actual_station = station70;
                        break;
                    default:
                        actual_station = empty_nucleo;
                        break;
                }

                ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
                        getApplicationContext(), android.R.layout.simple_spinner_item);

                for (int i = 0; i < actual_station.length; i++)
                    adapter2.add(actual_station[i][1]);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                station1.setAdapter(adapter2);

                station2.setAdapter(adapter2);

                station1_id = Integer.parseInt(actual_station[0][0]);
                station2_id = Integer.parseInt(actual_station[0][0]);


            }
            else if(nucleo.getSelectedItemPosition() == 0){

                station1_id = 0;
                station2_id = 0;

                ArrayAdapter<CharSequence> adapter2 = new ArrayAdapter<CharSequence>(
                        getApplicationContext(), android.R.layout.simple_spinner_item);

                for (int i = 0; i < empty_nucleo.length; i++)
                    adapter2.add(empty_nucleo[i][1]);

                adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                station1.setAdapter(adapter2);
                station2.setAdapter(adapter2);

            }

        }
        public void onNothingSelected(AdapterView arg0) {}
    };
    */

    private OnItemSelectedListener selectListener2 = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {

            /*
            if(can_change)
            {
                station1.setSelection(mPreferences.getInt("station1", 0));
            }
            */

            if(station1.getSelectedItemPosition() >= 0){
                int pos = station1.getSelectedItemPosition();
                station1_id_to_set = pos;
                station1_id = Integer.parseInt(actual_station[pos][0]);
            }
        }
        public void onNothingSelected(AdapterView arg0) {}
    };

    private OnItemSelectedListener selectListener3 = new OnItemSelectedListener() {
        public void onItemSelected(AdapterView parent, View v, int position, long id) {

            /*
            if(can_change)
            {
                station2.setSelection(mPreferences.getInt("station2", 0));
                can_change = false;
            }
            */

            if(station2.getSelectedItemPosition() >= 0){
                int pos = station2.getSelectedItemPosition();
                station2_id_to_set = pos;
                station2_id = Integer.parseInt(actual_station[pos][0]);
            }
        }
        public void onNothingSelected(AdapterView arg0) {}
    };
}
