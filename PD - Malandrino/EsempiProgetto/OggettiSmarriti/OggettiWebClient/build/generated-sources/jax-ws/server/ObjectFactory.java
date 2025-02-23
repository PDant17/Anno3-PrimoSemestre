
package server;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the server package. 
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

    private final static QName _FindByCategoriaResponse_QNAME = new QName("http://server/", "findByCategoriaResponse");
    private final static QName _FindAll_QNAME = new QName("http://server/", "findAll");
    private final static QName _FindByCategoria_QNAME = new QName("http://server/", "findByCategoria");
    private final static QName _FindByLuogo_QNAME = new QName("http://server/", "findByLuogo");
    private final static QName _CreateOggetto_QNAME = new QName("http://server/", "createOggetto");
    private final static QName _FindByLuogoResponse_QNAME = new QName("http://server/", "findByLuogoResponse");
    private final static QName _CreateOggettoResponse_QNAME = new QName("http://server/", "createOggettoResponse");
    private final static QName _DeleteOggettoResponse_QNAME = new QName("http://server/", "deleteOggettoResponse");
    private final static QName _ModifyOggetto_QNAME = new QName("http://server/", "modifyOggetto");
    private final static QName _FindAllResponse_QNAME = new QName("http://server/", "findAllResponse");
    private final static QName _ModifyOggettoResponse_QNAME = new QName("http://server/", "modifyOggettoResponse");
    private final static QName _FindById_QNAME = new QName("http://server/", "findById");
    private final static QName _FindByIdResponse_QNAME = new QName("http://server/", "findByIdResponse");
    private final static QName _DeleteOggetto_QNAME = new QName("http://server/", "deleteOggetto");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: server
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CreateOggetto }
     * 
     */
    public CreateOggetto createCreateOggetto() {
        return new CreateOggetto();
    }

    /**
     * Create an instance of {@link FindByLuogo }
     * 
     */
    public FindByLuogo createFindByLuogo() {
        return new FindByLuogo();
    }

    /**
     * Create an instance of {@link FindByCategoria }
     * 
     */
    public FindByCategoria createFindByCategoria() {
        return new FindByCategoria();
    }

    /**
     * Create an instance of {@link FindAll }
     * 
     */
    public FindAll createFindAll() {
        return new FindAll();
    }

    /**
     * Create an instance of {@link FindByCategoriaResponse }
     * 
     */
    public FindByCategoriaResponse createFindByCategoriaResponse() {
        return new FindByCategoriaResponse();
    }

    /**
     * Create an instance of {@link DeleteOggetto }
     * 
     */
    public DeleteOggetto createDeleteOggetto() {
        return new DeleteOggetto();
    }

    /**
     * Create an instance of {@link ModifyOggettoResponse }
     * 
     */
    public ModifyOggettoResponse createModifyOggettoResponse() {
        return new ModifyOggettoResponse();
    }

    /**
     * Create an instance of {@link FindById }
     * 
     */
    public FindById createFindById() {
        return new FindById();
    }

    /**
     * Create an instance of {@link FindByIdResponse }
     * 
     */
    public FindByIdResponse createFindByIdResponse() {
        return new FindByIdResponse();
    }

    /**
     * Create an instance of {@link FindAllResponse }
     * 
     */
    public FindAllResponse createFindAllResponse() {
        return new FindAllResponse();
    }

    /**
     * Create an instance of {@link CreateOggettoResponse }
     * 
     */
    public CreateOggettoResponse createCreateOggettoResponse() {
        return new CreateOggettoResponse();
    }

    /**
     * Create an instance of {@link DeleteOggettoResponse }
     * 
     */
    public DeleteOggettoResponse createDeleteOggettoResponse() {
        return new DeleteOggettoResponse();
    }

    /**
     * Create an instance of {@link ModifyOggetto }
     * 
     */
    public ModifyOggetto createModifyOggetto() {
        return new ModifyOggetto();
    }

    /**
     * Create an instance of {@link FindByLuogoResponse }
     * 
     */
    public FindByLuogoResponse createFindByLuogoResponse() {
        return new FindByLuogoResponse();
    }

    /**
     * Create an instance of {@link Date }
     * 
     */
    public Date createDate() {
        return new Date();
    }

    /**
     * Create an instance of {@link Oggetto }
     * 
     */
    public Oggetto createOggetto() {
        return new Oggetto();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCategoriaResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findByCategoriaResponse")
    public JAXBElement<FindByCategoriaResponse> createFindByCategoriaResponse(FindByCategoriaResponse value) {
        return new JAXBElement<FindByCategoriaResponse>(_FindByCategoriaResponse_QNAME, FindByCategoriaResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAll }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findAll")
    public JAXBElement<FindAll> createFindAll(FindAll value) {
        return new JAXBElement<FindAll>(_FindAll_QNAME, FindAll.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByCategoria }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findByCategoria")
    public JAXBElement<FindByCategoria> createFindByCategoria(FindByCategoria value) {
        return new JAXBElement<FindByCategoria>(_FindByCategoria_QNAME, FindByCategoria.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByLuogo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findByLuogo")
    public JAXBElement<FindByLuogo> createFindByLuogo(FindByLuogo value) {
        return new JAXBElement<FindByLuogo>(_FindByLuogo_QNAME, FindByLuogo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOggetto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createOggetto")
    public JAXBElement<CreateOggetto> createCreateOggetto(CreateOggetto value) {
        return new JAXBElement<CreateOggetto>(_CreateOggetto_QNAME, CreateOggetto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByLuogoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findByLuogoResponse")
    public JAXBElement<FindByLuogoResponse> createFindByLuogoResponse(FindByLuogoResponse value) {
        return new JAXBElement<FindByLuogoResponse>(_FindByLuogoResponse_QNAME, FindByLuogoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateOggettoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "createOggettoResponse")
    public JAXBElement<CreateOggettoResponse> createCreateOggettoResponse(CreateOggettoResponse value) {
        return new JAXBElement<CreateOggettoResponse>(_CreateOggettoResponse_QNAME, CreateOggettoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOggettoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "deleteOggettoResponse")
    public JAXBElement<DeleteOggettoResponse> createDeleteOggettoResponse(DeleteOggettoResponse value) {
        return new JAXBElement<DeleteOggettoResponse>(_DeleteOggettoResponse_QNAME, DeleteOggettoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyOggetto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "modifyOggetto")
    public JAXBElement<ModifyOggetto> createModifyOggetto(ModifyOggetto value) {
        return new JAXBElement<ModifyOggetto>(_ModifyOggetto_QNAME, ModifyOggetto.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindAllResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findAllResponse")
    public JAXBElement<FindAllResponse> createFindAllResponse(FindAllResponse value) {
        return new JAXBElement<FindAllResponse>(_FindAllResponse_QNAME, FindAllResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ModifyOggettoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "modifyOggettoResponse")
    public JAXBElement<ModifyOggettoResponse> createModifyOggettoResponse(ModifyOggettoResponse value) {
        return new JAXBElement<ModifyOggettoResponse>(_ModifyOggettoResponse_QNAME, ModifyOggettoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findById")
    public JAXBElement<FindById> createFindById(FindById value) {
        return new JAXBElement<FindById>(_FindById_QNAME, FindById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FindByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "findByIdResponse")
    public JAXBElement<FindByIdResponse> createFindByIdResponse(FindByIdResponse value) {
        return new JAXBElement<FindByIdResponse>(_FindByIdResponse_QNAME, FindByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteOggetto }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://server/", name = "deleteOggetto")
    public JAXBElement<DeleteOggetto> createDeleteOggetto(DeleteOggetto value) {
        return new JAXBElement<DeleteOggetto>(_DeleteOggetto_QNAME, DeleteOggetto.class, null, value);
    }

}
