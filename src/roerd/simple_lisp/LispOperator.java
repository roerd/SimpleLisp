package roerd.simple_lisp;

abstract class LispOperator implements LispObject {
    protected String _name;

    protected LispOperator(String name) {
        _name = name;
    }

    public Object getValue() {
        return "<Operator " + _name + ">";
    }

    public LispObject eval() {
        return this;
    }
}
