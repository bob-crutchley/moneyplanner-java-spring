package org.bob.moneyplanner.java.spring.template;

import org.apache.commons.lang.text.StrSubstitutor;
import org.bob.moneyplanner.java.spring.model.persistence.Account;
import org.springframework.core.env.Environment;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EmailTemplate {

    private final String templateFilePath;
    private final Environment environment;

    public EmailTemplate(String templateFilePath, org.springframework.core.env.Environment environment) {
        this.templateFilePath = templateFilePath;
        this.environment = environment;
    }

    public String getContentForAccount(Account account){
        Map<String, String> templateMap = account.asHashMap();
        String activationAddress = environment.getProperty("application.frontend-application.host") + ":"
                + environment.getProperty("application.frontend-application.port");
        templateMap.put("activationAddress", activationAddress);
        String content = "";
        try {
            List<String> lines = Files.readAllLines(Paths.get(templateFilePath), StandardCharsets.UTF_8);
            content = lines.stream().collect(Collectors.joining(System.lineSeparator()));
            StrSubstitutor strSubstitutor = new StrSubstitutor(templateMap);
            content = strSubstitutor.replace(content);
        } catch(IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
