# Määrittelydokumentti



## Algoritmit
| Algoritmi | Aikavaativuus | Tilavaativuus |
|-----------|---------------|---------------|
| leveyssunntainen haku| O( \| V \| + \| E \| )| \| V \| |
| syvyyssuntainen haku|  O( \| V \| + \| E \| )| \| V \| |
| Bellmanin-Ford| O( \| V \| * \| E \| ) | \| V \| |
| dijkstra| O( ( \| V \| + \| E \| ) log \| V \| ) | \| V \| |
| a-star| O( ( \| V \| + \| E \| ) log \| V \| ) | \| V \| |
| a-star variaatiot, jos riittää aika (optional)| O( ( \| V \| + \| E \| ) log \| V \| ) | \| V \| |
* Bellman-Ford skipattu ajan puutteen takia

Vaikka alustavasti nämä näyttävät aika samoilta, kiinnostuksen kohteena onkin, "kuinka samoja" nämä tosiaan ovat ja erityisesti mitä vaikutuksia eri tietrakenteilla on algoritmin suoritukseen.

## Tietorakenteet algoritmeilla (Voi muuttua inspiraation myötä)
* painollinen ja painoton taulukkokartta, missä painottomassa painot ovat kaikilla sama.
* keko: oma ja java
* jono: oma ja java
* taulukko: 1taulukko simuloitu xy, ja [][] taulukko
* uniikki setti (hash set): java, oma vakio taulukko koko ja oma dynaaminen taulukko koko

## Syöte
* taulukko, jolla ominaisuutena kuinka monta pistettä siihen astuminen maksaa
    * pari syöte lukua jolla conffaa karttageneraattorin
    * vaihtoehtoisesti generoidaan kartta tiedostosta
    * syötteessä lainattu karttoja https://movingai.com/benchmarks/grids.html
* syötteitä ei ole erikseen tarkistettu overflowista, mutta etäisyydet tallennetaan double arvoilla, niin käytännössä tätä ei pitäisi tapahtua ellei oikeasti yritä

## Tavoitteet ja analyysit
### Kyseessä on tutkimustyö ja tämä täytetään vertailuja toteuttaessa. 
Tavoitteena on verrata ylläolevat algoritmeja yleisesti sekä erityyppisissä kartoissa. Pääainona on tarkastella miten tietorakenteita muokkaamalla suorityskyky muuttuu. Tätä tutkimusta pitää pystyä jatkamaan tai käyttämään välituloksena esimerkiksi kielten tai käyttöjärjestelmän välisiä tehokkuus ja tilakäyttöä varten.

**Tällä kertaa rajoitutaan Javaan ja yhteen tietokoneeseen.**  

Kiinnostuken kohteena ovat esteetön avoin kartta, pieniä esteitä ja eripainoisia alueita kartassa, harhaan johtava labyrintti kartta ja portaalikartta (portaali jos aikaa riittää, sillä tätä varten tarvitaan rakenteellisia muutoksia taulukkoverkon sisältöön, pelkkä painoarvo ei riitä) Ei riittänyt.  
Näistä luodaan omat tulos dokumentit. Jossa on mainittu seuraavat:
* teoreettinen tila ja aika vaatimuus
* todellinen aika ja tila vaatimuus jokaiselle käytetylle kartalle, sillä on mahdollista, että esim käyttöjärjestelmä ja kielenvalinta voi vaikuttaa tähän.
* askelten määrä
* taulukko jota käytettiin vertaamiseen
* tietokoneen tiedot siis käyttöjärjestelmä, tietokoneen osat.

Yhteenvedossa tai toisinsanoen vertailussa vertaillaan algoritmin toteustavan kohden tuloksia. rajaus yhteen koneeseen tässä siltä osalta, ettei sitä huomioida tässä. Erikseen mainitaan halvin tilaa vasten ja nopein, ja listataan järjestyksessä kaikkien kerskiarvo tulokset. Järjestyskriteeri on ensisijaisesti polun paino ja toissijaisesti aika. (jos kohdetta ei löydy paino on 0) ja täten ylin listassa.

*Sivupainona on generaattori joka asettaa painot ja esteet kartalle, joko oikeasti generoiden tai ottaa tiedoston vastaan josta lukee kartan ja siitä generoi ohjelman muistinsisäisen kartan. Jos algoritmit loppuu kesken sovelletaan matopeliin tai työstetään generaattorille kartta tyylejä.*

##### Lähteet
* https://www.cs.helsinki.fi/u/saska/tira.pdf hakujen vaativuudet, pseudot algoritmeihin ja tietorakenteisiin
* https://movingai.com/benchmarks/grids.html karttoja lainattu, pienellä modauksella  
