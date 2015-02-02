/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.rest;

public class RestSynchronizerException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    
    public RestSynchronizerException (Throwable e) {
        super(e);
    }
    
    public RestSynchronizerException (String message, Throwable e) {
        super(message, e);
    }

    
}
