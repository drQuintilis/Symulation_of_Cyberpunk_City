package org.example;

/**
 * Enum określający kolejność wypełniania kroków ticka w symulacji.
 * <p>
 * Kroki ticka obejmują:
 * - IMPLANT_STATUS_UPDATE: Aktualizacja statusu implantów.
 * - CYBER_PSYCHO_ATTACK: CyberPsycho atakuje.
 * - MOVEMENTS_REQUESTS: Prośby o przeniesienie agentów.
 * - MOVEMENTS_EXECUTION: Wykonanie przeniesień agentów.
 * - MAXTAK_ATTACK: Maxtak agenci atakują.
 * - ECONOMICS_UPDATE: Aktualizacja gospodarki.
 * - INFORMATION_PRINT: Wyświetlanie informacji.
 */
public enum TickSteps { //kolejność wypełniania kroków ticka
    IMPLANT_STATUS_UPDATE,
    CYBER_PSYCHO_ATTACK,
    MOVEMENTS_REQUESTS,
    MOVEMENTS_EXECUTION,
    MAXTAK_ATTACK,
    ECONOMICS_UPDATE,
    INFORMATION_PRINT,
}
