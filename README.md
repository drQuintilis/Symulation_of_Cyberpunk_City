**Project Description**


The project was implemented under the subject Object-Oriented Programming.
This simulation is inspired by the world of “Cyberpunk 2077,” demonstrating interactions among several types of agents:

*Citizen* – A regular resident of Night City, with limited funds and some level of cybernetic augmentation.

*Cyberpsycho* – A citizen driven insane by excessive or faulty cybernetic implants, posing a threat to others.

*Solo* – A mercenary capable of fighting cyberpsychos if they possess enough implants (the required number grows quadratically in relation to the implants the cyberpsycho has).

*Maxtak Police Officer* – A specialized police force agent tasked with subduing cyberpsychos, dependent on budget and recruitment costs.


**This society is influenced by various parameters:**


*Gini Index* – Controls wealth inequality, affecting each citizen’s income and capacity to afford better implants.

*Risk Level* – Determines how likely a citizen is to purchase low-quality (and possibly risky) implants.

*Income and Living Costs* – Each citizen earns a random amount and pays recurring costs, influencing their ability to invest in implants.

The simulation runs on a graph-based model of Night City’s districts. 
If a cyberpsycho appears in a district, citizens flee while Maxtak officers and solos attempt to neutralize the threat.


**Key Functionalities**


*Social Stratification* – Citizens have varied incomes, governed by the Gini Index (low index = more equality, high index = larger income gaps).
Wealthier citizens typically afford high-quality implants, while poorer citizens risk going cyberpsycho by choosing cheaper, flawed options.

*Cyberpsycho Mechanics* – A resident can become a cyberpsycho if they exceed their safe implant threshold or use faulty cybernetics. 
Once someone turns cyberpsycho, panic spreads, and neighbors flee to adjacent districts.

*Maxtak Police* – An organized force funded by a set budget.
Can hire new officers (recruitment cost) and must also sustain existing ones (salaries).
The stronger the cyberpsycho (number of implants), the more officers required for a successful takedown (1:1 ratio in basic implementation).

*Solos* – Individual mercenaries who can fight cyberpsychos if they have enough implants to match the threat.
The relationship is quadratic: for every implant a cyberpsycho has, the solo needs that number squared to fight them alone.

*Flexible Configuration* – Variables like budget, risk, implant cost, and actual vs. declared malfunction probability can be adjusted to explore different outcomes.


**Example Scenarios**


High-Quality, Expensive Implants:
Fewer cyberpsychos overall, but only a small portion of the population can afford implants.

Cheap, Faulty Implants:
Many citizens get augmented, but the chance of becoming a cyberpsycho is much higher, causing frequent crises.

Low Police Salaries, Large Maxtak Force:
Maxtak is numerous because each officer is inexpensive to maintain. However, quality and morale might be questionable if the budget is low.


![Bez tytułu](https://github.com/user-attachments/assets/65079e0c-647d-4e1b-a02f-3455aa4f765b)


**Future Extensions**

GUI – Adding real-time visualization of districts, agent movements, and conflicts.

New Agent Types – Such as netrunner, ripperdoc, and fixer, each with unique cyberpunk-related interactions.

Advanced Economy – Implementing inflation, fluctuating implant prices, and supply-demand mechanics.

Upgrading Implants – Citizens replacing outdated components with higher-grade versions over time.

Dynamic Parameters – Adjusting risk factors and budgets mid-simulation to explore real-time strategic shifts.
