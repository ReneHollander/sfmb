package feature.event;

import feature.Feature;

public class FeatureUnregisterEvent extends FeatureEvent {

    /**
     * Generic feature event
     *
     * @param feature feature event is running for
     */
    public FeatureUnregisterEvent(Feature feature) {
        super(feature);
    }
}
