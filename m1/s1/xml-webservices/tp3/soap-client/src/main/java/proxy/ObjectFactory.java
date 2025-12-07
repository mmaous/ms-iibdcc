
package proxy;

import javax.xml.namespace.QName;
import jakarta.xml.bind.JAXBElement;
import jakarta.xml.bind.annotation.XmlElementDecl;
import jakarta.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the proxy package. 
 * <p>An ObjectFactory allows you to programmatically 
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

    private static final QName _ConvertEuroToMAD_QNAME = new QName("http://ws/", "ConvertEuroToMAD");
    private static final QName _ConvertEuroToMADResponse_QNAME = new QName("http://ws/", "ConvertEuroToMADResponse");
    private static final QName _GetAccount_QNAME = new QName("http://ws/", "getAccount");
    private static final QName _GetAccountResponse_QNAME = new QName("http://ws/", "getAccountResponse");
    private static final QName _ListAccounts_QNAME = new QName("http://ws/", "listAccounts");
    private static final QName _ListAccountsResponse_QNAME = new QName("http://ws/", "listAccountsResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: proxy
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ConvertEuroToMAD }
     * 
     * @return
     *     the new instance of {@link ConvertEuroToMAD }
     */
    public ConvertEuroToMAD createConvertEuroToMAD() {
        return new ConvertEuroToMAD();
    }

    /**
     * Create an instance of {@link ConvertEuroToMADResponse }
     * 
     * @return
     *     the new instance of {@link ConvertEuroToMADResponse }
     */
    public ConvertEuroToMADResponse createConvertEuroToMADResponse() {
        return new ConvertEuroToMADResponse();
    }

    /**
     * Create an instance of {@link GetAccount }
     * 
     * @return
     *     the new instance of {@link GetAccount }
     */
    public GetAccount createGetAccount() {
        return new GetAccount();
    }

    /**
     * Create an instance of {@link GetAccountResponse }
     * 
     * @return
     *     the new instance of {@link GetAccountResponse }
     */
    public GetAccountResponse createGetAccountResponse() {
        return new GetAccountResponse();
    }

    /**
     * Create an instance of {@link ListAccounts }
     * 
     * @return
     *     the new instance of {@link ListAccounts }
     */
    public ListAccounts createListAccounts() {
        return new ListAccounts();
    }

    /**
     * Create an instance of {@link ListAccountsResponse }
     * 
     * @return
     *     the new instance of {@link ListAccountsResponse }
     */
    public ListAccountsResponse createListAccountsResponse() {
        return new ListAccountsResponse();
    }

    /**
     * Create an instance of {@link Account }
     * 
     * @return
     *     the new instance of {@link Account }
     */
    public Account createAccount() {
        return new Account();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConvertEuroToMAD }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConvertEuroToMAD }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ConvertEuroToMAD")
    public JAXBElement<ConvertEuroToMAD> createConvertEuroToMAD(ConvertEuroToMAD value) {
        return new JAXBElement<>(_ConvertEuroToMAD_QNAME, ConvertEuroToMAD.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ConvertEuroToMADResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ConvertEuroToMADResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "ConvertEuroToMADResponse")
    public JAXBElement<ConvertEuroToMADResponse> createConvertEuroToMADResponse(ConvertEuroToMADResponse value) {
        return new JAXBElement<>(_ConvertEuroToMADResponse_QNAME, ConvertEuroToMADResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccount }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccount }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getAccount")
    public JAXBElement<GetAccount> createGetAccount(GetAccount value) {
        return new JAXBElement<>(_GetAccount_QNAME, GetAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetAccountResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link GetAccountResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "getAccountResponse")
    public JAXBElement<GetAccountResponse> createGetAccountResponse(GetAccountResponse value) {
        return new JAXBElement<>(_GetAccountResponse_QNAME, GetAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAccounts }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListAccounts }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "listAccounts")
    public JAXBElement<ListAccounts> createListAccounts(ListAccounts value) {
        return new JAXBElement<>(_ListAccounts_QNAME, ListAccounts.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ListAccountsResponse }{@code >}
     * 
     * @param value
     *     Java instance representing xml element's value.
     * @return
     *     the new instance of {@link JAXBElement }{@code <}{@link ListAccountsResponse }{@code >}
     */
    @XmlElementDecl(namespace = "http://ws/", name = "listAccountsResponse")
    public JAXBElement<ListAccountsResponse> createListAccountsResponse(ListAccountsResponse value) {
        return new JAXBElement<>(_ListAccountsResponse_QNAME, ListAccountsResponse.class, null, value);
    }

}
