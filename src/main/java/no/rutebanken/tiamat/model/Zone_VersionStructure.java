//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import net.opengis.gml._3.PolygonType;

import javax.persistence.*;
import javax.xml.bind.annotation.*;


/**
 * Type for a ZONE.
 * 
 * <p>Java class for Zone_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Zone_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}GroupOfPoints_VersionStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}ZoneGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Zone_VersionStructure", propOrder = {
    "types",
    "centroid",
    "polygon",
    "projections",
    "parentZoneRef"
})
@XmlSeeAlso({
    AccessZone_VersionStructure.class,
    GeneralZone_VersionStructure.class,
    AdministrativeZone_VersionStructure.class,
    RoutingConstraintZone_VersionStructure.class,
    TariffZone_VersionStructure.class,
    StopArea_VersionStructure.class,
    Place_VersionStructure.class
})
@MappedSuperclass
public class Zone_VersionStructure
    extends GroupOfPoints_VersionStructure
{

    @Transient
    protected TypeOfZoneRefs_RelStructure types;

    @XmlElement(name = "Centroid")
    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    protected SimplePoint centroid;

    @XmlElement(name = "Polygon", namespace = "http://www.opengis.net/gml/3.2")
    @Transient
    protected PolygonType polygon;

    @Transient
    protected Projections_RelStructure projections;

    @XmlElement(name = "ParentZoneRef")
    @Transient
    protected ZoneRefStructure parentZoneRef;

    /**
     * Gets the value of the types property.
     * 
     * @return
     *     possible object is
     *     {@link TypeOfZoneRefs_RelStructure }
     *     
     */
    public TypeOfZoneRefs_RelStructure getTypes() {
        return types;
    }

    /**
     * Sets the value of the types property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypeOfZoneRefs_RelStructure }
     *     
     */
    public void setTypes(TypeOfZoneRefs_RelStructure value) {
        this.types = value;
    }

    /**
     * Gets the value of the centroid property.
     * 
     * @return
     *     possible object is
     *     {@link SimplePoint }
     *     
     */
    public SimplePoint getCentroid() {
        return centroid;
    }

    /**
     * Sets the value of the centroid property.
     * 
     * @param value
     *     allowed object is
     *     {@link SimplePoint }
     *     
     */
    public void setCentroid(SimplePoint value) {
        this.centroid = value;
    }

    /**
     * Gets the value of the polygon property.
     * 
     * @return
     *     possible object is
     *     {@link PolygonType }
     *     
     */
    public PolygonType getPolygon() {
        return polygon;
    }

    /**
     * Sets the value of the polygon property.
     * 
     * @param value
     *     allowed object is
     *     {@link PolygonType }
     *     
     */
    public void setPolygon(PolygonType value) {
        this.polygon = value;
    }

    /**
     * Gets the value of the projections property.
     * 
     * @return
     *     possible object is
     *     {@link Projections_RelStructure }
     *     
     */
    public Projections_RelStructure getProjections() {
        return projections;
    }

    /**
     * Sets the value of the projections property.
     * 
     * @param value
     *     allowed object is
     *     {@link Projections_RelStructure }
     *     
     */
    public void setProjections(Projections_RelStructure value) {
        this.projections = value;
    }

    /**
     * Gets the value of the parentZoneRef property.
     * 
     * @return
     *     possible object is
     *     {@link ZoneRefStructure }
     *     
     */
    public ZoneRefStructure getParentZoneRef() {
        return parentZoneRef;
    }

    /**
     * Sets the value of the parentZoneRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ZoneRefStructure }
     *     
     */
    public void setParentZoneRef(ZoneRefStructure value) {
        this.parentZoneRef = value;
    }

}