//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for a TRANSFER.
 * 
 * <p>Java class for Transfer_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Transfer_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}DataManagedObjectStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}TransferGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Transfer_VersionStructure", propOrder = {
    "name",
    "typeOfTransferRef",
    "description",
    "distance",
    "transferDuration",
    "walkTransferDuration",
    "bothWays"
})
@XmlSeeAlso({
    Connection_VersionStructure.class,
    Access_VersionStructure.class,
    DefaultConnection_VersionStructure.class,
    SiteConnection_VersionStructure.class
})
public abstract class Transfer_VersionStructure
    extends DataManagedObjectStructure
{

    @XmlElement(name = "Name")
    protected MultilingualString name;
    @XmlElement(name = "TypeOfTransferRef")
    protected TypeOfTransferRefStructure typeOfTransferRef;
    @XmlElement(name = "Description")
    protected MultilingualString description;
    @XmlElement(name = "Distance")
    protected BigDecimal distance;
    @XmlElement(name = "TransferDuration")
    protected TransferDurationStructure transferDuration;
    @XmlElement(name = "WalkTransferDuration")
    protected TransferDurationStructure walkTransferDuration;
    @XmlElement(name = "BothWays", defaultValue = "true")
    protected Boolean bothWays;

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
     * Gets the value of the typeOfTransferRef property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfTransferRefStructure }
     *     
     */
    public TypeOfTransferRefStructure getTypeOfTransferRef() {
        return typeOfTransferRef;
    }

    /**
     * Sets the value of the typeOfTransferRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfTransferRefStructure }
     *     
     */
    public void setTypeOfTransferRef(TypeOfTransferRefStructure value) {
        this.typeOfTransferRef = value;
    }

    /**
     * Gets the value of the description property.
     * 
     * @return
     *     possible object is
     *     {@link MultilingualString }
     *     
     */
    public MultilingualString getDescription() {
        return description;
    }

    /**
     * Sets the value of the description property.
     * 
     * @param value
     *     allowed object is
     *     {@link MultilingualString }
     *     
     */
    public void setDescription(MultilingualString value) {
        this.description = value;
    }

    /**
     * Gets the value of the distance property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getDistance() {
        return distance;
    }

    /**
     * Sets the value of the distance property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setDistance(BigDecimal value) {
        this.distance = value;
    }

    /**
     * Gets the value of the transferDuration property.
     * 
     * @return
     *     possible object is
     *     {@link TransferDurationStructure }
     *     
     */
    public TransferDurationStructure getTransferDuration() {
        return transferDuration;
    }

    /**
     * Sets the value of the transferDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferDurationStructure }
     *     
     */
    public void setTransferDuration(TransferDurationStructure value) {
        this.transferDuration = value;
    }

    /**
     * Gets the value of the walkTransferDuration property.
     * 
     * @return
     *     possible object is
     *     {@link TransferDurationStructure }
     *     
     */
    public TransferDurationStructure getWalkTransferDuration() {
        return walkTransferDuration;
    }

    /**
     * Sets the value of the walkTransferDuration property.
     * 
     * @param value
     *     allowed object is
     *     {@link TransferDurationStructure }
     *     
     */
    public void setWalkTransferDuration(TransferDurationStructure value) {
        this.walkTransferDuration = value;
    }

    /**
     * Gets the value of the bothWays property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isBothWays() {
        return bothWays;
    }

    /**
     * Sets the value of the bothWays property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setBothWays(Boolean value) {
        this.bothWays = value;
    }

}