package com.acooly.module.sms.sender.support.serializer;

import com.acooly.module.sms.sender.support.XmlUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author shuijing
 */
public class AliyunMessageSendSerializer extends XMLSerializer<String> {

    public static final String DEFAULT_XML_NAMESPACE = "http://mns.aliyuncs.com/doc/v1";
    public static final String MESSAGE_TAG = "Message";
    public static final String MESSAGE_BODY_TAG = "MessageBody";
    public static final String MESSAGE_TAG_TAG = "MessageTag";
    public static final String MESSAGE_ATTRIBUTES_TAG = "MessageAttributes";
    public static final String DIRECT_MAIL_TAG = "DirectMail";
    public static final String SMS_TAG = "DirectSMS";
    public static final String WEBSOCKET_TAG = "WebSocket";

    private AliyunMessageSendSerializer() {
    }

    public static final AliyunMessageSendSerializer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String serialize(String paraMapStr, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, MESSAGE_TAG);
        doc.appendChild(root);

        Element node = safeCreateContentElement(doc, MESSAGE_BODY_TAG, "content", "");
        if (node != null) {
            root.appendChild(node);
        }

        Element attributesNode = doc.createElement(MESSAGE_ATTRIBUTES_TAG);
        root.appendChild(attributesNode);

        node = safeCreateContentElement(doc, SMS_TAG, paraMapStr, null);
        if (node != null) {
            attributesNode.appendChild(node);
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);

        return xml;
    }

    private static class SingletonHolder {
        private static final AliyunMessageSendSerializer INSTANCE = new AliyunMessageSendSerializer();
    }
}
