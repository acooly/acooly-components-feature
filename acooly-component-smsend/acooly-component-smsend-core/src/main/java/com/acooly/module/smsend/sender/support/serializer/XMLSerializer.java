package com.acooly.module.smsend.sender.support.serializer;

import com.acooly.module.smsend.sender.support.parser.BaseXMLSerializer;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author shuijing
 */
public abstract class XMLSerializer<T> extends BaseXMLSerializer<T> implements Serializer<T> {

    public Element safeCreateContentElement(
            Document doc, String tagName, Object value, String defaultValue) {
        if (value == null && defaultValue == null) {
            return null;
        }

        Element node = doc.createElement(tagName);
        if (value != null) {
            node.setTextContent(value.toString());
        } else {
            node.setTextContent(defaultValue);
        }
        return node;
    }
}
