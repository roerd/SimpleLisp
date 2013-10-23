package roerd.simple_lisp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class LispList implements LispObject {
    private final List<LispObject> _elements;

    public LispList(LispObject... elements) {
        this(Arrays.asList(elements));
    }

    public LispList(List<LispObject> elements) {
        _elements = elements;
    }

    public Object getValue() {
        return getElements();
    }

    public List<LispObject> getElements() {
        return _elements;
    }

    public LispObject eval() {
        if (_elements.size() == 0) {
            return this;
        }

        List<LispObject> evaluatedElements = new ArrayList<>();
        for (LispObject o : _elements) {
            evaluatedElements.add(o.eval());
        }

        LispObject op = evaluatedElements.get(0);
        List<LispObject> args = evaluatedElements.subList(1, evaluatedElements.size());
        return op.apply(new LispList(args));
    }

    public LispObject apply(LispList args) {
        throw new UnsupportedOperationException("Lists cannot be applied.");
    }
}
