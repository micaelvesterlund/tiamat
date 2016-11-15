//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for an ADDRESSABLE PLACE.
 * 
 * <p>Java class for AddressablePlace_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="AddressablePlace_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}Place_VersionStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}AddressablePlaceGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AddressablePlace_VersionStructure", propOrder = {
    "url",
    "image",
    "postalAddress",
    "roadAddress"
})
@XmlSeeAlso({
    AddressablePlace.class,
    Garage_VersionStructure.class,
    SiteElement_VersionStructure.class
})
@MappedSuperclass
public class AddressablePlace_VersionStructure
    extends Place_VersionStructure
{

    @Transient
    protected String url;

    @Transient
    protected String image;

    @Transient
    protected PostalAddress postalAddress;


//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Transient
    protected RoadAddress roadAddress;

    public AddressablePlace_VersionStructure(MultilingualString name) {
        super(name);
    }

    public AddressablePlace_VersionStructure() {}

    /**
     * Gets the value of the url property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUrl() {
        return url;
    }

    /**
     * Sets the value of the url property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUrl(String value) {
        this.url = value;
    }

    /**
     * Gets the value of the image property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getImage() {
        return image;
    }

    /**
     * Sets the value of the image property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setImage(String value) {
        this.image = value;
    }

    /**
     * Gets the value of the postalAddress property.
     * 
     * @return
     *     possible object is
     *     {@link PostalAddress }
     *     
     */
    public PostalAddress getPostalAddress() {
        return postalAddress;
    }

    /**
     * Sets the value of the postalAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link PostalAddress }
     *     
     */
    public void setPostalAddress(PostalAddress value) {
        this.postalAddress = value;
    }

    /**
     * ADDRESS of a numbered building on a named road.
     * 
     * @return
     *     possible object is
     *     {@link RoadAddress }
     *     
     */
    public RoadAddress getRoadAddress() {
        return roadAddress;
    }

    /**
     * Sets the value of the roadAddress property.
     * 
     * @param value
     *     allowed object is
     *     {@link RoadAddress }
     *     
     */
    public void setRoadAddress(RoadAddress value) {
        this.roadAddress = value;
    }

}
