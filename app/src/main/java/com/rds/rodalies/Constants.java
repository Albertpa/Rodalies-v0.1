package com.rds.rodalies;

public class Constants {
	
	//Nombre del archivo, como se llamara
    public static final String RODA_PREFERENCES = "RodaPrefs";

    //Nombre máximo de preferencias almacenadas
    public static final Integer MAX_PREF = 3;

    //Se guarda desde Settings
    public static final String LINEA_PARAMETRO = "LINEA_PARAMETRO"; // String
/*    public static final String LINEA_SECUNDARIA1 = "LINEA_SECUNDARIA1"; // String
    public static final String LINEA_SECUNDARIA2 = "LINEA_SECUNDARIA2"; // String
    public static final String LINEA_SECUNDARIA3 = "LINEA_SECUNDARIA3"; // String
    public static final String LINEA_SECUNDARIA4 = "LINEA_SECUNDARIA4"; // String
    public static final String LINEA_SECUNDARIA5 = "LINEA_SECUNDARIA5"; // String
    public static final String LINEA_SECUNDARIA6 = "LINEA_SECUNDARIA6"; // String
    public static final String LINEA_SECUNDARIA7 = "LINEA_SECUNDARIA7"; // String
    public static final String LINEA_SECUNDARIA8 = "LINEA_SECUNDARIA8"; // String
    public static final String LINEA_SECUNDARIA9 = "LINEA_SECUNDARIA9"; // String
    public static final String LINEA_SECUNDARIA10 = "LINEA_SECUNDARIA10"; // String
    public static final String LINEA_SECUNDARIA11 = "LINEA_SECUNDARIA11"; // String
    public static final String LINEA_SECUNDARIA12 = "LINEA_SECUNDARIA12"; // String
    public static final String LINEA_SECUNDARIA13 = "LINEA_SECUNDARIA13"; // String*/

    public static final String[] lineaSecundaria =
            {
                "LINEA_SECUNDARIA1",
                "LINEA_SECUNDARIA2",
                "LINEA_SECUNDARIA3",
                "LINEA_SECUNDARIA4",
                "LINEA_SECUNDARIA5",
                "LINEA_SECUNDARIA6",
                "LINEA_SECUNDARIA7",
                "LINEA_SECUNDARIA8",
                "LINEA_SECUNDARIA9",
                "LINEA_SECUNDARIA10",
                "LINEA_SECUNDARIA11",
                "LINEA_SECUNDARIA12",
                "LINEA_SECUNDARIA13"
            };

    public static final String LINEAS_TOTAL = "LINEAS_TOTAL"; // String

    /* Array con las URL de las lineas */
    public static final String[] lineasURL = new String[]{
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r1_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r2_nord_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r2_sud_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r3_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r4_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r7_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r8_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r11_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r12_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r13_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r14_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r15_ca_ES.xml",
            "http://www.gencat.cat/rodalies/incidencies_rodalies_rss_r16_ca_ES.xml"
    };

    /* Array con los nombres de las lineas */
    public static final String[] nombreLineas = new String[]{
            "R1", "R2 Nord", "R2 Sud", "R3", "R4", "R7", "R8", "R11", "R12", "R13", "R14", "R15", "R16"
    };

    /* Array cuentas twitter */
    public static final String[] usuarioTwitterLineas = new String[]{
            "rodalia1","rodalia2", "rodalia2", "rodalia3","rodalia4","rodalia7","rodalia8","#rod11","#rod12","#rod13",
            "#rod14","#rod15","#rod16"
    };

}
