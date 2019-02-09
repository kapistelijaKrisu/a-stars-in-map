# Ohjelman määrittelydokumentti

## Ohjelman perustiedot
* Kieli: Java 11
* Gradle 5
* Komentorivi ohjelma

## Päävalikko
* Aseta kartta
* Valitse ajettava algoritmi
* Tuntemattomasta syötteestä ohjelma suljetaan.
* (optional) Luo .map tiedosto käytetystä kartasta
* (optional) Avaa raportti X ohjelmalla. todennäköisesti html ja chromella
* (optional) Käy monta testi kierrosta läpi kerralla

## Ohjelman peruskulku
* askel 1: Aseta kartta
* askel 2: Valitse algoritmi
* askel 3: ?????
* askel 4: Profit. Juurikansion viereen on ilmestynyt doc/reports kansio (jos sitä ei ole), jonka sisällä on raportti algoritmin tuloksista.

### 1 Aseta kartta

#### 1.1 Karttagenetaattorin valinta
* Sisälle rakennettu NoWeightSimpleGenerator alkuvaiheiden testausta varten
* Rakennetaan samanlainen random painojen kanssa.
* Varsinaista analyysiä varten luodaan lukija
* Joskus harmaassa tulevaisuudessa voisi luoda graafinen generaattori millä voi piirtää haluttu testikartta (Ei tira labran scopessa)


##### Kartan luominen lukijalla
* Ohjelman juurikansion vieressä on maps niminen kansio
* maps kansiosta valitaan .map tyyppinen tiedosto
##### .map rakenne
* tiedosto muotoiltu siten, että jokaiselle yksittäiselle merkille on annettu numeerinen painoarvo (0 = seinä)
* kerrottu korkeus "width"
* kerrottu leveys "height" 
* kerrottu alkupiste x y "start 2 3"
* kerrottu määränpää x y "target 43 2"
* # toimii kommenttin merkistä seuraen välilyönti
* älä laita määränpäätä seinään
* kerrottu milloin kartta alkaa "map" ja sen jälkeen merkki per arvo luetaan ignooraen välilyöntejä
* tiedoston luku päättyy kun ollaan luettu korkeuden verran karttaa

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

#### 1.2.1 Kartan luominen
* Heti generaattorin valinna jälkeen generaattori kysyy lisätietoja mm. lukija listaa kartat maps kansiosta, joka on samassa kansiossa juuren kanssa ja kysyy mitä käytetään
* Kyselyn jälkeen luodaan kartta

### 2 Valitse ajettava algoritmi

#### 2.1.success Valitaan listasta algoritmeja testattava
#### 2.1.fail Jos karttaa ei ole asetettu palaudutaan päävalikkoon, missä voidaan toteuttaa askel 1
#### 2.2 Algoritmi saattaa kysyä mitä tietorakenteita tulisi käyttää jos siitä on enemmän variaatioita
#### 2.3 Siirtyy suoraan askel 3:een

### 3 ??????
Tässä taika-askeleessa algoritmi käy haunsa läpi ja kirjoittaa tuloksensa juurikansion viereen muodossa 
doc/reports/kartan_nimi/algoritmin_nimi/aika_leima.md. Vielä määrittämättä raportin sisältö.  
#### Muistilista raportin suunnitteluun
* Miten kirjoitetaan koneen specsit raporttiin
* Kovakoodata vai lukea teoreettiset tila-, aikavaativuudet jostain
* Piirtää tai linkittää kartan
* Kirjoittaa ajan
* Tilan käytön selvitys?
#### Raportin suunnitelma tähän mennessä
* md tyyppinen tiedosto 

### 4 Profit
Palataan päävalikkoon. Voit ihailla tai analysoida tuloksia.

## Testaus
Unit testaus mockiton kanssa. Raportointi jacocon kanssa. Testikattavuuden minimi 70% ja tavoite 90%.

## Javadoc
Kaikki public ja protected.