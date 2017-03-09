package org.xiem.com.hibernate.object;

import gnu.trove.map.TShortObjectMap;
import gnu.trove.map.hash.TShortObjectHashMap;

public enum AuditStatus {

    NOT_START((short) 0),
    IN_PROGRESS((short) 1),
    SUCCESS((short) 2),
    FAIL((short) 3);

    public final short code;

    AuditStatus(short code) {
        this.code = code;
    }

    private static final TShortObjectMap<AuditStatus> CODE_MAP;

    static {
        CODE_MAP = new TShortObjectHashMap<AuditStatus>();
        for (AuditStatus type : AuditStatus.values()) {
            if (CODE_MAP.get(type.code) != null) {
                throw new IllegalArgumentException("Duplication decteded for "
                        + type.code);
            }
            CODE_MAP.put(type.code, type);
        }
    }

    public static AuditStatus getStatusFromCode(short code) {
        return CODE_MAP.get(code);
    }
}
