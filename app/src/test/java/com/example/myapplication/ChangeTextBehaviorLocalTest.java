package com.example.myapplication;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public final class ChangeTextBehaviorLocalTest {


    @Test
    public void isTheFormatTypeStringTrue() {
        assertTrue(ValidationUtility.isString("IAmAString"));
    }

    @Test
    public void isTheFormatTypeStringTrue1() {
        assertTrue(ValidationUtility.isString("OneTwoThree"));
    }

    @Test
    public void isTheFormatTypeStringTrue2() {
        assertTrue(ValidationUtility.isString("GreatStrings"));
    }

    @Test
    public void isTheFormatTypeStringFalse() {
        assertFalse(ValidationUtility.isString(213 + " " + " @"));
    }

    @Test
    public void isTheFormatTypeStringFalse1() {
        assertFalse(ValidationUtility.isString(213 + " " + " @"));
    }

    @Test
    public void isTheFormatTypeStringFalse2() {
        assertFalse(ValidationUtility.isString("$$$$$$$$$$$$$$"));
    }

    @Test
    public void isTheRightTypeOfWeatherTrue() {
        assertFalse(ValidationUtility.isTypeRight("Rain"));
    }

    @Test
    public void isTheRightTypeOfWeatherTrue1() {
        assertFalse(ValidationUtility.isTypeRight("Clouds"));
    }

    @Test
    public void isTheRightTypeOfWeatherFalse() {
        assertFalse(ValidationUtility.isTypeRight(""));
    }

    @Test
    public void isTheRightTypeOfWeatherFalse1() {
        assertFalse(ValidationUtility.isTypeRight("cloudssss"));
    }

    @Test
    public void theTimeIsNotCorrectFalse() {
        assertFalse(ValidationUtility.isCorrectTime("asd:asd"));
    }

    @Test
    public void theTimeIsNotCorrectFalseTwice() {
        assertFalse(ValidationUtility.isCorrectTime("31:98"));
    }

    @Test
    public void theTimeIsNotCorrectTrue() {
        assertFalse(ValidationUtility.isCorrectTime("16:59"));
    }

    @Test
    public void theTimeIsNotCorrectTrue1() {
        assertFalse(ValidationUtility.isCorrectTime("04:03"));
    }

    @Test
    public void theTimeIsNotCorrectTrue2() {
        assertFalse(ValidationUtility.isCorrectTime("12:32"));
    }


    @Test
    public void weatherTempCorrectTrue() {
        assertFalse(ValidationUtility.isTempCorrect(1));
    }

    @Test
    public void weatherTempCorrectTrue1() {
        assertFalse(ValidationUtility.isTempCorrect(-60));
    }

    @Test
    public void weatherTempCorrectTrue2() {
        assertFalse(ValidationUtility.isTempCorrect(99));
    }


    @Test
    public void weatherTempCorrectFalse() {
        assertFalse(ValidationUtility.isTempCorrect(9000));
    }

    @Test
    public void weatherTempCorrectFalse1() {
        assertFalse(ValidationUtility.isTempCorrect(123));
    }

    @Test
    public void weatherTempCorrectFalse2() {
        assertFalse(ValidationUtility.isTempCorrect(-101));
    }
}