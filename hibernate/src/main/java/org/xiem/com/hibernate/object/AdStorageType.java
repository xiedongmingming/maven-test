package org.xiem.com.hibernate.object;

import gnu.trove.map.hash.TShortObjectHashMap;

public enum AdStorageType {
    URL((short) 0),
    FILE((short) 1),
    DB((short) 2),
    SWIFT((short) 3),
    ;

    public short code;

    AdStorageType(short code) {
        this.code = code;
    }

	private static final TShortObjectHashMap<AdStorageType> CODE_MAP;

    static {
		CODE_MAP = new TShortObjectHashMap<>();
        for (AdStorageType type : AdStorageType.values()) {
            if (CODE_MAP.get(type.code) != null) {
                throw new IllegalArgumentException("Duplication decteded for "
                        + type.code);
            }
            CODE_MAP.put(type.code, type);
        }
    }

    public static AdStorageType fromDbCode(short code) {
        return CODE_MAP.get(code);
    }
}
