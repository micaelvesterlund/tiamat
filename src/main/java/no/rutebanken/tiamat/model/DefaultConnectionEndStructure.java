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
 * Type for a DEFAULT TRANSFER.
 * 
 * <p>Java class for DefaultConnectionEndStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="DefaultConnectionEndStructure">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TransportMode" type="{http://www.netex.org.uk/netex}VehicleModeEnumeration" minOccurs="0"/>
 *         &lt;element ref="{http://www.netex.org.uk/netex}OperatorView" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DefaultConnectionEndStructure", propOrder = {
    "transportMode",
    "operatorView"
})
public class DefaultConnectionEndStructure {

    @XmlElement(name = "TransportMode")
    @XmlSchemaType(name = "NMTOKEN")
    protected VehicleModeEnumeration transportMode;
    @XmlElement(name = "OperatorView")
    protected OperatorView operatorView;

    /**
     * Gets the value of the transportMode property.
     * 
     * @return
     *     possible object is
     *     {@link VehicleModeEnumeration }
     *     
     */
    public VehicleModeEnumeration getTransportMode() {
        return transportMode;
    }

    /**
     * Sets the value of the transportMode property.
     * 
     * @param value
     *     allowed object is
     *     {@link VehicleModeEnumeration }
     *     
     */
    public void setTransportMode(VehicleModeEnumeration value) {
        this.transportMode = value;
    }

    /**
     * Gets the value of the operatorView property.
     * 
     * @return
     *     possible object is
     *     {@link OperatorView }
     *     
     */
    public OperatorView getOperatorView() {
        return operatorView;
    }

    /**
     * Sets the value of the operatorView property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperatorView }
     *     
     */
    public void setOperatorView(OperatorView value) {
        this.operatorView = value;
    }

}