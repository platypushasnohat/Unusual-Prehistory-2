package com.barlinc.unusual_prehistory.entity.ai.goals;

import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;

public class PachycephalosaurusTargetNearbyPlayersGoal extends TargetNearbyPlayersGoal {

    private final Pachycephalosaurus pachycephalosaurus;

    public PachycephalosaurusTargetNearbyPlayersGoal(Pachycephalosaurus pachycephalosaurus) {
        super(pachycephalosaurus);
        this.pachycephalosaurus = pachycephalosaurus;
    }

    @Override
    public void start() {
        super.start();
        this.pachycephalosaurus.setCanCharge(false);
    }

    @Override
    public void stop() {
        super.stop();
        this.pachycephalosaurus.setCanCharge(true);
    }
}
