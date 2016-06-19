package dk.catnip.android.android_app.model;

public enum ButtonId {

    UNDEFINED(-1),
    A(0),
    B(1),
    C(2),
    D(3);

    int id;

    ButtonId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    //TODO refac: make more dynamic
    public static ButtonId fromString(String s) {
        switch (s.toUpperCase()) {
            case "A":
                return A;
            case "B":
                return B;
            case "C":
                return C;
            case "D":
                return D;
            default:
                return UNDEFINED;
        }
    }
}
