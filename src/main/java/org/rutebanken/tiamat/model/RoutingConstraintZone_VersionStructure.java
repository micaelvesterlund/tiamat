//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for ROUTING CONSTRAINT ZONE.
 * 
 * <p>Java class for RoutingConstraintZone_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="RoutingConstraintZone_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}Zone_VersionStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}RoutingConstraintZoneGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoutingConstraintZone_VersionStructure", propOrder = {
    "zoneUse",
    "pointsInPattern",
    "lines",
    "groupOfLinesRef"
})
@XmlSeeAlso({
    RoutingConstraintZone.class
})
public class RoutingConstraintZone_VersionStructure
    extends Zone_VersionStructure
{

    @XmlElement(name = "ZoneUse")
    @XmlSchemaType(name = "string")
    protected ZoneUseEnumeration zoneUse;
    protected PointsInJourneyPattern_RelStructure pointsInPattern;
    protected LineRefs_RelStructure lines;
    @XmlElementRef(name = "GroupOfLinesRef", namespace = "http://www.netex.org.uk/netex", type = JAXBElement.class, required = false)
    protected JAXBElement<? extends GroupOfLinesRefStructure> groupOfLinesRef;

    /**
     * Gets the value of the zoneUse property.
     * 
     * @return
     *     possible object is
     *     {@link ZoneUseEnumeration }
     *     
     */
    public ZoneUseEnumeration getZoneUse() {
        return zoneUse;
    }

    /**
     * Sets the value of the zoneUse property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZoneUseEnumeration }
     *     
     */
    public void setZoneUse(ZoneUseEnumeration value) {
        this.zoneUse = value;
    }

    /**
     * Gets the value of the pointsInPattern property.
     * 
     * @return
     *     possible object is
     *     {@link PointsInJourneyPattern_RelStructure }
     *     
     */
    public PointsInJourneyPattern_RelStructure getPointsInPattern() {
        return pointsInPattern;
    }

    /**
     * Sets the value of the pointsInPattern property.
     * 
     * @param value
     *     allowed object is
     *     {@link PointsInJourneyPattern_RelStructure }
     *     
     */
    public void setPointsInPattern(PointsInJourneyPattern_RelStructure value) {
        this.pointsInPattern = value;
    }

    /**
     * Gets the value of the lines property.
     * 
     * @return
     *     possible object is
     *     {@link LineRefs_RelStructure }
     *     
     */
    public LineRefs_RelStructure getLines() {
        return lines;
    }

    /**
     * Sets the value of the lines property.
     * 
     * @param value
     *     allowed object is
     *     {@link LineRefs_RelStructure }
     *     
     */
    public void setLines(LineRefs_RelStructure value) {
        this.lines = value;
    }

    /**
     * Gets the value of the groupOfLinesRef property.
     * 
     * @return
     *     possible object is
     *     {@link JAXBElement }{@code <}{@link NetworkRefStructure }{@code >}
     *     {@link JAXBElement }{@code <}{@link GroupOfLinesRefStructure }{@code >}
     *     
     */
    public JAXBElement<? extends GroupOfLinesRefStructure> getGroupOfLinesRef() {
        return groupOfLinesRef;
    }

    /**
     * Sets the value of the groupOfLinesRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link JAXBElement }{@code <}{@link NetworkRefStructure }{@code >}
     *     {@link JAXBElement }{@code <}{@link GroupOfLinesRefStructure }{@code >}
     *     
     */
    public void setGroupOfLinesRef(JAXBElement<? extends GroupOfLinesRefStructure> value) {
        this.groupOfLinesRef = value;
    }

}