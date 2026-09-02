package com.chachamaru.me.streaventhandcuffs.cuffs;

public enum CuffType {

    FRONT("front"),
    BACK("back");

    private final String type;

    CuffType(String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }

    public static CuffType getTypeByName(String type) {
        if (type == null || type.isEmpty()) return FRONT;

        for (CuffType types : values()) {
            if (types.name().equalsIgnoreCase(type)) return types;
        }

        return FRONT;
    }

}
