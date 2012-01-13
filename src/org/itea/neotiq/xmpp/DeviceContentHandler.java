package org.itea.neotiq.xmpp;

import org.itea.neotiq.devicemodel.DeviceToCreate;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;

import android.util.Log;

public class DeviceContentHandler implements ContentHandler {
    
    private DeviceToCreate d;
    
    public DeviceContentHandler() {
        super();
    }

    public void characters(char[] ch, int start, int length) throws SAXException {
        Log.d("characters", "ch:"+String.valueOf(ch)+" start: "+start+" length: "+length);
    }

    public void endDocument() throws SAXException {
        Log.d("endDocument", "");

    }

    public void endElement(String uri, String localName, String qName) throws SAXException {
        Log.d("endElement", "uri: " +uri+" localName: "+localName+" qName: "+qName);

    }

    public void endPrefixMapping(String prefix) throws SAXException {
        Log.d("endPrefixMapping", prefix);
    }

    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {
        Log.d("ignorableWhitespace", "ch: "+String.valueOf(ch)+" start: "+start+" length: "+length);
    }

    public void processingInstruction(String target, String data) throws SAXException {
        Log.d("processingInstruction", "target: "+target+" data: "+data);
    }

    public void setDocumentLocator(Locator locator) {
        Log.d("setDocumentLocator", locator.toString());
    }

    public void skippedEntity(String name) throws SAXException {
        Log.d("skippedEntity", name);
    }

    public void startDocument() throws SAXException {
        Log.d("startDocument", "");
    }

    public void startElement(String uri, String localName, String qName, Attributes atts)
            throws SAXException {
        Log.d("startElement", "uri: " +uri+" localName: "+localName+" qName: "+qName+" attributes: "+atts);
        d = new DeviceToCreate();
        d.setName(atts.getValue("name"));
        d.setType(atts.getValue("type"));
    }

    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        Log.d("startPrefixMapping", "prefix: "+prefix+" uri: "+uri);
    }

    public DeviceToCreate getParsedData() {
        return d;
    }

}
