package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.maxtak.MaxtakCorp;

public class MaxtakAgent extends Agent {

    private MaxtakCorp maxtakCorp;
    public MaxtakAgent(
            Simulation simulation,
            CitySquare citySquare,
            int agentID
    ) {
        super(agentID, simulation, citySquare);
        this.maxtakCorp = simulation.getMaxtak();
    }

    public void die(){
        super.die();
        this.maxtakCorp.deregisterAgent(this);
    }

    protected void doMovement() {
        Integer[] activePsychoSquares = this.maxtakCorp.getActivePsychoSquares();
        int[] closestPsycho = null;
        for (int i = 0; i < activePsychoSquares.length; i++) {
            int[] path = this.currentSimulation.getCity().getShortestPath(this.position.squareID, activePsychoSquares[i]);
            if (closestPsycho == null || path.length < closestPsycho.length) {
                closestPsycho = path;
            }
        }
        if (closestPsycho == null || closestPsycho.length == 0) return;
        this.position.requestMovement(this,closestPsycho[0]);
    }

    private void attackPsycho(){
        if (this.position.isPsychoHere()) {
            this.position.getPsycho().attack(-1);
        }
    }

    public void doTick(TickSteps step){
        if(!this.isDead) {
            if (step == TickSteps.MOVEMENTS_REQUESTS) doMovement();
            if (step == TickSteps.MAXTAK_ATTACK) attackPsycho();
        }
    }
}
