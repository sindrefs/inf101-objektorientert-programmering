# Oversikt

Bildene er hentet fra: URL:http://opengameart.org/content/platformer-art-deluxe
Credit "Kenney.nl" or "www.kenney.nl"
Copyright: CC0 1.0 Universal (CC0 1.0) 
Public Domain Dedication


1. Hvilke interface finnes? Hva er sammenhengen mellom dem og hva brukes de til?

Det finnes flere interfaces i prosjektet. De viktigste er IDBObject, IBDMovingObject og IDBKillable, i tillegg finnes IGrid, IList og IGenerator. Disse forteller hvordan de spesifikke klassene skal se ut. De fungerer som oppskrifter. F.eks. implementerer BDPlayer IBDKillable (samtidig som den extender AbstractBDMovingObject som implementerer IBDMovingObject). Det forteller player at den skal ha kill()-metoden fra IBDKillable, og videre hvordan metodene for bevegelse skal implementeres fra IBDMovingObject. IBDObject forteller om de felles metodene et objekt skal ha.

IBDMovingObject implementerer IDBObject fordi det er et objekt. IBDKillable implementerer igjen IBDMovingObject fordi det er et beveglig objekt.


2. Hva slags rolle spiller arv i designet til programmet?

Man sparer mye koding fordi du kan ha objekter som er ganske like, men ikke helt, uten s�rlig mye mer kode. En childklasse kan velge � feks. kun override �n metode som er annerledes for det objektet. 

3. Det er flere abstrakte klasser i systemet. Hva slags funksjon har de? Hvorfor er de abstrakte?

Vi har AbstractFallingObject, AbstractKillingObject, AbstractMovingObject og AbstractBDObject. En abstrakt klasse er en mellomting mellom et interface og en parent class. De kan inneholde en kombinasjon av metoder med og uten implementasjon. Alts� kan en klasse som extender den abstrakte klassen bestemme mer hvordan dens oppf�rsel skal v�re, fremfor � m�tte implementere ALLE metodene fra interfacet. Hvis du feks. trenger en metode som gjelder for mange objekter, mens resten av oppf�rselen er forskjellig, er en abstrakt klasse best � bruke. Hvis noe av den "felles oppf�rselen" (implementert metoden(e)) ikke gjelder for et objekt s� kan man override den ogs�.

4. Hvor er hoveddelen av logikken til spillet er implementert? F� oversikt over metodene, hvor de er implementert, hvordan de kalles.

Main er selvsagt "hjertet" av programmet. Herfra blir kartet startet lest, og GUI satt igang. Det er allikavel i BoulderDashGUI at programmet begynner � gj�re noe vettugt. Mye av logikken ligger ogs� i BDMap. Oppf�rselen till hvert enkelt objekt ligger i det objektklasse.

5. Hva slags rolle spiller abstraksjon i dette programmet?

Abstraksjonen gj�r det mulig � generere mange kart uten � ha reperende kode. 

6. Hvordan kunne man lagt til en ny type feltobjekter?

For � lage et nytt kartobjekt m� man f�rst lage en klasse for objekttypen. Deretter velger man at den skal extende den klassen som representerer hva slags objekt det er, evt. ogs� implementere IBDKillable. 

7. Hvordan er det implementert at en diamant faller?

IDDiamond extends AbstractBDFallingObject betyr at IDDiamond f�r egenskapene til et fallende objekt. I AbstractBDFallingObject er det override p� stepmetoden fordi objektet skal kunne falle. I stepmetoden blir derfor falling()metoden kalt. 