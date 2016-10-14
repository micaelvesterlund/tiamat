//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for a STAIRCASE EQUIPMENT.
 * 
 * <p>Java class for StaircaseEquipment_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="StaircaseEquipment_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}StairEquipment_VersionStructure">
 *       &lt;group ref="{http://www.netex.org.uk/netex}StaircaseGroup"/>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "StaircaseEquipment_VersionStructure", propOrder = {
    "continuousHandrail",
    "spiralStair",
    "numberOfFlights",
    "flights"
})
@XmlSeeAlso({
    StaircaseEquipment.class
})
public class StaircaseEquipment_VersionStructure
    extends StairEquipment_VersionStructure
{

    @XmlElement(name = "ContinuousHandrail")
    protected Boolean continuousHandrail;
    @XmlElement(name = "SpiralStair")
    protected Boolean spiralStair;
    @XmlElement(name = "NumberOfFlights")
    protected BigInteger numberOfFlights;
    protected StairFlights_RelStructure flights;

    /**
     * Gets the value of the continuousHandrail property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isContinuousHandrail() {
        return continuousHandrail;
    }

    /**
     * Sets the value of the continuousHandrail property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setContinuousHandrail(Boolean value) {
        this.continuousHandrail = value;
    }

    /**
     * Gets the value of the spiralStair property.
     * 
     * @return
     *     possible object is
     *     {@link Boolean }
     *     
     */
    public Boolean isSpiralStair() {
        return spiralStair;
    }

    /**
     * Sets the value of the spiralStair property.
     * 
     * @param value
     *     allowed object is
     *     {@link Boolean }
     *     
     */
    public void setSpiralStair(Boolean value) {
        this.spiralStair = value;
    }

    /**
     * Gets the value of the numberOfFlights property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getNumberOfFlights() {
        return numberOfFlights;
    }

    /**
     * Sets the value of the numberOfFlights property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setNumberOfFlights(BigInteger value) {
        this.numberOfFlights = value;
    }

    /**
     * Gets the value of the flights property.
     * 
     * @return
     *     possible object is
     *     {@link StairFlights_RelStructure }
     *     
     */
    public StairFlights_RelStructure getFlights() {
        return flights;
    }

    /**
     * Sets the value of the flights property.
     * 
     * @param value
     *     allowed object is
     *     {@link StairFlights_RelStructure }
     *     
     */
    public void setFlights(StairFlights_RelStructure value) {
        this.flights = value;
    }

}