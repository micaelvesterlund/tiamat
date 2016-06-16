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
 * Type for an OPERATING DEPARTMENT.
 * 
 * <p>Java class for OperatingDepartment_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperatingDepartment_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}Department_VersionStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}OperatingDepartmentGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperatingDepartment_VersionStructure", propOrder = {
    "operationalContexts"
})
@XmlSeeAlso({
    OperatingDepartment.class
})
public class OperatingDepartment_VersionStructure
    extends Department_VersionStructure
{

    protected OperationalContexRefs_RelStructure operationalContexts;

    /**
     * Gets the value of the operationalContexts property.
     * 
     * @return
     *     possible object is
     *     {@link OperationalContexRefs_RelStructure }
     *     
     */
    public OperationalContexRefs_RelStructure getOperationalContexts() {
        return operationalContexts;
    }

    /**
     * Sets the value of the operationalContexts property.
     * 
     * @param value
     *     allowed object is
     *     {@link OperationalContexRefs_RelStructure }
     *     
     */
    public void setOperationalContexts(OperationalContexRefs_RelStructure value) {
        this.operationalContexts = value;
    }

}