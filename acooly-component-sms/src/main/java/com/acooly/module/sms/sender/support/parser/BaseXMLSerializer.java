package com.acooly.module.sms.sender.support.parser;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * @author shuijing
 */
public class BaseXMLSerializer<T> {

    protected static DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();

    private static ThreadLocal<DocumentBuilder> sps = new ThreadLocal<DocumentBuilder>();

    protected static DocumentBuilder getDocmentBuilder() throws ParserConfigurationException {
        DocumentBuilder db = sps.get();
        if (db == null) {
            db = factory.newDocumentBuilder();
            sps.set(db);
        }
        return db;
    }
}
