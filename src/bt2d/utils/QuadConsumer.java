package bt2d.utils;

import java.util.function.Consumer;

/**
 * Represents an operation that accepts four input arguments and returns no
 * result. This is the four-arity specialization of {@link Consumer}.
 *
 * @param <T1> the type of the first argument to the operation
 * @param <T2> the type of the second argument to the operation
 * @param <T3> the type of the third argument to the operation
 * @param <T4> the type of the fourth argument to the operation
 *
 * @author Lukas Hartwig
 * @since 02.11.2021
 */
@FunctionalInterface
public interface QuadConsumer<T1, T2, T3, T4>
{
    /**
     * Performs this operation on the given arguments.
     *
     * @param t1 the first input argument
     * @param t2 the second input argument
     * @param t3 the third input argument
     * @param t4 the fourth input argument
     *
     * @author Lukas Hartwig
     * @since 02.11.2021
     */
    public void accept(T1 t1, T2 t2, T3 t3, T4 t4);
}