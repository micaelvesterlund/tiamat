//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.11.05 at 07:41:01 PM CET 
//


package org.rutebanken.tiamat.model;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for VehicleLoadingEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="VehicleLoadingEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}NMTOKEN">
 *     &lt;enumeration value="none"/>
 *     &lt;enumeration value="loading"/>
 *     &lt;enumeration value="unloading"/>
 *     &lt;enumeration value="additionalLoading"/>
 *     &lt;enumeration value="additionaUnloading"/>
 *     &lt;enumeration value="unknown"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "VehicleLoadingEnumeration")
@XmlEnum
public enum VehicleLoadingEnumeration {

    @XmlEnumValue("none")
    NONE("none"),
    @XmlEnumValue("loading")
    LOADING("loading"),
    @XmlEnumValue("unloading")
    UNLOADING("unloading"),
    @XmlEnumValue("additionalLoading")
    ADDITIONAL_LOADING("additionalLoading"),
    @XmlEnumValue("additionaUnloading")
    ADDITIONA_UNLOADING("additionaUnloading"),
    @XmlEnumValue("unknown")
    UNKNOWN("unknown");
    private final String value;

    VehicleLoadingEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static VehicleLoadingEnumeration fromValue(String v) {
        for (VehicleLoadingEnumeration c: VehicleLoadingEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}