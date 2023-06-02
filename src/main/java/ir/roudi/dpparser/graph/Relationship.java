package ir.roudi.dpparser.graph;

public enum Relationship {
    ASSOCIATION(2),
    DELEGATION(3);

    private final int value;

    Relationship(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
