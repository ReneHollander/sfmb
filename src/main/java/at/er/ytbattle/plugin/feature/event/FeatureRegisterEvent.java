package at.er.ytbattle.plugin.feature.event;

import at.er.ytbattle.plugin.feature.Feature;

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
