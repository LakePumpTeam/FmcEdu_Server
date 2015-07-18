import com.fmc.edu.model.app.DeviceType;
import com.fmc.edu.util.BeanUtils;
import com.fmc.edu.util.RepositoryUtils;

/**
 * Created by Yu on 7/18/2015.
 */
public class Ognl {

    /**
     * 对像为空返回True
     *
     * @param o 对像值
     * @return True or False
     * @throws IllegalArgumentException
     */
    public static boolean isEmpty(Object o) throws IllegalArgumentException {
        return BeanUtils.isEmpty(o);
    }

    /**
     * 对像不为空返回True
     *
     * @param o 对像值
     * @return True or False
     */
    public static boolean isNotEmpty(Object o) {
        return !isEmpty(o);
    }

    /**
     * Long对像不为空值返回True
     *
     * @param o Long对像值
     * @return True or False
     */
    public static boolean isNotEmpty(Long o) {
        return !isEmpty(o);
    }

    /**
     * Number 对像为空值返回True
     *
     * @param o Number对像值
     * @return True or False
     */
    public static boolean isNumber(Object o) {
        return BeanUtils.isNumber(o);
    }

    public static boolean id(Object id) {
        if (id == null) {
            return false;
        }
        if (id instanceof String) {
            return RepositoryUtils.idIsValid((String) id);
        }
        if (id instanceof Integer) {
            return RepositoryUtils.idIsValid((Integer) id);
        }
        return false;
    }

    public static boolean deviceType(Integer pDeviceType) {
        return DeviceType.isValidDeviceType(pDeviceType);
    }

    public static boolean isPositiveInteger(Object pNumber) {
        if (!isNumber(pNumber)) {
            return false;
        }

        if (pNumber instanceof String) {
            if (((String) pNumber).contains(".")) {
                return false;
            }
            return Integer.valueOf((String) pNumber) > 0;
        }
        if (pNumber instanceof Integer) {
            return (Integer) pNumber > 0;
        }
        return false;
    }
}
