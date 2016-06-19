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

    public static ButtonId fromString(String s) {
        if (s == null) return null;

        String name = s.toUpperCase();
        for (ButtonId buttonId : ButtonId.values()) {
            if (buttonId.name().equals(name)) {
                return buttonId;
            }
        }

        return ButtonId.UNDEFINED;
    }

}
