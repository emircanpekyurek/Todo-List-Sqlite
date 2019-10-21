package com.list.todo.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

public class AesUtilsTest {

    private String decrypt = "bugun hava cok guzel!'^+%&/()=?:.çÜİĞŞPİĞPO:@€<>x|¨´@798456321+-*/,.";
    private String encrypt = "50E7098A840851710F9023B6E15700B9D3EB3DA65C8F3AC08E4B15C7700AB4E90A47988798C886415E83CD72C77422E05523CE79687DC314BA105B09B668A5871A4B2C9DAE857889125128A711A35470";

    @Test
    public void encrypt() {
        assertEquals(encrypt, AesUtils.encrypt(decrypt));
        assertNotEquals(encrypt, decrypt);
        assertNotEquals(encrypt, AesUtils.encrypt(encrypt));
        assertNotEquals(encrypt, AesUtils.encrypt(AesUtils.encrypt(encrypt)));
    }

    @Test
    public void decrypt() {
        assertEquals(decrypt, AesUtils.decrypt(encrypt));
        assertNotEquals(decrypt, encrypt);

        try {
            assertNotEquals(decrypt, AesUtils.decrypt(decrypt));
        } catch (RuntimeException e) {
            assertTrue(true);
        }

        try {
            assertNotEquals(decrypt, AesUtils.decrypt(AesUtils.decrypt(encrypt)));
        } catch (RuntimeException e) {
            assertTrue(true);
        }

    }
}