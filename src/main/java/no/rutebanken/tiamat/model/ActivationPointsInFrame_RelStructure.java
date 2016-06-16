//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElementRef;
import javax.xml.bind.annotation.XmlType;


/**
 * Type for containment in frame of ACTIVATION POINTs.
 * 
 * <p>Java class for activationPointsInFrame_RelStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="activationPointsInFrame_RelStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}containmentAggregationStructure">
 *       &lt;sequence>
 *         &lt;element ref="{http://www.netex.org.uk/netex}ActivationPoint_" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "activationPointsInFrame_RelStructure", propOrder = {
    "activationPoint_"
})
public class ActivationPointsInFrame_RelStructure
    extends ContainmentAggregationStructure
{

    @XmlElementRef(name = "ActivationPoint_", namespace = "http://www.netex.org.uk/netex", type = JAXBElement.class)
    protected List<JAXBElement<? extends Point_VersionStructure>> activationPoint_;

    /**
     * Gets the value of the activationPoint property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the activationPoint property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getActivationPoint_().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link JAXBElement }{@code <}{@link ActivationPoint }{@code >}
     * {@link JAXBElement }{@code <}{@link Point_VersionStructure }{@code >}
     * {@link JAXBElement }{@code <}{@link BeaconPoint }{@code >}
     * 
     * 
     */
    public List<JAXBElement<? extends Point_VersionStructure>> getActivationPoint_() {
        if (activationPoint_ == null) {
            activationPoint_ = new ArrayList<JAXBElement<? extends Point_VersionStructure>>();
        }
        return this.activationPoint_;
    }

}