# Oversikt

Student:

## Om funksjonaliteten

Del 1 detaljert beskrivelse av systemet:
SimAnimal er flyttet fra examples til objects.
SimAnimal ser kun mat og farer foran seg.
Synsfeltet er på 90 grader. Den ser getViewDistance() langt etter mat, og getRepellantViewDistance etter repellant.
SimAnimal får lov til å ta mat, sålenge det ikke er farer fremfor eller at maten ligger foran faren. Hvis det ikke det ikke er farer i foran vil den prioritere å ta den beste maten. Hvis det er farer i nærheten vil den ta den nærmeste manten, også komme seg vekk.
Når SimAnimal ser en fare eller fler så snur den seg vekk fra alle. Den flytter seg fortere jo nærmere den er faren.
SimAnimal har en energy-level. Den går ned 0.0004 for hver step. Den går opp 0.2 når den spiser. Den starter på 1, og blir aldri større enn det. Når den blir 0 dør den. 
SimAnimal har en lifetime. Den går ned 0.00001 for hver step. Den starter på 1. Når den blir 0 dør den.

Del 2 og 3 beskrivelse av systemet:

Fugler bare flyr over systemet. Når de ser hverandre så begynner de å fly i flokk. Fugler er en subklasse av AbstractMovingObject, og ikke SimAnimal. Dette fordi de hadde for lite til felles med «landdyr». 
Rever spiser harer. De spiser også epler, men prioriterer harer hvis begge deler er innen rekkevidde.
Harer spiser epler. De spiser nærmeste mat, selv om koden for å spise den mest næringsrike maten ligger lett tilgjengelig. De har et synsfelt. Når de ser en rev eller annen fare vil de snu seg vekk og løpe (de kan håndtere flere farer av gangen også).
Rever og harer er begge subklasser av SimAnimal. De har en lifespan og ett energinivå. Hvis en av disse går til null, så dør de. De kan parre seg med hverandre, men kriteriet for det er at de ikke er sultne, har levd en stund, og at det er en stund siden de sist parret seg sist. Begge unngår farer. 

## Svar på spørsmål

Se på Position.move(). Hvordan virker den? Hvordan er den annerledes fra Position-klasse i Lab 5?
-	Position.move()-metodene returnerer et nytt posisjonsobjekt. En av dem bruker en hjelpemetode i Direktion-klassen. De to andre summerer nåværende koordinater og distansen(delta) man skal bevege seg i hver retning. Forskjellen i forhold til til lab 5 er ast det her lages nye posisjonsobjekt, mens det i lab 5 bare ble endret på variablene på det eksisterende posisjonsobjektet (public void).
Hva er forskjellen på AbstractSimObject og AbstractMovingObject? (Det er meningen du skal bruke disse som superklasser når du lager ting selv)
-	AbstractSimObject er en superklasse, og AbstractMovingObject er en underklasse av den. AbstractMovingObject har også funksjonaliteten for bevegelse, der AbstractSimObject er ment for å være stillestående. 
Posisjonen er lagret i en privat feltvariabel. Hvordan er det meningen at subklasser skal kunne justere posisjonen?
-	Med metoden getPosition() i AbstractSimObject.
Hva gjør hjelpemetodene distanceTo og directionTo?
-	Finner enkelt og greit distansen og retningen fra et objekt til et annet. For eksempel this.distanceTo (obj).
AbstractMovingObject har en metode accelerateTo for å endre på farten. Kunne det være smart å gjøre speed feltvariablen private?
-	Det kunne man gjort, men slik det er nå gir det mulighet for å sette andre hastigheter i underklasser.
Vi har ikke laget noen hjelpemetoder for å justere på retningen. Hva må du gjøre for å endre retning i en subklasse? Burde vi ha metoder også for å endre retning?
-	Man må skrive dir  = *ny retning*. Vi har allerede metoder i Position-klasse, så det blir mindre gjentagelse hvis vi bare gjør det slik som her. 
Vi har ingen public metoder for å endre på posisjon, retning, osv. Hadde det vært lurt å ha det? Hvorfor / hvorfor ikke?
-	Samme svar som over. Det blir enklere å bruke hvis vi hadde hatt egne metoder for endre pos og retning, men det blir mindre gjentagelse hvis vi bare bruker de metodene som finnes fra før i Direction og Position. 


1.7: bla bla bla

Spørsmål 2.0:
Hvorfor tror du vi har laget systemet på denne måten? Hadde det vært andre måter å få det til på (ville det isåfall vært like fleksibelt)?
-	Grunnen til at det er laget slik er for å gjøre systemet mest mulig fleksibelt. Man slipper gjentagende kode, og mange forskjellige objekter kan bruke metoder fra andre klasser.
Kunne vi fått dette til uten å bruke grensesnitt?
-	Man kunne tenke seg at det ville vært mulig uten interfaces, og heller brukt superklasser, og underklasser. Dette hadde riktignok blitt veldig tungvint da en klasse kan bli extended av flere klasser, men en klasse kan bare extende en annen.  Dessuten ville hierarkiet blitt veldig merkelig. Man får lagt bedre fleksibilitet og logikk i systemet ved å bruke interfaces. Da kan flere objekter på «forskjellige steder i systemet» implementere samme metoder, slik at man får felles oppførsel.

## Kjente feil
-	Testen for parring fungerer ikke. Det blir ikke flere dyr på brettet.
-	SimAnimal unngår farer utifra farens sentrum, ikke faren sin radius/kant. Det betyr at den i enkelte tilfell kan komme nær kanten på en repellant, men den vil aldri gå over sentrum på faren.  

## Hierarki
AbstractSimObject
-	SimRepellant
-	SimRepellantKOPI
-	SimApple
-	SimFeed
-	AbstractMovingObject
-		SimAnimalKOPI
-		SimBird
-		SimAnimal
-			SimRabbit
-			SimFox



## Kilder til media


Bird (animation):
bevouliin.com
https://opengameart.org/content/pink-flappy-bird-sprite-sheets
CC0

Grass:
athilde
https://opengameart.org/content/seamless-grass-texture-ii
CC0

Fire (animation): 
FoshyTakashi
https://opengameart.org/content/9-frame-fire-animation-16x-32x-64x
CC-BY 3.0

Fox:
Gitt i oppgaven

Rabbit:
Gitt i oppgaven

Eple: 
Gitt i oppgaven


* Rammeverkkode: © Anya Helene Bagge (basert på tidligere utgaver, laget av Anya Helene Bagge, Anneli Weiss og andre).

* pipp.png, bakgrunn.png © Anya Helene Bagge, This work is licensed under a Creative Commons Attribution-ShareAlike 4.0 International License
