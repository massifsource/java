/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli.support;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultBannerProvider;
import org.springframework.shell.support.util.OsUtils;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class BannerProvider extends DefaultBannerProvider {
    
    @Value("${build.version}")
    private String version;
    
    public String getBanner() {
        StringBuffer buf = new StringBuffer();
        buf.append("=======================================").append(OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *").append(OsUtils.LINE_SEPARATOR);
        buf.append("*         Migration Service           *").append(OsUtils.LINE_SEPARATOR);
        buf.append("*                                     *").append(OsUtils.LINE_SEPARATOR);
        buf.append("=======================================").append(OsUtils.LINE_SEPARATOR);
        buf.append("Version:").append(version);
        return buf.toString();
    }

    public String getWelcomeMessage() {
        return "Welcome to Migration Service";
    }

    public String getProviderName() {
        return "ms Banner";
    }
}
