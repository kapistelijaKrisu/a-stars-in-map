# Viikkoraportti 6  on going

### Tehty
* codecov vaatimusten mukaiseksi koodin muuttaminen 30min~
* Enum solmujen prosessointityypille, kaksisanainen tässä tapauksessa on kuvaavampi kuin muuttuja. 30min~
* Muutama uudelleennimeäminen paremmin kuvaamaan luokan vastuuta. devmoden poisto pois sotkemasta käyttäjää 30min~
* Edelliset hajotti vähän asioita, korjaus 3h~
* Järkevät kartat lisätty, karttalatauksen logiikka uista erotettu. Kontrasti suurennttu raportin karttaan v -> ! ja # -> @ ~2h
* jar luonti ja dynaaminen juurikansion löytö ~1h
* Oikeellisuuden testaus dokumentti "TestDoc.md" viimeistely ~0h
* Todettu travis jätetään välistä ainakin toistaiseksi ~3h
* projektin implementaatio dokkari ja pienet hionnat ~3h
* rajapinnoitettu tietorakenteet ja luotu custom hashset ~2h
* lisätty iso läjä algoritmeja sis. enumit ja rakenteen muutokset ~2h
* korjattu rikkoutuneet testit (uudet menee ensi viikolle) ~1h
* järkevät sisällöt TBD kohtiin raportteihin ~0h

### Päiväkirja skipattu sama kuin tehty
### TODO list priority order
* 1 päivitä testit ja gittiin testikattavuus (viikko7, kattavuus tippui uusien rakenteiden kanssa)
* 2 automatisoitu vertailu dokumentti?
* 3 vertaa ohjelmaa määrittelydokkariin ja korjaa (odottaa vastauksia epäselvyys kohtiin)
* 4 suoritusraportti myös konsoliin
* 5 virhe käsittely yli integer max arvoille (odottaa vastauksia epäselvyys kohtiin)
* 6 funkkari ajaakseen kaikki algoritmit jokaiseen karttaan (bonus)
* 7 max leveys ja korkeus 10k ja varoita yli integer max arvoista (odottaa vastauksia epäselvyys kohtiin)
* 8 viimeinen refaktoirointikierros
* 9 typot pois dokkareista

### Mitä opin
* travis ei supportaa javaa windowsilla
* Refaktoroi varovasti
* hashset

### Epäsevää 
* saiko verrata valmiisiin java luokista koostuviin algoritmeihin?
* Pitäisikö keskittyä eri algoritmi variaatioiden luontiin vai automatisoituun vertailijaan (olisi uusi steppi app_use_caseihin)
* Voiko bellman-fordin poistaa tässä välissä projektin määrittelystä? (edellisen kohdan takia)
* jos dokkarissa mainitsee rajauksen kartan painoihin voiko olettaa, ettei sen arvoa ylitettä vaikka käytännössä on mahdollista?