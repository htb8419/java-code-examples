package org.examples;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class HtmlSanitizerApplication {

    public static void main(String[] args) {
        String untrustedHTML = "<body><a href='#'>test</a><script>alert('hi')</script></body>";
        PolicyFactory policy = Sanitizers.FORMATTING
                .and(Sanitizers.BLOCKS)
                .and(Sanitizers.LINKS);
        String safeHTML = policy.sanitize(untrustedHTML);
        System.out.println(safeHTML);
    }
}
