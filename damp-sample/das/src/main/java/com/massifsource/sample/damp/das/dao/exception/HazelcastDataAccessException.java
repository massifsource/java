/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.das.dao.exception;

import org.springframework.dao.DataAccessException;

/**
 * This is a wrapper class for the Spring Framework data access exception.
 *
 * @author toconnell
 */
public class HazelcastDataAccessException extends DataAccessException {

   /**
    * Track class changes.
    */
   private static final long serialVersionUID = -8387234438627388595L;

   /**
    * Constructor.
    *
    * @param msg the message to be displayed with this exception.
    */
   public HazelcastDataAccessException(String msg) {
      super(msg);
   }

   /**
    * Constructor.
    *
    * @param msg the message to be displayed with this exception.
    * @param ex the nested exception underlying this.
    */
   public HazelcastDataAccessException(String msg, Throwable ex) {
      super(msg, ex);
   }
}
