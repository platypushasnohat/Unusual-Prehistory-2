package com.barlinc.unusual_prehistory.entity.mob.update_6.arthropleura;

public enum ArthropleuraPartIndex {

    HEAD(0.0F),
    BODY(2.25F),
    TAIL(2.25F);

    private final float backOffset;

    ArthropleuraPartIndex(float partOffset){
        this.backOffset = partOffset;
    }

    public static ArthropleuraPartIndex fromOrdinal(int i) {
        return switch (i) {
            case 0 -> HEAD;
            case 3 -> TAIL;
            default -> BODY;
        };
    }

    public static ArthropleuraPartIndex sizeAt(int index) {
        return switch (index) {
            case 0 -> HEAD;
            case 3 -> TAIL;
            default -> BODY;
        };
    }

    public float getBackOffset() {
        return backOffset;
    }
}