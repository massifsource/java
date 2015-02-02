/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli.support;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultHistoryFileNameProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HistoryFileNameProvider extends DefaultHistoryFileNameProvider {
    private static final String BASE_DIR = System.getProperty("basedir");

    public String getHistoryFileName() {
        return BASE_DIR + "/logs/ms-cmd-history.log";
    }

    public String getProviderName() {
        return "HistoryFileNameProvider";
    }
}
