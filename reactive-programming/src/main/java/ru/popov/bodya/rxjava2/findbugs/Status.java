package ru.popov.bodya.rxjava2.findbugs;

class Status {
    private final boolean isEnabled;

    Status(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    boolean isEnabled() {
        return isEnabled;
    }

    @Override
    public String toString() {
        return "Status{" +
                "isEnabled=" + isEnabled +
                '}';
    }
}
