package feature.event;

import feature.Feature;

public class FeatureEnabledEvent extends FeatureEvent {

    /**
     * Generic feature event
     *
     * @param feature feature event is running for
     */
    public FeatureEnabledEvent(Feature feature) {
        super(feature);
    }
}
