# Ohjelman määrittelydokumentti

## Ohjelman perustiedot
* Kieli: Java 11
* Gradle 5
* Komentorivi ohjelma

## Päävalikko
* Aseta kartta
* Valitse ajettava algoritmi
* Aja kaikki mahdolliset testit.
* Luo yhteenveto olemassaolevista raporteista.
* Tuntemattomasta syötteestä ohjelma suljetaan.

## Ohjelman peruskulku
* askel 1: Aseta kartta
* askel 2: Aja algoritmi ja luo raportti
* askel 3: Luo yhteenveto raporteista

### 1 Aseta kartta

* 1.1 Karttageneraattorin valinta
* Sisälle rakennettu NoWeightSimpleGenerator alkuvaiheiden testausta varten
* Rakennetaan samanlainen random painojen kanssa.
* Varsinaista analyysiä varten luodaan lukija
* Joskus harmaassa tulevaisuudessa voisi luoda graafinen generaattori millä voi piirtää haluttu testikartta (Ei tira labran scopessa)

#### Kartan lataaminen tiedostosta lukijalla
Ohjelman juurikansion vieressä on maps niminen kansio
* maps kansiosta valitaan .map tyyppinen tiedosto
##### .map rakenne
* tiedosto muotoiltu siten, että jokaiselle yksittäiselle merkille on annettu numeerinen painoarvo (0 = seinä)
* kerrottu korkeus "width"
* kerrottu leveys "height" 
* kerrottu alkupiste x y "start 2 3"
* kerrottu määränpää x y "target 43 2"
* toimii kommenttin merkistä seuraen välilyönti
* älä laita määränpäätä tai alkua seinään
* kerrottu milloin kartta alkaa "map" ja sen jälkeen merkki per arvo luetaan ignooraen välilyöntejä
* tiedoston luku päättyy kun ollaan luettu korkeuden verran karttaa
* Painoarvot merkeille asetetaan decode-begin ja decode-end välissä mallin mukaisesti

```
# esimerkki sisällöstä
decode-begin
T 0
. 1
decode-end
start 1 1
target 1 0
height 2
width 3
map
T..
T.T
```

#### 1.2 Kartan luominen/lataaminen
* Heti generaattorin valinna jälkeen generaattori kysyy lisätietoja mm. lukija listaa kartat maps kansiosta, joka on samassa kansiossa juuren kanssa ja kysyy mitä käytetään
* Kyselyn jälkeen luodaan kartta

### 2 Valitse ajettava algoritmi

* 2.0 tarkistaa, että kartta on validi. Jos ei palataan päävalikkoon
* 2.1 Kysytään halutaanko ajaa kaikki algoritmi vai filtteröidä algoritmin perusteella
* 2.1.1 jos ei valittu kaikkia, niin kysytään minkälaista toteutusta valitusta algoritmista vai kaikki niistä.
* 2.2 Ajetaan algoritmi(t)
* 2.3 luodaan raportit tuloksista 
    * kansioon jonka sisällä ohjelma on muodossa doc/reports/kartan_nimi/algoritmin_nimi/algoritmin_nimi_aika_leima.report.md.

### 3 Luo yhteenveto raporteista
* 3.1 kerätään kaikki raportit ryhmiteltynä doc/reports/kartan_nimi/
* 3.2 otetaan keskiarvot (saman nimisistä + samat implementaatiot)
* 3.3 otetaan nopein ja halvin muistin kannalta eteen sekä järjestetään lista polun painon mukaan ensisijaisesti ja nopeus toissijaisesti
* 3.4 kirjoitetaan tämä doc/reports/kartan_nimi/kartan_nimi_aikaleima_statistics.md muodossa.