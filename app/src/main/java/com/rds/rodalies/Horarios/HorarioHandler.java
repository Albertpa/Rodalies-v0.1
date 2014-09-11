package com.rds.rodalies.Horarios;

/**
 * Created by RDS on 03/07/14.

 *Basada en la app de * Jon Segador
 **/

import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.util.Vector;

public class HorarioHandler extends DefaultHandler{

    public HorarioHandler() {
        super();
        this.MyParsedExampleDataSets = new Vector<ParsedHorarioDataSet>();
    }

    private ParsedHorarioDataSet DataSet;
    private Vector<ParsedHorarioDataSet> MyParsedExampleDataSets;

    public Vector<ParsedHorarioDataSet> getParsedExampleDataSets() {
        return this.MyParsedExampleDataSets;
    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }


    @Override
    public void startElement(String namespaceURI, String localName,
                             String qName, org.xml.sax.Attributes atts) throws SAXException {
        if (localName.equals("salida")) {
            DataSet.setHoraSalida(atts.getValue("value"));
        }else if (localName.equals("llegada")) {
            DataSet.setHoraLlegada(atts.getValue("value"));
        }else if (localName.equals("tiempo")) {
            DataSet.setTiempo(atts.getValue("value"));
        }else if (localName.equals("lineasalida")) {
            DataSet.setLineaSalida(atts.getValue("value"));
        }else if (localName.equals("transbordo")) {
            DataSet.setTransbordo(atts.getValue("value"));
        }else if (localName.equals("transbordolinea")) {
            DataSet.setTransbordoLinea(atts.getValue("value"));
        }else if (localName.equals("Horario")) {
            DataSet = new ParsedHorarioDataSet();
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName)
            throws SAXException {
        if (localName.equals("salida")) {

        }
        else if (localName.equals("llegada")){

        }
        else if (localName.equals("tiempo")) {

        }
        else if (localName.equals("lineasalida")) {

        }
        else if (localName.equals("transbordo")) {

        }
        else if (localName.equals("transbordolinea")) {

        }
        else if (localName.equals("Horario")) {
            MyParsedExampleDataSets.add(DataSet);
        }
    }

    @Override
    public void characters(char ch[], int start, int length) {

    }

}
