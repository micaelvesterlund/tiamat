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
 * <p>Java class for ParkingVehicleEnumeration.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p>
 * <pre>
 * &lt;simpleType name="ParkingVehicleEnumeration">
 *   &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *     &lt;enumeration value="pedalCycle"/>
 *     &lt;enumeration value="moped"/>
 *     &lt;enumeration value="motorcycle"/>
 *     &lt;enumeration value="motorcycleWithSidecar"/>
 *     &lt;enumeration value="motorScooter"/>
 *     &lt;enumeration value="twoWheeledVehicle"/>
 *     &lt;enumeration value="threeWheeledVehicle"/>
 *     &lt;enumeration value="car"/>
 *     &lt;enumeration value="smallCar"/>
 *     &lt;enumeration value="passengerCar"/>
 *     &lt;enumeration value="largeCar"/>
 *     &lt;enumeration value="fourWheelDrive"/>
 *     &lt;enumeration value="taxi"/>
 *     &lt;enumeration value="camperCar"/>
 *     &lt;enumeration value="carWithTrailer"/>
 *     &lt;enumeration value="carWithCaravan"/>
 *     &lt;enumeration value="minibus"/>
 *     &lt;enumeration value="bus"/>
 *     &lt;enumeration value="van"/>
 *     &lt;enumeration value="largeVan"/>
 *     &lt;enumeration value="highSidedVehicle"/>
 *     &lt;enumeration value="highSidedVehicle"/>
 *     &lt;enumeration value="lightGoodsVehicle"/>
 *     &lt;enumeration value="heavyGoodsVehicle"/>
 *     &lt;enumeration value="truck"/>
 *     &lt;enumeration value="agriculturalVehicle"/>
 *     &lt;enumeration value="tanker"/>
 *     &lt;enumeration value="truck"/>
 *     &lt;enumeration value="tram"/>
 *     &lt;enumeration value="articulatedVehicle"/>
 *     &lt;enumeration value="vehicleWithTrailer"/>
 *     &lt;enumeration value="lightGoodsVehicleWithTrailer"/>
 *     &lt;enumeration value="heavyGoodsVehicleWithTrailer"/>
 *     &lt;enumeration value="undefined"/>
 *     &lt;enumeration value="other"/>
 *     &lt;enumeration value="allPassengerVehicles"/>
 *     &lt;enumeration value="all"/>
 *   &lt;/restriction>
 * &lt;/simpleType>
 * </pre>
 * 
 */
@XmlType(name = "ParkingVehicleEnumeration")
@XmlEnum
public enum ParkingVehicleEnumeration {

    @XmlEnumValue("pedalCycle")
    PEDAL_CYCLE("pedalCycle"),
    @XmlEnumValue("moped")
    MOPED("moped"),
    @XmlEnumValue("motorcycle")
    MOTORCYCLE("motorcycle"),
    @XmlEnumValue("motorcycleWithSidecar")
    MOTORCYCLE_WITH_SIDECAR("motorcycleWithSidecar"),
    @XmlEnumValue("motorScooter")
    MOTOR_SCOOTER("motorScooter"),
    @XmlEnumValue("twoWheeledVehicle")
    TWO_WHEELED_VEHICLE("twoWheeledVehicle"),
    @XmlEnumValue("threeWheeledVehicle")
    THREE_WHEELED_VEHICLE("threeWheeledVehicle"),
    @XmlEnumValue("car")
    CAR("car"),
    @XmlEnumValue("smallCar")
    SMALL_CAR("smallCar"),
    @XmlEnumValue("passengerCar")
    PASSENGER_CAR("passengerCar"),
    @XmlEnumValue("largeCar")
    LARGE_CAR("largeCar"),
    @XmlEnumValue("fourWheelDrive")
    FOUR_WHEEL_DRIVE("fourWheelDrive"),
    @XmlEnumValue("taxi")
    TAXI("taxi"),
    @XmlEnumValue("camperCar")
    CAMPER_CAR("camperCar"),
    @XmlEnumValue("carWithTrailer")
    CAR_WITH_TRAILER("carWithTrailer"),
    @XmlEnumValue("carWithCaravan")
    CAR_WITH_CARAVAN("carWithCaravan"),
    @XmlEnumValue("minibus")
    MINIBUS("minibus"),
    @XmlEnumValue("bus")
    BUS("bus"),
    @XmlEnumValue("van")
    VAN("van"),
    @XmlEnumValue("largeVan")
    LARGE_VAN("largeVan"),
    @XmlEnumValue("highSidedVehicle")
    HIGH_SIDED_VEHICLE("highSidedVehicle"),
    @XmlEnumValue("lightGoodsVehicle")
    LIGHT_GOODS_VEHICLE("lightGoodsVehicle"),
    @XmlEnumValue("heavyGoodsVehicle")
    HEAVY_GOODS_VEHICLE("heavyGoodsVehicle"),
    @XmlEnumValue("truck")
    TRUCK("truck"),
    @XmlEnumValue("agriculturalVehicle")
    AGRICULTURAL_VEHICLE("agriculturalVehicle"),
    @XmlEnumValue("tanker")
    TANKER("tanker"),
    @XmlEnumValue("tram")
    TRAM("tram"),
    @XmlEnumValue("articulatedVehicle")
    ARTICULATED_VEHICLE("articulatedVehicle"),
    @XmlEnumValue("vehicleWithTrailer")
    VEHICLE_WITH_TRAILER("vehicleWithTrailer"),
    @XmlEnumValue("lightGoodsVehicleWithTrailer")
    LIGHT_GOODS_VEHICLE_WITH_TRAILER("lightGoodsVehicleWithTrailer"),
    @XmlEnumValue("heavyGoodsVehicleWithTrailer")
    HEAVY_GOODS_VEHICLE_WITH_TRAILER("heavyGoodsVehicleWithTrailer"),
    @XmlEnumValue("undefined")
    UNDEFINED("undefined"),
    @XmlEnumValue("other")
    OTHER("other"),
    @XmlEnumValue("allPassengerVehicles")
    ALL_PASSENGER_VEHICLES("allPassengerVehicles"),
    @XmlEnumValue("all")
    ALL("all");
    private final String value;

    ParkingVehicleEnumeration(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ParkingVehicleEnumeration fromValue(String v) {
        for (ParkingVehicleEnumeration c: ParkingVehicleEnumeration.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }

}