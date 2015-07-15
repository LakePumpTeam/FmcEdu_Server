package com.fmc.edu.model.app;

/**
 * Created by YW on 2015/5/3.
 * <p/>
 * Device type class
 */
public class DeviceType {
    public static final int WEB = 1;
    public static final int PC = 2;
    public static final int ANDROID = 3;
    public static final int IOS = 4;
    public static final int WINDOWS_PHONE = 5;

    public static String toString(final int pDevice) {

        switch (pDevice) {
            case DeviceType.WEB: {
                return "WEB";
            }
            case DeviceType.PC: {
                return "PC";
            }
            case DeviceType.ANDROID: {
                return "ANDROID";
            }
            case DeviceType.IOS: {
                return "IOS";
            }
            case DeviceType.WINDOWS_PHONE: {
                return "WINDOWS_PHONE";
            }
            default: {
                return String.valueOf(pDevice);
            }
        }
    }

    public static boolean isValidDeviceType(int pDeviceType) {
        return pDeviceType == 1
                || pDeviceType == 2
                || pDeviceType == 3
                || pDeviceType == 4
                || pDeviceType == 5;
    }
}
