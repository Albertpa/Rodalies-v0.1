package com.example.rodalies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.HorizontalScrollView;
import android.widget.TabHost;
import android.widget.TabWidget;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends FragmentActivity {

	//private static final String[] fragmentTags = { "Page1", "Page2" };	
	private ArrayAdapter<String> adapt;

    TabHost mTabHost;
    ViewPager  mViewPager;

    //clase que mezcla tabhost con pageview
    TabsAdapter mTabsAdapter;
    
    private SharedPreferences sharedSettings;
    private boolean existPreferences = true;

    private Map<String, String> nombre_cuentaTwitter;

    
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        crearTabs();

	}

    private void crearTabs(){
        sharedSettings = getSharedPreferences(Constants.RODA_PREFERENCES, Context.MODE_PRIVATE);

        if (!(sharedSettings.contains(Constants.LINEA_SECUNDARIA1))) {
            //no tiene preferencias, llamamos a la configuración de la linea principal
            existPreferences=false;
            //startActivity(new Intent(MainActivity.this, GuardarPreferencias.class));
            startActivityForResult(new Intent(MainActivity.this, GuardarPreferencias.class), 100);

        }
        else{
            ArrayList<Integer> codeRod = new ArrayList<Integer>(); //Codigo linea
            int codePrincipal = -1;

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA1) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA1,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA1,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA2) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA2,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA2,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA3) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA3,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA3,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA4) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA4,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA4,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA5) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA5,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA5,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA6) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA6,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA6,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA7) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA7,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA7,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA8) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA8,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA8,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA9) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA9,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA9,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA10) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA10,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA10,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA11) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA11,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA11,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA12) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA12,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA12,-1));
            }

            if( sharedSettings.contains(Constants.LINEA_SECUNDARIA13) ){
                if(codePrincipal !=sharedSettings.getInt(Constants.LINEA_SECUNDARIA13,-1))
                    codeRod.add(sharedSettings.getInt(Constants.LINEA_SECUNDARIA13,-1));
            }

            //montamos el array para los nombres de los fragments y nombres de los tabs

            final int size = codeRod.size();
            String[] fragmentTags = new String[size];
            String[] items_menu = new String[size];
            for (int i = 0; i < size; i++)
            {
                fragmentTags[i] = this.nombreLinea(codeRod.get(i));
                //Log.e("Rodalies", this.nombreLinea(codeRod.get(i)));
                items_menu[i] = this.nombreLinea(codeRod.get(i));
            }
            //Log.e("Rodalies", "te sharedpreferences");
            existPreferences=true;

            mTabHost = (TabHost)findViewById(android.R.id.tabhost);

            mTabHost.setup();

            mViewPager = (ViewPager)findViewById(R.id.pager);
            mTabsAdapter = new TabsAdapter(this, mTabHost, mViewPager);

            //Se le indica la clase en cada posicion del array, es decir para cada posicion tiene un texto ya
            //pero necesita indicar la clase correspondiente

            //bucle for de la pagina
            for (int i = 0; i < size; i++)
            {
                Bundle args = new Bundle();
                args.putInt(Constants.LINEA_PARAMETRO, codeRod.get(i) );
                mTabsAdapter.addTab(mTabHost.newTabSpec(fragmentTags[i]).setIndicator(items_menu[i]), Page1Activity.class, args);
            }

            nombre_cuentaTwitter = new HashMap<String, String>();
            nombre_cuentaTwitter.put("R1", "@rodalia1");
            nombre_cuentaTwitter.put("R2 Nord", "@rodalia2");
            nombre_cuentaTwitter.put("R2 Sud", "@rodalia2");
            nombre_cuentaTwitter.put("R3", "@rodalia3");
            nombre_cuentaTwitter.put("R4", "@rodalia4");
            nombre_cuentaTwitter.put("R7", "@rodalia7");
            nombre_cuentaTwitter.put("R8", "@rodalia8");
            nombre_cuentaTwitter.put("R11", "rod11");
            nombre_cuentaTwitter.put("R12", "rod12");
            nombre_cuentaTwitter.put("R13", "rod13");
            nombre_cuentaTwitter.put("R14", "rod14");
            nombre_cuentaTwitter.put("R15", "rod15");
            nombre_cuentaTwitter.put("R16", "rod16");
        }
    }

     @Override
     protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
         super.onActivityResult(requestCode, resultCode, intent);

        //100 es el codigo que se usa para llamar a la activity settings
         if (requestCode == 100) {

             //montarVista(); // your "refresh" code
             finish();
             startActivity(getIntent());
         }
     }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}


    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
	    switch(item.getItemId())
	    {
		    case R.id.action_settings:
		    	Log.i("Rodalies", "entra a settings");
				//no tiene preferencias, llamamos a la configuracion de la linea principal
				existPreferences=false;
                startActivityForResult(new Intent(MainActivity.this, GuardarPreferencias.class), 100);
                return true;
            case R.id.nuevo_tuit:
                escribirTuit();
                Log.i("Rodalies", "Escribir tuit");
                return true;
            case R.id.about:
                startActivityForResult(new Intent(MainActivity.this, About.class), 100);
                Log.i("Rodalies", "About");
                return true;
            case R.id.actualizar:
                actualizar();
                Log.i("Rodalies", "Actualizar");
                return true;
            default:
                return super.onOptionsItemSelected(item);
		 }
	}

    private void actualizar() {
        TabsAdapter.TabInfo tabI = mTabsAdapter.mTabs.get(mTabHost.getCurrentTab()); // Obtiene la tab en la cual se encuentra el codigo de la linea.
        Bundle info = tabI.args;

        // Integer idLinea = info.getInt(Constants.LINEA_PRINCIPAL); //Codigo de la linea actual


        //Log.e("TAG", "->"+tabI.tag);

        //pienso que, con el tag, deberiamos saber cual es el codigo de la linea:
        //p.e.-> R3 es 2
        Integer idLinea = codigoLinea(tabI.tag); //Codigo de la linea actual

        List<Fragment> listPage = getSupportFragmentManager().getFragments(); //Lista de fragments
        Page1Activity act = (Page1Activity) listPage.get(mTabHost.getCurrentTab()); //Obtiene el fragment que se está visualizando


        act.actualizar(idLinea); //Se llama al metodo actualizar del fragment que se está viendo.
    }

    private void escribirTuit() {
        String originalMessage = obtenerTwitterLinea(mTabHost.getCurrentTabTag());

        String originalMessageEscaped = null;
        try {
            originalMessageEscaped = String.format(
                    "https://twitter.com/intent/tweet?source=webclient&text=%s",
                    URLEncoder.encode(originalMessage, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        if(originalMessageEscaped != null) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(originalMessageEscaped));
            startActivity(i);
        }
        else {
            // Some Error
        }
    }

    private String obtenerTwitterLinea(String currentTabTag) {
        return nombre_cuentaTwitter.get(currentTabTag);
    }
	
	public String nombreLinea(int i){

        String ret;
        switch (i){
            case 0:
                ret="R1";
                break;
            case 1:
                ret="R2 Nord";
                break;
            case 2:
                ret="R2 Sud";
                break;
            case 3:
                ret="R3";
                break;
            case 4:
                ret="R4";
                break;
            case 5:
                ret="R7";
                break;
            case 6:
                ret="R8";
                break;
            case 7:
                ret="R11";
                break;
            case 8:
                ret="R12";
                break;
            case 9:
                ret="R13";
                break;
            case 10:
                ret="R14";
                break;
            case 11:
                ret="R15";
                break;
            case 12:
                ret="R16";
                break;
            default:
                ret="NO DEFINED";
            break;
        }
        return ret;

    }

    public int codigoLinea(String i){

        int ret;
        if (i.equals("R1")) {
            ret = 0;

        } else if (i.equals("R2 Nord")) {
            ret = 1;

        } else if (i.equals("R2 Sud")) {
            ret = 2;

        } else if (i.equals("R3")) {
            ret = 3;

        } else if (i.equals("R4")) {
            ret = 4;

        } else if (i.equals("R7")) {
            ret = 5;

        } else if (i.equals("R8")) {
            ret = 6;

        } else if (i.equals("R11")) {
            ret = 7;

        } else if (i.equals("R12")) {
            ret = 8;

        } else if (i.equals("R13")) {
            ret = 9;

        } else if (i.equals("R14")) {
            ret = 10;

        } else if (i.equals("R15")) {
            ret = 11;

        } else if (i.equals("R16")) {
            ret = 12;

        } else {
            ret = -1;

        }
        return ret;

    }
	
	 /**
     * This is a helper class that implements the management of tabs and all
     * details of connecting a ViewPager with associated TabHost.  It relies on a
     * trick.  Normally a tab host has a simple API for supplying a View or
     * Intent that each tab will show.  This is not sufficient for switching
     * between pages.  So instead we make the content part of the tab host
     * 0dp high (it is not shown) and the TabsAdapter supplies its own dummy
     * view to show as the tab content.  It listens to changes in tabs, and takes
     * care of switch to the correct paged in the ViewPager whenever the selected
     * tab changes.
     */
	//Esta clase ayuda la mezcla de Pager con TabHost
    public static class TabsAdapter extends FragmentStatePagerAdapter
            implements TabHost.OnTabChangeListener, ViewPager.OnPageChangeListener {
        private final Context mContext;
        private final TabHost mTabHost;
        private final ViewPager mViewPager;
        private final ArrayList<TabInfo> mTabs = new ArrayList<TabInfo>();
        private int positionActual = 0;

        static final class TabInfo { //esta pequenya clase guarda info de cada tab
            private final String tag;
            private final Class<?> clss;
            private final Bundle args;

            TabInfo(String _tag, Class<?> _class, Bundle _args) {
                tag = _tag;
                clss = _class;
                args = _args;
            }
        }

        static class DummyTabFactory implements TabHost.TabContentFactory {
            private final Context mContext;

            public DummyTabFactory(Context context) {
                mContext = context;
            }

            @Override
            public View createTabContent(String tag) {
                View v = new View(mContext);
                v.setMinimumWidth(0);
                v.setMinimumHeight(0);
                return v;
            }
        }

        public TabsAdapter(FragmentActivity activity, TabHost tabHost, ViewPager pager) {
            super(activity.getSupportFragmentManager());
            mContext = activity;
            mTabHost = tabHost;
            mViewPager = pager;
            mTabHost.setOnTabChangedListener(this);
            mViewPager.setAdapter(this);
            mViewPager.setOnPageChangeListener(this);
        }


        public void addTab(TabHost.TabSpec tabSpec, Class<?> clss, Bundle args) {
            tabSpec.setContent(new DummyTabFactory(mContext));
            String tag = tabSpec.getTag();

            TabInfo info = new TabInfo(tag, clss, args);
            mTabs.add(info);
            mTabHost.addTab(tabSpec);
            notifyDataSetChanged();
        }

        @Override
        public int getCount() {
            return mTabs.size();
        }

        @Override
        public Fragment getItem(int position) {
            TabInfo info = mTabs.get(position);
            return Fragment.instantiate(mContext, info.clss.getName(), info.args);
        }
        
        public Fragment findFragment(int position) {
            String name = "android:switcher:" + mViewPager.getId() + ":" + position; // curiosos tags
            FragmentManager fm = ((FragmentActivity) mContext).getSupportFragmentManager();
            Fragment fragment = fm.findFragmentByTag(name);
            if (fragment == null) {
                fragment = getItem(position);
            }
            return fragment;
        }        

        @Override
        public void onTabChanged(String tabId) {

            int position = mTabHost.getCurrentTab();
            mViewPager.setCurrentItem(position);

            //position es la nueva position

            //Log.e("Rodalies", "tab1:"+positionActual);
            //Log.e("Rodalies", "tab2:"+position);

            final HorizontalScrollView strip;
            final int positionTab = position * 110;
            View rootView = ((Activity)mContext).getWindow().getDecorView().findViewById(android.R.id.tabhost);

            //View v = rootView.findViewById(R.id.scrollTabs);

            strip = (HorizontalScrollView) rootView.findViewById(R.id.scrollTabs);

            strip.scrollTo(positionTab, 0);
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            // Unfortunately when TabHost changes the current tab, it kindly
            // also takes care of putting focus on it when not in touch mode.
            // The jerk.
            // This hack tries to prevent this from pulling focus out of our
            // ViewPager.
            TabWidget widget = mTabHost.getTabWidget();
            int oldFocusability = widget.getDescendantFocusability();
            widget.setDescendantFocusability(ViewGroup.FOCUS_BLOCK_DESCENDANTS);
            mTabHost.setCurrentTab(position);
            widget.setDescendantFocusability(oldFocusability);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
               
    }

}
