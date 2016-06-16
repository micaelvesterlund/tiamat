//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.adapters.NormalizedStringAdapter;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;


/**
 * Type for Simplified DESTINATION DISPLAY.
 * 
 * <p>Java class for DestinationDisplay_DerivedViewStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DestinationDisplay_DerivedViewStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}DerivedViewStructure">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.netex.org.uk/netex}keyList" minOccurs="0"/>
 *         &lt;element ref="{http://www.netex.org.uk/netex}DestinationDisplayRef" minOccurs="0"/>
 *         &lt;group ref="{http://www.netex.org.uk/netex}DestinationDisplayNameGroup"/>
 *         &lt;group ref="{http://www.netex.org.uk/netex}DestinationDisplayCodeGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DestinationDisplay_DerivedViewStructure", propOrder = {
    "keyList",
    "destinationDisplayRef",
    "name",
    "shortName",
    "sideText",
    "frontText",
    "driverDisplayText",
    "shortCode",
    "publicCode",
    "privateCode"
})
@XmlSeeAlso({
    DestinationDisplayView.class
})
@MappedSuperclass
public class DestinationDisplay_DerivedViewStructure
    extends DerivedViewStructure
{

    @Transient
    protected KeyListStructure keyList;

    @XmlElement(name = "DestinationDisplayRef")
    @Transient
    protected DestinationDisplayRefStructure destinationDisplayRef;

    @XmlElement(name = "Name")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected MultilingualString name;

    @XmlElement(name = "ShortName")
    @Transient
    protected MultilingualString shortName;

    @XmlElement(name = "SideText")
    @Transient
    protected MultilingualString sideText;

    @XmlElement(name = "FrontText")
    @Transient
    protected MultilingualString frontText;

    @XmlElement(name = "DriverDisplayText")
    @Transient
    protected MultilingualString driverDisplayText;

    @XmlElement(name = "ShortCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String shortCode;

    @XmlElement(name = "PublicCode")
    @XmlJavaTypeAdapter(NormalizedStringAdapter.class)
    @XmlSchemaType(name = "normalizedString")
    protected String publicCode;

    @XmlElement(name = "PrivateCode")
    @Transient
    protected PrivateCodeStructure privateCode;

    /**
     * A list of alternative Key values for an element.
     * 
     * @return
     *     possible object is
     *     {@link KeyListStructure }
     *     
     */
    public KeyListStructure getKeyList() {
        return keyList;
    }

    /**
     * Sets the value of the keyList property.
     * 
     * @param value
     *     allowed object is
     *     {@link KeyListStructure }
     *     
     */
    public void setKeyList(KeyListStructure value) {
        this.keyList = value;
    }

    /**
     * Gets the value of the destinationDisplayRef property.
     * 
     * @return
     *     possible object is
     *     {@link DestinationDisplayRefStructure }
     *     
     */
    public DestinationDisplayRefStructure getDestinationDisplayRef() {
        return destinationDisplayRef;
    }

    /**
     * Sets the value of the destinationDisplayRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link DestinationDisplayRefStructure }
     *     
     */
    public void setDestinationDisplayRef(DestinationDisplayRefStructure value) {
        this.destinationDisplayRef = value;
    }

    /**
     * Gets the value of the name property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getName() {
        return name;
    }

    /**
     * Sets the value of the name property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setName(MultilingualString value) {
        this.name = value;
    }

    /**
     * Gets the value of the shortName property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getShortName() {
        return shortName;
    }

    /**
     * Sets the value of the shortName property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setShortName(MultilingualString value) {
        this.shortName = value;
    }

    /**
     * Gets the value of the sideText property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getSideText() {
        return sideText;
    }

    /**
     * Sets the value of the sideText property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setSideText(MultilingualString value) {
        this.sideText = value;
    }

    /**
     * Gets the value of the frontText property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getFrontText() {
        return frontText;
    }

    /**
     * Sets the value of the frontText property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setFrontText(MultilingualString value) {
        this.frontText = value;
    }

    /**
     * Gets the value of the driverDisplayText property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getDriverDisplayText() {
        return driverDisplayText;
    }

    /**
     * Sets the value of the driverDisplayText property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setDriverDisplayText(MultilingualString value) {
        this.driverDisplayText = value;
    }

    /**
     * Gets the value of the shortCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShortCode() {
        return shortCode;
    }

    /**
     * Sets the value of the shortCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShortCode(String value) {
        this.shortCode = value;
    }

    /**
     * Gets the value of the publicCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPublicCode() {
        return publicCode;
    }

    /**
     * Sets the value of the publicCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPublicCode(String value) {
        this.publicCode = value;
    }

    /**
     * Gets the value of the privateCode property.
     * 
     * @return
     *     possible object is
     *     {@link PrivateCodeStructure }
     *     
     */
    public PrivateCodeStructure getPrivateCode() {
        return privateCode;
    }

    /**
     * Sets the value of the privateCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link PrivateCodeStructure }
     *     
     */
    public void setPrivateCode(PrivateCodeStructure value) {
        this.privateCode = value;
    }

}