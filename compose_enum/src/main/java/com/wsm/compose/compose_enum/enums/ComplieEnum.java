package com.wsm.compose.compose_enum.enums;

/**
 * @value: ComplieEnum
 * @Author: wangshimin
 * @Date: 2019/11/16  13:01
 * @Description:
 */
public class ComplieEnum {

    public static enum USER_LOCK {

        ON("0", "可用"),
        OFF("1", "不可用");

        private String code;
        private String value;

        USER_LOCK(String value, String code) {
            this.value = value;
            this.code = code;
        }

        // 普通方法
        public static String getvalue(String code) {
            for (USER_LOCK c : USER_LOCK.values()) {
                if (c.getcode() == code) {
                    return c.value;
                }
            }
            return null;
        }

        // get set 方法
        public String getvalue() {
            return value;
        }

        public void setvalue(String value) {
            this.value = value;
        }

        public String getcode() {
            return code;
        }

        public void setcode(String code) {
            this.code = code;
        }
    }
}
