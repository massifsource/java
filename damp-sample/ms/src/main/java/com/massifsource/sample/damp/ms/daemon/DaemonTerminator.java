/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.daemon;

import java.io.IOException;

import com.massifsource.sample.damp.ms.cli.support.CommandSupportService;
import com.massifsource.sample.damp.ms.rest.RestSynchronizerException;

public class DaemonTerminator implements Runnable {

    private CommandSupportService service;

    public DaemonTerminator(CommandSupportService service) {
        this.service = service;
    }

    public void run() {
        try {
            service.removeAllListeners();
        }
        catch (IOException e) {
            throw new RestSynchronizerException(e);
        }

    }

}
