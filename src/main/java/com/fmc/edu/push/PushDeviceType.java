package com.fmc.edu.push;

/**
 * Created by YW on 2015/5/3.
 * <p>
 * The baidu cloud push device type class
 */
public class PushDeviceType {
	public static final int WEB = 1;
	public static final int PC = 2;
	public static final int ANDROID = 3;
	public static final int IOS = 4;
	public static final int WINDOWS_PHONE = 5;

	public static String toString(final int pDevice) {

		switch (pDevice) {
			case PushDeviceType.WEB: {
				return "WEB";
			}
			case PushDeviceType.PC: {
				return "PC";
			}
			case PushDeviceType.ANDROID: {
				return "ANDROID";
			}
			case PushDeviceType.IOS: {
				return "IOS";
			}
			case PushDeviceType.WINDOWS_PHONE: {
				return "WINDOWS_PHONE";
			}
			default: {
				return String.valueOf(pDevice);
			}
		}
	}
}
