/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.jms;

public class MigrationContextIsNullException extends Exception {
    
    private static final long serialVersionUID = 1L;

    public MigrationContextIsNullException () {
        super();
    }
    
    public MigrationContextIsNullException (Throwable t) {
        super(t);
    }
    
    public MigrationContextIsNullException (String message) {
        super(message);
    }
    
    public MigrationContextIsNullException (Throwable t, String message) {
        super(message, t);
    }

}
