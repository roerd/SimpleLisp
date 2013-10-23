package roerd.simple_lisp;

import java.io.*;
import java.util.*;

/**
 * This is an evaluator for simple arithmetic expressions in Lisp syntax.
 */
public class SimpleLisp {
    private final Map<String, LispOperator> _ops = new HashMap<>();

    public SimpleLisp() {
        _ops.put("+", new LispOperator("+") {
            public LispObject apply(LispList args) {
                double result = 0;
                for (LispObject arg : args.getElements()) {
                    result += numVal(arg);
                }
                return new LispNumber(result);
            }
        });

        _ops.put("-", new LispOperator("-") {
            public LispObject apply(LispList args) {
                List<LispObject> elements = args.getElements();
                if (elements.size() < 1) {
                    throw new IllegalArgumentException("At least 1 argument expected.");
                }
                double result = numVal(elements.get(0));
                if (elements.size() == 1) {
                    result = 0 - result;
                } else {
                    for (LispObject arg: elements.subList(1, elements.size())) {
                        result -= numVal(arg);
                    }
                }
                return new LispNumber(result);
            }
        });

        _ops.put("*", new LispOperator("*") {
            public LispObject apply(LispList args) {
                double result = 1;
                for (LispObject arg: args.getElements()) {
                    result *= numVal(arg);
                }
                return new LispNumber(result);
            }
        });

        _ops.put("/", new LispOperator("/") {
            public LispObject apply(LispList args) {
                List<LispObject> elements = args.getElements();
                if (elements.size() < 1) {
                    throw new IllegalArgumentException("At least 1 argument expected.");
                }
                double result = numVal(elements.get(0));
                if (elements.size() == 1) {
                    result = 1 / result;
                } else {
                    for (LispObject arg: elements.subList(1, elements.size())) {
                        result /= numVal(arg);
                    }
                }
                return new LispNumber(result);
            }
        });
    }

    private double numVal(LispObject o) {
        if (o instanceof LispNumber) {
            LispNumber n = (LispNumber) o;
            return n.getDoubleValue();
        } else {
            throw new IllegalArgumentException("Not a number: " + o.getValue());
        }
    }

    public LispOperator getOperator(String name) {
        LispOperator op = _ops.get(name);
        if (op == null) {
            throw new IllegalArgumentException("Unknown operator: " + name);
        } else {
            return op;
        }
    }

    class EndOfListException extends IOException {
        EndOfListException(int line) {
            super("Line " + line + ": Unsuspected end of list.");
        }
    }

    public LispObject read(Reader reader) throws IOException {
        StreamTokenizer tokenizer = new StreamTokenizer(reader);
        tokenizer.ordinaryChar('-');
        tokenizer.ordinaryChar('/');

        return read(tokenizer);
    }

    private LispObject read(StreamTokenizer tokenizer) throws IOException {
        switch (tokenizer.nextToken()) {
            case StreamTokenizer.TT_EOF:
                throw new IOException("Line " + tokenizer.lineno() + ": Unsuspected EOF.");
            case StreamTokenizer.TT_NUMBER:
                return new LispNumber(tokenizer.nval);
            case '(':
                List<LispObject> elements = new ArrayList<>();
                while (true) {
                    try {
                        elements.add(read(tokenizer));
                    } catch (EndOfListException e) {
                        return new LispList(elements);
                    }
                }
            case ')':
                throw new EndOfListException(tokenizer.lineno());
            case StreamTokenizer.TT_WORD:
                return getOperator(tokenizer.sval);
            default:
                return getOperator(Character.toString((char) tokenizer.ttype));
        }
    }

    /**
     * Run a simple REPL for the evaluator. Quit it by entering an empty line.
     */
    public static void main(String[] args) throws IOException {
        SimpleLisp lisp = new SimpleLisp();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("> ");
            String line = in.readLine();
            if (line.trim().isEmpty()) {
                break;
            }
            LispObject expr = lisp.read(new StringReader(line));
            LispObject result = expr.eval();
            System.out.println(result.getValue());
        }
    }
}
