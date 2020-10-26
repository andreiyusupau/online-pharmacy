package com.vironit.onlinepharmacy.dao.util;

import com.vironit.onlinepharmacy.dao.collection.util.BasicIdGenerator;
import com.vironit.onlinepharmacy.dao.collection.util.IdGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BasicIdGeneratorTest {

    private IdGenerator idGenerator;

    @BeforeEach
    void init() {
        idGenerator = new BasicIdGenerator();
    }

    @Test
    void getNextIdShouldReturnOneOnFirstLaunch() {
        long id = idGenerator.getNextId();

        long expected = 1;
        Assertions.assertEquals(expected, id);
    }

    @Test
    void getNextIdShouldReturnOneTwoThree() {
        long[] idList = new long[3];

        idList[0] = idGenerator.getNextId();
        idList[1] = idGenerator.getNextId();
        idList[2] = idGenerator.getNextId();

        long[] expected = new long[]{1, 2, 3};
        Assertions.assertArrayEquals(expected, idList);
    }
}
