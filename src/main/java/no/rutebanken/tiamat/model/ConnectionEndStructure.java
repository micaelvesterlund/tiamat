//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for a CONNECTION END.
 * 
 * <p>Java class for ConnectionEndStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="ConnectionEndStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransportMode" type="{http://www.netex.org.uk/netex}AllVehicleModesOfTransportEnumeration" minOccurs="0"/>
 *         &lt;element name="ScheduledStopPointRef" type="{http://www.netex.org.uk/netex}ScheduledStopPointRefStructure" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ConnectionEndStructure", propOrder = {
    "transportMode",
    "scheduledStopPointRef"
})
public class ConnectionEndStructure {

    @XmlElement(name = "TransportMode", defaultValue = "all")
    @XmlSchemaType(name = "NMTOKEN")
    protected AllVehicleModesOfTransportEnumeration transportMode;
    @XmlElement(name = "ScheduledStopPointRef")
    protected ScheduledStopPointRefStructure scheduledStopPointRef;

    /**
     * Gets the value of the transportMode property.
     * 
     * @return
     *     possible object is
     *     {@link AllVehicleModesOfTransportEnumeration }
     *     
     */
    public AllVehicleModesOfTransportEnumeration getTransportMode() {
        return transportMode;
    }

    /**
     * Sets the value of the transportMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link AllVehicleModesOfTransportEnumeration }
     *     
     */
    public void setTransportMode(AllVehicleModesOfTransportEnumeration value) {
        this.transportMode = value;
    }

    /**
     * Gets the value of the scheduledStopPointRef property.
     * 
     * @return
     *     possible object is
     *     {@link ScheduledStopPointRefStructure }
     *     
     */
    public ScheduledStopPointRefStructure getScheduledStopPointRef() {
        return scheduledStopPointRef;
    }

    /**
     * Sets the value of the scheduledStopPointRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ScheduledStopPointRefStructure }
     *     
     */
    public void setScheduledStopPointRef(ScheduledStopPointRefStructure value) {
        this.scheduledStopPointRef = value;
    }

}