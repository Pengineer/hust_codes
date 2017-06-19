
package csdc.tool.webService.smdb.jaxws;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the csdc.tool.webService.smdb.jaxws package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OperateResponse_QNAME = new QName("http://server.webService.service.csdc/", "operateResponse");
    private final static QName _Arg0_QNAME = new QName("http://csdc.info/", "arg0");
    private final static QName _Operate_QNAME = new QName("http://server.webService.service.csdc/", "operate");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: csdc.tool.webService.smdb.jaxws
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link OperateResponse }
     * 
     */
    public OperateResponse createOperateResponse() {
        return new OperateResponse();
    }

    /**
     * Create an instance of {@link Operate }
     * 
     */
    public Operate createOperate() {
        return new Operate();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link OperateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webService.service.csdc/", name = "operateResponse")
    public JAXBElement<OperateResponse> createOperateResponse(OperateResponse value) {
        return new JAXBElement<OperateResponse>(_OperateResponse_QNAME, OperateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://csdc.info/", name = "arg0")
    public JAXBElement<String> createArg0(String value) {
        return new JAXBElement<String>(_Arg0_QNAME, String.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Operate }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server.webService.service.csdc/", name = "operate")
    public JAXBElement<Operate> createOperate(Operate value) {
        return new JAXBElement<Operate>(_Operate_QNAME, Operate.class, null, value);
    }

}
