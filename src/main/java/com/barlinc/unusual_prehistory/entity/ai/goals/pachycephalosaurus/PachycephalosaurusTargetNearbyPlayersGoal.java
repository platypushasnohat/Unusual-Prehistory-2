package com.barlinc.unusual_prehistory.entity.ai.goals.pachycephalosaurus;

import com.barlinc.unusual_prehistory.entity.Pachycephalosaurus;
import com.barlinc.unusual_prehistory.entity.ai.goals.TargetNearbyPlayersGoal;

public class PachycephalosaurusTargetNearbyPlayersGoal extends TargetNearbyPlayersGoal {

    private final Pachycephalosaurus pachycephalosaurus;

    public PachycephalosaurusTargetNearbyPlayersGoal(Pachycephalosaurus pachycephalosaurus) {
        super(pachycephalosaurus, 50, 3.0D);
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
