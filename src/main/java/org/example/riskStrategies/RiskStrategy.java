package org.example.riskStrategies;

import org.example.agents.Citizen;
import org.example.implants.Implant;

public interface RiskStrategy { //interfejs dla strategii ryzyka
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant);  // funkcja odpowiadająca za decyzje citizena kupić implant
// która zależy od chęci citizena natychmiast kupić implant, pomnożoną przez pewny współczynnik, który pokazuje, o ile citizen jest ryzykowny
} // (szybciej rośnie chęć, przez co szybciej się zgadza na kupienie gorszych implantów)
