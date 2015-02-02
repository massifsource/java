/**
 * MassifSource
 * @year 2015
 * @author nsarychev
 */
package com.massifsource.sample.damp.ms.cli.support;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.shell.plugin.support.DefaultPromptProvider;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class PromptProvider extends DefaultPromptProvider {
    
    private static final String DEFAULT_PROMPT_TEXT = "ms-cli";
    private String promptText = DEFAULT_PROMPT_TEXT;
    
    @Override
    public String getPrompt() {
            return getPromptText() + ">";
    }

    public String getProviderName() {
            return "MsMsPromptProvider";
    }

    public String getPromptText() {
        return promptText;
    }
    
    public void setDefaultPromptText() {
        setPromptText(DEFAULT_PROMPT_TEXT);
    }

    public void setPromptText(String promptText) {
        this.promptText = promptText;
    }

}
