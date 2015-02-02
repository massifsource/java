/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.common;

public class RunnableDataCreatorException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public RunnableDataCreatorException(Throwable e) {
        super(e);
    }

    public RunnableDataCreatorException(String message, Throwable e) {
        super(message, e);
    }

}
