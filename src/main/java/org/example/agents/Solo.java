package org.example.agents;

import org.example.CitySquare;
import org.example.Simulation;
import org.example.TickSteps;
import org.example.riskStrategies.RiskStrategy;

public class Solo extends Citizen{
    public Solo(Simulation simulation, CitySquare citySquare, int agentID, int targetImplantNumber, RiskStrategy riskStrategy) {
        super(simulation,citySquare, agentID, targetImplantNumber, riskStrategy);
    }

    private void attackPsycho(){
        if (this.position.isPsychoHere()) {
            this.position.getPsycho().attack(this.getActualNumberOfImplants());
        }
    }
    public void doTick(TickSteps step){
        super.doTick(step);
        if(!this.isDead) {
            if (step == TickSteps.MAXTAK_ATTACK) attackPsycho();
        }
    }

    protected void doMovement() {
        if (Math.random()*100 <= 40) {
            CitySquare[] neighbourSquares = this.position.getCitySquareLinks();
            this.position.requestMovement(
                    this,
                    neighbourSquares[
                            (int)(Math.random()*neighbourSquares.length)
                            ].squareID
            );
        }
    }
}
