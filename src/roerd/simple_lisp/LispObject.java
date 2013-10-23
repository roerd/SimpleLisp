package roerd.simple_lisp;

interface LispObject {
    /**
     * @return the value of this Lisp object as a Java object, or a description is there's no equivalent Java type.
     */
    Object getValue();

    /**
     * @return the Lisp object that this Lisp object evaluates to.
     */
    LispObject eval();

    /**
     * Invoke this Lisp object as an operator with the supplied arguements.
     *
     * @param args the arguments of the operation.
     * @return the result value of the operation.
     */
    LispObject apply(LispList args);
}
