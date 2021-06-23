package biss;

import common.ByteArrayClassLoader;
import common.Exceptions;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.regex.Pattern;

import static org.objectweb.asm.Opcodes.*;

public class Compiler {
    public static void main(String[] args) {
        DoubleUnaryOperator f = compiled("root(-x * x + 2 * 5 * 2 * 5) / 2 / 2 - 1 - 1");
        double y = f.applyAsDouble(6);
        System.out.println(y);
    }

    private static final Pattern LEXEMES = Pattern.compile("x|[0-9]+|[-+*/]|root|[^\\s]");

    private static final Map<String, Integer> INSTRUCTIONS = Map.of(
            "*", DMUL, "/", DDIV, "+", DADD, "-", DSUB);

    public static DoubleUnaryOperator compiled(String formula) {
        return new Parser<DoubleUnaryOperator>(LEXEMES.matcher(formula)) {
            MethodVisitor mv;

            @Override
            public DoubleUnaryOperator get() {
                // create class
                ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
                classWriter.visit(V1_8, ACC_PUBLIC, "biss/Compiler$Function123", null,
                        "java/lang/Object",
                        new String[]{"java/util/function/DoubleUnaryOperator"});

                // default constructor
                mv = classWriter.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
                mv.visitCode();
                mv.visitVarInsn(ALOAD, 0);
                mv.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V", false);
                mv.visitInsn(RETURN);
                mv.visitEnd();
                mv.visitMaxs(0, 0); // compute automatically

                // applyAsDouble
                mv = classWriter.visitMethod(ACC_PUBLIC, "applyAsDouble", "(D)D", null, null);
                mv.visitCode();
                subexpression(0);
                expect(END);
                mv.visitInsn(DRETURN);
                mv.visitEnd();
                mv.visitMaxs(0, 0); // compute automatically

                // load class
                classWriter.visitEnd();
                ByteArrayClassLoader classLoader = new ByteArrayClassLoader();
                Class<?> mathe = classLoader.defineClass("biss.Compiler$Function123", classWriter.toByteArray());

                // instantiate
                DoubleUnaryOperator function = null;
                try {
                    function = (DoubleUnaryOperator) mathe.getConstructor().newInstance();
                } catch (NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException ex) {
                    Exceptions.sneakyThrow(ex);
                }
                return function;
            }

            private void subexpression(int leftPrecedence) {
                primary();
                int rightPrecedence;
                // a + B * c    + < *
                // a * B + c    * > +
                while (leftPrecedence < (rightPrecedence = PRECEDENCE.getOrDefault(lexeme, -1))) {
                    String operator = accept();
                    subexpression(rightPrecedence);
                    mv.visitInsn(INSTRUCTIONS.get(operator));
                }
            }

            private void primary() {
                switch (lexeme.charAt(0)) {
                    case '0':
                    case '1':
                    case '2':
                    case '3':
                    case '4':
                    case '5':
                    case '6':
                    case '7':
                    case '8':
                    case '9':
                        mv.visitLdcInsn(Double.parseDouble(accept()));
                        return;

                    case 'x':
                        advance();
                        mv.visitVarInsn(DLOAD, 1);
                        return;

                    case '-':
                        advance();
                        subexpression(14);
                        mv.visitInsn(DNEG);
                        return;

                    case '(':
                        advance();
                        subexpression(0);
                        expect(")");
                        return;

                    case 'r':
                        advance();
                        expect("(");
                        subexpression(0);
                        expect(")");
                        mv.visitMethodInsn(INVOKESTATIC, "java/lang/Math", "sqrt", "(D)D", false);
                        return;

                    default:
                        throw new IllegalArgumentException(lexeme);
                }
            }
        }.get();
    }
}
