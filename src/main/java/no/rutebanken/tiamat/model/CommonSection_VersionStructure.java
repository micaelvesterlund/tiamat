//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for COMMON SECTION.
 * 
 * <p>Java class for CommonSection_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="CommonSection_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}GroupOfEntities_VersionStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}CommonSectionGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CommonSection_VersionStructure", propOrder = {
    "usedIn",
    "members"
})
@XmlSeeAlso({
    CommonSection.class,
    LineSection_VersionStructure.class,
    FareSection_VersionStructure.class
})
public class CommonSection_VersionStructure
    extends GroupOfEntities_VersionStructure
{

    protected CommonSectionSequenceMembers_RelStructure usedIn;
    protected CommonSectionPointMembers_RelStructure members;

    /**
     * Gets the value of the usedIn property.
     * 
     * @return
     *     possible object is
     *     {@link CommonSectionSequenceMembers_RelStructure }
     *     
     */
    public CommonSectionSequenceMembers_RelStructure getUsedIn() {
        return usedIn;
    }

    /**
     * Sets the value of the usedIn property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonSectionSequenceMembers_RelStructure }
     *     
     */
    public void setUsedIn(CommonSectionSequenceMembers_RelStructure value) {
        this.usedIn = value;
    }

    /**
     * Gets the value of the members property.
     * 
     * @return
     *     possible object is
     *     {@link CommonSectionPointMembers_RelStructure }
     *     
     */
    public CommonSectionPointMembers_RelStructure getMembers() {
        return members;
    }

    /**
     * Sets the value of the members property.
     * 
     * @param value
     *     allowed object is
     *     {@link CommonSectionPointMembers_RelStructure }
     *     
     */
    public void setMembers(CommonSectionPointMembers_RelStructure value) {
        this.members = value;
    }

}