package com.barlinc.unusual_prehistory.entity.utils;

public enum ArthropleuraPartIndex {
    HEAD(0F), BODY(0.5F), TAIL(0.4F);

    private final float backOffset;

    ArthropleuraPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static ArthropleuraPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> HEAD;
            case 2 -> TAIL;
            default -> BODY;
        };

    }

    public static ArthropleuraPartIndex sizeAt(int bodyindex) {
        return switch (bodyindex) {
            case 0 -> HEAD;
            case 7 -> TAIL;
            default -> BODY; // cases 2, 3, 4 and others
        };
    }

    public float getBackOffset() {
        return backOffset;
    }
}