package at.er.sfmb.plugin.feature.event;

import at.er.sfmb.plugin.feature.Feature;

public class FeatureDisableEvent extends FeatureEvent {

    /**
     * Generic feature event
     *
     * @param feature feature event is running for
     */
    public FeatureDisableEvent(Feature feature) {
        super(feature);
    }
}
