package org.examples.ratelimit;

import io.github.bucket4j.Bandwidth;

import java.time.Duration;

public enum PricingPlan {
    FREE {
        Bandwidth getLimit() {
            return Bandwidth.builder().capacity(20).refillIntervally(20, Duration.ofHours(1)).build();
        }
    },
    BASIC {
        Bandwidth getLimit() {
            return Bandwidth.builder().capacity(40).refillIntervally(40, Duration.ofHours(1)).build();
        }
    },
    PROFESSIONAL {
        Bandwidth getLimit() {
            return Bandwidth.builder().capacity(100).refillIntervally(100, Duration.ofHours(1)).build();
        }
    }
}
