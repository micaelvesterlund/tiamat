//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import java.math.BigInteger;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.Duration;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Type for an OPERATING DAY.
 * 
 * <p>Java class for OperatingDay_VersionStructure complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OperatingDay_VersionStructure">
 *   &lt;complexContent>
 *     &lt;extension base="{http://www.netex.org.uk/netex}DataManagedObjectStructure">
 *       &lt;sequence>
 *         &lt;group ref="{http://www.netex.org.uk/netex}OperatingDayGroup"/>
 *       &lt;/sequence>
 *     &lt;/extension>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OperatingDay_VersionStructure", propOrder = {
    "calendarDate",
    "serviceCalendarRef",
    "name",
    "shortName",
    "dayNumber",
    "privateCode",
    "earliestTime",
    "dayLength"
})
@XmlSeeAlso({
    OperatingDay.class
})
public class OperatingDay_VersionStructure
    extends DataManagedObjectStructure
{

    @XmlElement(name = "CalendarDate", required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar calendarDate;
    @XmlElement(name = "ServiceCalendarRef")
    protected ServiceCalendarRefStructure serviceCalendarRef;
    @XmlElement(name = "Name")
    protected MultilingualString name;
    @XmlElement(name = "ShortName")
    protected MultilingualString shortName;
    @XmlElement(name = "DayNumber")
    protected BigInteger dayNumber;
    @XmlElement(name = "PrivateCode")
    protected PrivateCodeStructure privateCode;
    @XmlElement(name = "EarliestTime")
    @XmlSchemaType(name = "time")
    protected XMLGregorianCalendar earliestTime;
    @XmlElement(name = "DayLength")
    protected Duration dayLength;

    /**
     * Gets the value of the calendarDate property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCalendarDate() {
        return calendarDate;
    }

    /**
     * Sets the value of the calendarDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCalendarDate(XMLGregorianCalendar value) {
        this.calendarDate = value;
    }

    /**
     * Reference to parent  SERVICE CALENDAR. If given by context, does not need to be given.
     * 
     * @return
     *     possible object is
     *     {@link ServiceCalendarRefStructure }
     *     
     */
    public ServiceCalendarRefStructure getServiceCalendarRef() {
        return serviceCalendarRef;
    }

    /**
     * Sets the value of the serviceCalendarRef property.
     * 
     * @param value
     *     allowed object is
     *     {@link ServiceCalendarRefStructure }
     *     
     */
    public void setServiceCalendarRef(ServiceCalendarRefStructure value) {
        this.serviceCalendarRef = value;
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
     * Gets the value of the dayNumber property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getDayNumber() {
        return dayNumber;
    }

    /**
     * Sets the value of the dayNumber property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setDayNumber(BigInteger value) {
        this.dayNumber = value;
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

    /**
     * Gets the value of the earliestTime property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getEarliestTime() {
        return earliestTime;
    }

    /**
     * Sets the value of the earliestTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setEarliestTime(XMLGregorianCalendar value) {
        this.earliestTime = value;
    }

    /**
     * Gets the value of the dayLength property.
     * 
     * @return
     *     possible object is
     *     {@link Duration }
     *     
     */
    public Duration getDayLength() {
        return dayLength;
    }

    /**
     * Sets the value of the dayLength property.
     * 
     * @param value
     *     allowed object is
     *     {@link Duration }
     *     
     */
    public void setDayLength(Duration value) {
        this.dayLength = value;
    }

}