package dk.catnip.android.android_app.model;

public enum ButtonId {
    A(0),
    B(1),
    C(2),
    D(3),
    UNDEFINED(-1);

    int id;

    ButtonId(int id) {
        this.id = id;
    }

    public static ButtonId fromInt(int i) {
        for (ButtonId buttonId : ButtonId.values()) {
            if (buttonId.id == i) {
                return buttonId;
            }
        }
        return UNDEFINED;
    }

    public int getId() {
        return id;
    }
}
