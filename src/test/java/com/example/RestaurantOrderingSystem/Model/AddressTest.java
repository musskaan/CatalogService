package com.example.RestaurantOrderingSystem.Model;

import org.apache.logging.log4j.util.Strings;
import org.junit.jupiter.api.Test;

import static com.example.RestaurantOrderingSystem.Constants.Constants.*;
import static org.junit.jupiter.api.Assertions.*;

class AddressTest {

    private static final String VALID_ADDRESS_CREATION = "valid address creation";

    @Test
    public void testInitializeAddressWithEmptyStreet_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(Strings.EMPTY, VALID_CITY, VALID_STATE, VALID_ZIP_CDE));
    }

    @Test
    public void testInitializeAddressWithEmptyCity_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(VALID_STREET, Strings.EMPTY, VALID_STATE, VALID_ZIP_CDE));
    }

    @Test
    public void testInitializeAddressWithEmptyState_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(VALID_STREET, VALID_CITY, Strings.EMPTY, VALID_ZIP_CDE));
    }

    @Test
    public void testInitializeAddressWithEmptyZipCode_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Address(VALID_STREET, VALID_CITY, VALID_STATE, Strings.EMPTY));
    }

    @Test
    public void testInitializeAddress_success() {
        Address address = new Address(VALID_STREET, VALID_CITY, VALID_STATE, VALID_ZIP_CDE);

        assertAll(VALID_ADDRESS_CREATION,
                () -> assertEquals(VALID_STREET, address.getStreet()),
                () -> assertEquals(VALID_CITY, address.getCity()),
                () -> assertEquals(VALID_STATE, address.getState()),
                () -> assertEquals(VALID_ZIP_CDE, address.getZipCode()));
    }
}