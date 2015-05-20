package at.er.sfmb.plugin.feature.event;

import at.er.sfmb.plugin.feature.Feature;

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
