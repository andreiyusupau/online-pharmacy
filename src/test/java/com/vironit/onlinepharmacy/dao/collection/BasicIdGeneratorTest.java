package com.vironit.onlinepharmacy.dao.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class BasicIdGeneratorTest {

    @Test
    void testGetNextIdShouldReturnOneOnFirstLaunch() {
        IdGenerator idGenerator = new BasicIdGenerator();

        long id = idGenerator.getNextId();

        long expected = 1;
        Assertions.assertEquals(expected, id);
    }

    @Test
    void testGetNextIdShouldReturnOneTwoThree() {
        IdGenerator idGenerator = new BasicIdGenerator();
        long[] idList = new long[3];

        idList[0] = idGenerator.getNextId();
        idList[1] = idGenerator.getNextId();
        idList[2] = idGenerator.getNextId();

        long[] expected = new long[]{1, 2, 3};
        Assertions.assertArrayEquals(expected, idList);
    }
}
