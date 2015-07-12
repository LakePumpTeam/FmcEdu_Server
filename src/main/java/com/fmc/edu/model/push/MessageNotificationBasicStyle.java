package com.fmc.edu.model.push;

/**
 * Created by Yu on 7/13/2015.
 */
public class MessageNotificationBasicStyle {
    public static int BEL_NOT_ERASIBLE = 0X04;
    public static int VIBRA_NOT_ERASIBLE = 0x02;
    public static int BEL_VIBRA_NOT_ERASIBLE = 0x04 | 0x02;
    public static int ERASIBLE = 0X01;
    public static int BEL_ERASIBLE = 0X04 | 0X01;
    public static int VIBRA_ERASIBLE = 0x02 | 0X01;
    public static int BEL_VIBRA_ERASIBLE = 0x04 | 0x02 | 0X01;
}
