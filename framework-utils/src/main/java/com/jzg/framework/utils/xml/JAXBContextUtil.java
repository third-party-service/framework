package com.jzg.framework.utils.xml;

import javax.xml.bind.*;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by fengbo on 09/06/2017.
 */
public final class JAXBContextUtil {
    private JAXBContextUtil(){ };

    /**
     * Java bean to xml string.
     *
     * @param <T>   the type parameter
     * @param t     the t
     * @return the string
     */
    public static <T> String javaBeanToXml(T t) {

        StringWriter writer = new StringWriter();

        try {
            JAXBContext context = JAXBContext.newInstance(t.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(t, writer);

        } catch (PropertyException e) {
            e.printStackTrace();
        } catch (JAXBException e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    /**
     * xml转bean
     *
     * @param <T> the type parameter
     * @param xml the xml
     * @param c   the c
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public static <T> T xmlToJavaBean(String xml, Class<T> c) {
        T t = null;
        try {
            JAXBContext context = JAXBContext.newInstance(c);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            t = (T) unmarshaller.unmarshal(new StringReader(xml));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return t;
    }

}
