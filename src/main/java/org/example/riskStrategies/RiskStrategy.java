package org.example.riskStrategies;

import org.example.agents.Citizen;
import org.example.implants.Implant;
/**
 * Interfejs dla strategii ryzyka.
 */
public interface RiskStrategy { //interfejs dla strategii ryzyka
    /**
     *
     * @param citizen citizen rozważający zakup implanta
     * @param implant implant do zakupu
     * @return true, jeśli citizen powinien kupić implant; false w przeciwnym razie
     */
    public boolean shouldIBuyImplant(Citizen citizen, Implant implant);  // funkcja odpowiadająca za decyzje citizena kupić implant
// która zależy od chęci citizena natychmiast kupić implant, pomnożoną przez pewny współczynnik, który pokazuje, o ile citizen jest ryzykowny
} // (szybciej rośnie chęć, przez co szybciej się zgadza na kupienie gorszych implantów)
