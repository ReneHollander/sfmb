package at.er.ytbattle.plugin.feature.event;

import at.er.ytbattle.plugin.feature.Feature;

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
