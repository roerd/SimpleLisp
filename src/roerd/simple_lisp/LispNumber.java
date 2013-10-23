package roerd.simple_lisp;

class LispNumber implements LispObject {
    private final double _value;

    public LispNumber(double value) {
        _value = value;
    }

    public Object getValue() {
        return getDoubleValue();
    }

    public double getDoubleValue() {
        return _value;
    }

    public LispObject eval() {
        return this;
    }

    public LispObject apply(LispList args) {
        throw new UnsupportedOperationException("Numbers cannot be applied.");
    }
}
