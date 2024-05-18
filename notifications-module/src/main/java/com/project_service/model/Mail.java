package com.project_service.model;

import lombok.Getter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Getter
public class Mail {
    private final String subject;
    private final String template;
    private final String[] recipientArray;
    private final Map<String, Object> templateVars = new HashMap<>();


    public Mail(String subject, String template, String[] recipientArray) {
        this.subject = subject;
        this.template = template;
        this.recipientArray = recipientArray;
    }

    public void addVariable(String key, Object value) {
        this.templateVars.put(key, value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mail mail = (Mail) o;
        return Objects.equals(subject, mail.subject)
                && Objects.equals(template, mail.template)
                && Arrays.equals(recipientArray, mail.recipientArray)
                && Objects.equals(templateVars, mail.templateVars);
    }

    @Override
    public int hashCode() {
        return Objects.hash(subject, template, Arrays.hashCode(recipientArray), templateVars);
    }

    @Override
    public String toString() {
        return "Mail{" +
                "subject=" + subject +
                ", template='" + template + '\'' +
                ", recipient='" + Arrays.toString(recipientArray) + '\'' +
                ", templateVars=" + templateVars +
                '}';
    }
}
