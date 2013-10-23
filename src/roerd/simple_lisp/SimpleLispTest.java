package roerd.simple_lisp;

import org.junit.Test;

import java.io.StringReader;

import static org.junit.Assert.assertEquals;

public class SimpleLispTest {
    SimpleLisp _lisp = new SimpleLisp();

    @Test
    public void testEval() throws Exception {
        LispObject expr =
                new LispList(
                        _lisp.getOperator("+"),
                        new LispList(
                                _lisp.getOperator("-"),
                                new LispNumber(6)),
                        new LispList(
                                _lisp.getOperator("+"),
                                new LispNumber(3),
                                new LispNumber(2),
                                new LispNumber(1)),
                        new LispList(
                                _lisp.getOperator("/"),
                                new LispNumber(10),
                                new LispNumber(5)),
                        new LispList(
                                _lisp.getOperator("*"),
                                new LispNumber(2),
                                new LispNumber(3),
                                new LispNumber(4)));

        assertEquals(26.0, expr.eval().getValue());
    }

    @Test
    public void testReadEval() throws Exception {
        String s = "( + (-6) (+ 3 2 1) (/ 10 5) (* 2 3 4))";
        LispObject expr = _lisp.read(new StringReader(s));

        assertEquals(26.0, expr.eval().getValue());
    }
}
