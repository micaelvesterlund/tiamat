//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package no.rutebanken.tiamat.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for ParkingReservationEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParkingReservationEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="reservationRequired"/>
 *     &lt;enumeration value="reservationAllowed"/>
 *     &lt;enumeration value="noReservations"/>
 *     &lt;enumeration value="registrationRequired"/>
 *     &lt;enumeration value="other"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParkingReservationEnumeration")
@XmlEnum
public enum ParkingReservationEnumeration {

    @XmlEnumValue("reservationRequired")
    RESERVATION_REQUIRED("reservationRequired"),
    @XmlEnumValue("reservationAllowed")
    RESERVATION_ALLOWED("reservationAllowed"),
    @XmlEnumValue("noReservations")
    NO_RESERVATIONS("noReservations"),
    @XmlEnumValue("registrationRequired")
    REGISTRATION_REQUIRED("registrationRequired"),
    @XmlEnumValue("other")
    OTHER("other");
    private final String value;

    ParkingReservationEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParkingReservationEnumeration fromValue(String v) {
        for (ParkingReservationEnumeration c: ParkingReservationEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}