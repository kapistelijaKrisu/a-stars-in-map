# Viikkoraportti 3

### Tehty
* Ohjelman tarkka määrittely ~3h
* Suunniteltu kartan lukija ~10min
* gradlen asennus ja pystytys ~1h
* Testien aloitus ja tekeminen nykyisiin luokkiin ~5h
* Teoreettiset vaativuudet lisätty ~15min
* Mockito ja jacoco käyttöön, testejä täydennetty ~2h
* Oshi apin lukemista  https://github.com/oshi/oshi ~2h
* Raportin luonnin alkaminen (kansiorakenne, nimeäminen, templaten aloitus) ~2h
* Esiversio javadocseista valmiisiin komponentteihin, pitää selvittää miten viitataan luokkaan ja @param ja muuttujille ~30min
* Javadocin generoinnin konffaus ~1h
* pieni refaktorointi (magic numbers pois, siivota abstractit luokat) ~2h
* kirjoituksen suunnittelua ~3h
* raportin templaten palasien koonti (systemTools paketti) ~2h

### Suunniteltu seuraavaksi
* 1 ohjelma tallentamaan raportit siis templaten viimeistely ja sen arvojen vaihto
* 2 .map tiedostojen luku
* 3 leveyshaun testit
* 4 tässä vaiheessa jäljelle jää enään algoritmien implementaatiot

### Epäselvyyksiä
* kuinka tarkka javadocin pitäis olla, pitää privaatit muuttujat myäs luetella?
* Pitääkö ohjelman rungon tietorakenteet myös käsin tehdä?

### Mitä opin
* visual code best md file editor
* mockitolla ongelmia tietyn tyyppisten luokkien kanssa -> ei käytetäkkään
* löytyi tapauksia missä mockitosta onkin hyötyä -> takaisin käyttöön
* Mockitolla voi testata abstrakteja luokkia
* Gradle on oikeasti päivittänyt asioita -> jacocoTestReport conffaus ongelmia
* Jacocon peruspystytys gradle 5:lla
* oshi on jännä kirjasto koneen specsien etsimiseen

### Itsekrittiikkiä
* Sain yllättävän paljon "background" duunia tehtyä, mutta ohjelma itsessään ei edennyt niin pitkälle kuin halusin, muutoin oli hieno viikko
