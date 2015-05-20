package at.er.sfmb.plugin.feature.event;

import at.er.sfmb.plugin.feature.Feature;

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
