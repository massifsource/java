/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.test.common;

import com.massifsource.sample.damp.ms.rest.RestService;

public class RunnableDataCreator implements Runnable {
    //private static final Logger log = LoggerFactory.getLogger(RunnableDataCreator.class);
    private int iterations;
    private int sleepDurationInMillis;
    private RestService service;
    private static final int STARTING_ID = 50000;
    
    public RunnableDataCreator(int iterations, int sleepDurationInMillis, RestService service) {
        this.iterations = iterations;
        this.service = service;
        this.sleepDurationInMillis = sleepDurationInMillis;
    }

    public void run() {
        for (int i = 0; i < iterations; i++) {
            try {
                TestDataProvider.populateProductState(1, service, STARTING_ID + i);
                Thread.sleep(sleepDurationInMillis);
            }
            catch (Exception e) {
               throw new RunnableDataCreatorException(e);
               
            }
        }

    }

}
