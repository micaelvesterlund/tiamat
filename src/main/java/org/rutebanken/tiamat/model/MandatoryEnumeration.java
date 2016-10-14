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
 * <p>Java class for MandatoryEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="MandatoryEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}normalizedString">
 *     &lt;enumeration value="required"/>
 *     &lt;enumeration value="optional"/>
 *     &lt;enumeration value="notAllowed"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "MandatoryEnumeration")
@XmlEnum
public enum MandatoryEnumeration {


    /**
     * Include elements that meet selection criteria (e.g. validity condition).
     * 
     */
    @XmlEnumValue("required")
    REQUIRED("required"),

    /**
     * Include elements that are referenced by primary element. E.g. TYPES OF VALUE, OPERATOR etc.
     * 
     */
    @XmlEnumValue("optional")
    OPTIONAL("optional"),

    /**
     * Include all elements.
     * 
     */
    @XmlEnumValue("notAllowed")
    NOT_ALLOWED("notAllowed");
    private final String value;

    MandatoryEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MandatoryEnumeration fromValue(String v) {
        for (MandatoryEnumeration c: MandatoryEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}