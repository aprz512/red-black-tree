package com.aprz;

import java.lang.IllegalArgumentException;
import java.lang.IllegalStateException;

/**
 * 参数等检测
 */
public class Check {

    public static void checkNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException(object + " should not be null");
        }
    }

    public static void checkNull(Object object) {
        if (object != null) {
            throw new IllegalArgumentException(object + " should be null");
        }
    }

    public static void checkEquals(Object obj1, Object obj2) {
        if (obj1 != obj2) {
            throw new IllegalArgumentException(obj1 + " should be equals to " + obj2);
        }
    }

    public static void checkNotEquals(Object obj1, Object obj2) {
        if (obj1 == obj2) {
            throw new IllegalArgumentException(obj1 + " should not be equals to " + obj2);
        }
    }

    public static void shouldNotReachHere() {
        shouldNotReachHere("");
    }

    public static void shouldNotReachHere(String msg) {
        throw new IllegalStateException("code should not reach here, " + msg);
    }

    public static void checkLessEquals(int a, int b) {
        if (a > b) {
            throw new IllegalArgumentException(a + " > " + b);
        }
    }

    public static void checkLess(int a, int b) {
        if (a >= b) {
            throw new IllegalArgumentException(a + " >= " + b);
        }
    }

}
