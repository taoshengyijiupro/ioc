package com.shipparts.xml;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.util.List;

public class ExtClassPathXmlApplicationContext {

    private String xmlPath;

    public ExtClassPathXmlApplicationContext(String xmlPath) {
        this.xmlPath = xmlPath;
    }


    public Object getBean(String beanId) throws Exception {
        if (StringUtils.isEmpty(beanId)) {
            throw new Exception("beanId不能为空！");
        }
        List<Element> readerXML = readerXML();
        if (readerXML == null || readerXML.isEmpty()) {
            throw new Exception("配置文件中没有配置bean信息！");
        }
        String byElementClass = findByElementClass(readerXML, beanId);
        if (StringUtils.isEmpty(byElementClass)){
            throw new Exception("该bean对象没有配置class地址");
        }
        return newInstance(byElementClass);
    }

    public String findByElementClass(List<Element> elementList, String beanId) {
        for (Element element : elementList) {
            String xmlBeanId = element.attributeValue("id");
            if (StringUtils.isEmpty(xmlBeanId)) {
                continue;
            }
            if (xmlBeanId.equals(beanId)) {
                String xmlClass = element.attributeValue("class");
                return xmlClass;
            }
        }
        return null;
    }

    public Object newInstance(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> classInfo = Class.forName(className);
        return classInfo.newInstance();
    }

    public List<Element> readerXML() throws DocumentException {
        SAXReader saxReader = new SAXReader();
        Document document = saxReader.read(getResourceAsStream(xmlPath));
        Element rootElement = document.getRootElement();
        List<Element> elements = rootElement.elements();
        return elements;
    }

    /**
     * 获取当前上下文路径
     *
     * @param xmlPath
     * @return
     */
    public InputStream getResourceAsStream(String xmlPath) {
        InputStream resourceAsStream = this.getClass().getClassLoader().getResourceAsStream(xmlPath);
        return resourceAsStream;
    }
}
