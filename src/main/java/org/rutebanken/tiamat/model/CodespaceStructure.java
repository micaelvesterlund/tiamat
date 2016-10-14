//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Type for CODESPACE.
 * 
 * <p>Java class for CodespaceStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CodespaceStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}EntityStructure">
 *       &lt;sequence>
 *         &lt;element name="Xmlns" type="{http://www.netex.org.uk/netex}CodespaceIdType"/>
 *         &lt;element name="XmlnsUrl" type="{http://www.w3.org/2001/XMLSchema}anyURI" minOccurs="0"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dataSourceRef" type="{http://www.netex.org.uk/netex}DataSourceIdType" />
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CodespaceStructure", propOrder = {
    "xmlns",
    "xmlnsUrl",
    "description"
})
@XmlSeeAlso({
    Codespace.class
})
public class CodespaceStructure
    extends EntityStructure
{

    @XmlElement(name = "Xmlns", required = true)
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String xmlns;
    @XmlElement(name = "XmlnsUrl")
    @XmlSchemaType(name = "anyURI")
    protected String xmlnsUrl;
    @XmlElement(name = "Description")
    protected String description;
    @XmlAttribute(name = "dataSourceRef")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    protected String dataSourceRef;

    /**
     * Gets the value of the xmlns property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlns() {
        return xmlns;
    }

    /**
     * Sets the value of the xmlns property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlns(String value) {
        this.xmlns = value;
    }

    /**
     * Gets the value of the xmlnsUrl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getXmlnsUrl() {
        return xmlnsUrl;
    }

    /**
     * Sets the value of the xmlnsUrl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setXmlnsUrl(String value) {
        this.xmlnsUrl = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * Gets the value of the dataSourceRef property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDataSourceRef() {
        return dataSourceRef;
    }

    /**
     * Sets the value of the dataSourceRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDataSourceRef(String value) {
        this.dataSourceRef = value;
    }

}