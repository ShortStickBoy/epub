package com.sunzn.epub.library;

import android.text.TextUtils;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.File;
import java.util.Iterator;
import java.util.List;

public class XmlHelper {

    public static String getCoverPath(final String xmlPath, String fatherNode, String subtagAttributesName, String subtagAttributesValue) throws DocumentException {

        if (TextUtils.isEmpty(xmlPath))
            return null;
        if (TextUtils.isEmpty(fatherNode))
            return null;
        if (TextUtils.isEmpty(subtagAttributesName))
            return null;

        SAXReader reader = new SAXReader();
        Document document = reader.read(new File(xmlPath));
        // 通过document对象获取根节点bookstore
        Element node = document.getRootElement();
        Iterator<Element> it = node.element(fatherNode).elementIterator();
        // 获取element的id属性节点对象
        if (it != null) {
            while (it.hasNext()) {
                Element e = it.next();
                List<Attribute> list = e.attributes();
                for (Attribute attr : list) {
                    if (attr.getName().equals(subtagAttributesName) && attr.getValue().contains(subtagAttributesValue)) {
                        return attr.getValue();
                    }
                }
            }
        }
        return null;
    }

}
