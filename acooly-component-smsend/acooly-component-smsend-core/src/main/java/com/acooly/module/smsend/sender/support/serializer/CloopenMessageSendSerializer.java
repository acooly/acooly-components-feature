package com.acooly.module.smsend.sender.support.serializer;

import com.acooly.module.smsend.sender.support.XmlUtil;
import lombok.extern.slf4j.Slf4j;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import java.util.Map;

/**
 * @author shuijing
 */
@Slf4j
public class CloopenMessageSendSerializer extends XMLSerializer<Map<String, Object>> {

    public static final String TEMPLATE_SMS = "TemplateSMS";
    public static final String TO = "to";
    public static final String APP_ID = "appId";
    public static final String TEMPLATE_ID = "templateId";
    public static final String DATAS = "datas";
    public static final String DATA = "data";

    private CloopenMessageSendSerializer() {
    }

    public static final CloopenMessageSendSerializer getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String serialize(Map<String, Object> params, String encoding) throws Exception {
        Document doc = getDocmentBuilder().newDocument();

        Element root = doc.createElement(TEMPLATE_SMS);
        //Element root = doc.createElementNS(DEFAULT_XML_NAMESPACE, MESSAGE_TAG);
        doc.appendChild(root);

        Element to = safeCreateContentElement(doc, TO, params.get(TO), "");
        if (to != null) {
            root.appendChild(to);
        }

        Element appId = safeCreateContentElement(doc, APP_ID, params.get(APP_ID), "");
        if (appId != null) {
            root.appendChild(appId);
        }

        Element templateId = safeCreateContentElement(doc, TEMPLATE_ID, params.get(TEMPLATE_ID), "");
        if (templateId != null) {
            root.appendChild(templateId);
        }

        Element datas = doc.createElement(DATAS);
        root.appendChild(datas);
        List dataArray = (List) params.get(DATAS);
        for (int i = 0; i < dataArray.size(); i++) {
            Element data = safeCreateContentElement(doc, DATA, dataArray.get(i), null);
            if (data != null) {
                datas.appendChild(data);
            }
        }

        String xml = XmlUtil.xmlNodeToString(doc, encoding);
        return xml;
        //log.info("send xml is: {}",xml);
    }

    private static class SingletonHolder {
        private static final CloopenMessageSendSerializer INSTANCE = new CloopenMessageSendSerializer();
    }
}
