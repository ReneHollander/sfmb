package at.er.sfmb.plugin.feature.event;

import at.er.sfmb.plugin.feature.Feature;

public class FeatureRegisterEvent extends FeatureEvent {

    /**
     * Generic feature event
     *
     * @param feature feature event is running for
     */
    public FeatureRegisterEvent(Feature feature) {
        super(feature);
    }
}
