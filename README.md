# ToDo

## Vizija projekta

**Cilj aplikacije** je uporabnikom omogočiti enostaven in intuitiven način za ustvarjanje, urejanje in sledenje dnevnim opravilom ali večjim projektom. To-Do želi izboljšati uporabniško izkušnjo upravljanja z nalogami tako, da združi enostavnost uporabe in funkcionalnosti, ki so uporabnikom resnično v pomoč, kot so opomniki, prioritizacija nalog ter organizacija v skupine ali projekte. Aplikacija je namenjena tako posameznikom kot ekipam, ki želijo učinkoviteje organizirati svoj čas, povečati osredotočenost na ključna opravila in zmanjšati občutek preobremenjenosti.

## Sodelujoči

- Jan Ančevski
- Tilen Gabor
- Hanan Mešić

## 1. Namen aplikacije

**ToDo** je preprosta in intuitivna aplikacija za upravljanje nalog, ki uporabnikom omogoča organizacijo in sledenje njihovim opravilom. Namenjena je uporabnikom, ki želijo izboljšati svojo produktivnost, upravljati vsakodnevna opravila ali enostavno spremljati napredek pri delu na različnih projektih. Aplikacija sledi arhitekturi `frontend-backend`, kjer frontend skrbi za uporabniški vmesnik, backend pa za poslovno logiko in komunikacijo z bazo podatkov.

## 2. Projektna struktura

Projekt je razdeljen v dve glavni komponenti:

- **/frontend**: Vsebuje kodo za uporabniški vmesnik (UI), izdelano z uporabo [izberi tehnologijo, npr. React, Angular]. Vsebuje vse datoteke in mape, ki so povezane s prikazovanjem podatkov, navigacijo in interakcijami z uporabniki.
- **/backend**: Služi kot strežniška stran aplikacije, implementirana v [izberi tehnologijo, npr. Node.js, Express]. Backend vsebuje poslovno logiko, API-je za komunikacijo s frontendom ter dostop do baze podatkov.
- **/database**: Vsebuje datoteko `init.sql`, ki vsebuje SQL skripto za inicializacijo baze podatkov (ustvarjanje baze, tabel in začetnih podatkov).
- **README.md**: Dokumentacija za namestitev in razvoj aplikacije.

## 3. Standardi kodiranja

Projekt sledi naslednjim standardom kodiranja:

- **JavaScript**: Vsi JavaScript (ali TypeScript) moduli in komponente sledijo pravilom za poimenovanje z `camelCase`. Funkcije in spremenljivke se začnejo z malimi črkami, razredi pa z veliko.
- **Linting**: Uporabljamo ESLint za ohranjanje čistosti in enotnosti kode.
- **Formatiranje**: Koda je formatirana s pomočjo Prettier, kar zagotavlja enotno obliko.
- **Komentiranje**: Ključne funkcije in komponente so dokumentirane z JSDoc.

---

## 4. Navodila za nameščanje

### 4.1 Predpogoji

- [Node.js](https://nodejs.org/) (priporočena verzija 16 ali več)
- [npm](https://www.npmjs.com/) za upravljanje paketov
- SQL baza podatkov (MySQL)

### 4.2 Koraki za namestitev

#### 1. Kloniranje repozitorija

Najprej klonirajte repozitorij na svoj računalnik:

```bash
git clone https://github.com/kihecpihec/ToDo.git
cd ToDo
```

#### 2. Namestitev odvisnosti

V korenski mapi in podmapah za frontend in backend namestite vse potrebne odvisnosti:

```bash
cd frontend
npm install
```

#### 3. Zagon aplikacije

V backend mapi zaženite strežnik, nato zaženite aplikacijo na frontendu.

```bash
cd frontend
node index.js
```

## 5. DPU

### 5.1 Diagram

![DPU](DPURIS.png)

### 5.2 Use case opisi
**Primer uporabe: Prijava v račun**
ID: UC1
Cilj: Uporabnik želi dostopati do svojega računa, kjer lahko upravlja naloge.
Akterji: Uporabnik
Predpogoji: Uporabnik že ima ustvarjen račun.
Stanje sistema po PU: Sistem uporabnika prepozna kot prijavljenega in mu omogoči dostop do funkcionalnosti.
Scenarij:
Uporabnik odpre aplikacijo in izbere možnost "Prijava".
Sistem prikaže polji za vnos uporabniškega imena in gesla.
Uporabnik vnese svoje podatke in pritisne "Prijava".
Sistem preveri pravilnost podatkov in uporabnika prijavi v račun.
Alternativni tokovi: Uporabnik lahko izbere možnost za ponastavitev gesla, če ga je pozabil.
Izjeme: Če uporabniško ime ali geslo nista pravilna, sistem prikaže sporočilo o napaki.

**Primer uporabe: Ustvarjanje računa**
ID: UC2
Cilj: Uporabnik želi ustvariti nov račun, da lahko dostopa do funkcionalnosti aplikacije.
Akterji: Uporabnik
Predpogoji: Uporabnik ima dostop do aplikacije in ima vzpostavljeno stabilno internetno povezavo.
Stanje sistema po PU: Sistem ustvari nov račun za uporabnika in shrani njegove podatke.
Scenarij:
Uporabnik izbere možnost "Ustvari račun".
Sistem prikaže polja za vnos osebnih podatkov (uporabniško ime, geslo, e-pošta).
Uporabnik vnese zahtevane podatke in pritisne "Potrdi".
Sistem preveri pravilnost podatkov in ustvari nov račun.
Alternativni tokovi: Če uporabniško ime ali e-pošta že obstajata, sistem prikaže opozorilo.
Izjeme: Če so podatki nepopolni ali napačno vneseni, sistem prikaže sporočilo o napaki.

**Primer uporabe: Iskanje in filtriranje nalog**
ID: UC3
Cilj: Uporabnik želi poiskati specifične naloge ali filtrirati naloge glede na različne parametre, kot so rok ali prioriteta.
Akterji: Prijavljen uporabnik
Predpogoji: Uporabnik je prijavljen v aplikacijo.
Stanje sistema po PU: Sistem prikaže filtrirane naloge glede na uporabnikove nastavitve.
Scenarij:
Uporabnik odpre seznam nalog in izbere možnost "Filtriraj".
Sistem prikaže različne filtre (po rokih, prioriteti, statusu).
Uporabnik izbere želene filtre in pritisne "Uporabi".
Sistem prikaže naloge, ki ustrezajo izbranim kriterijem.
Alternativni tokovi: Če ni najdenih nalog, sistem prikaže obvestilo "Ni rezultatov".
Izjeme: Če pride do napake pri prenosu podatkov, sistem obvesti uporabnika o napaki.

**Primer uporabe: Urejanje To-Do nalog**
ID: UC4
Cilj: Uporabnik želi urediti obstoječo To-Do nalogo.
Akterji: Prijavljen uporabnik
Predpogoji: Uporabnik je prijavljen in ima obstoječo nalogo.
Stanje sistema po PU: Sistem posodobi nalogo z novimi podatki.
Scenarij:
Uporabnik izbere nalogo za urejanje.
Sistem prikaže podrobnosti naloge.
Uporabnik naredi potrebne spremembe in pritisne "Shrani".
Sistem posodobi nalogo in prikaže posodobljeno različico.
Alternativni tokovi: Če uporabnik zapre okno brez shranjevanja, se spremembe zavržejo.
Izjeme: Če pride do napake pri shranjevanju, sistem prikaže obvestilo o napaki.

**Primer uporabe: Dodajanje To-Do nalog**
ID: UC5
Cilj: Uporabnik želi ustvariti novo nalogo, ki jo bo lahko spremljal in urejal.
Akterji: Prijavljen uporabnik
Predpogoji: Uporabnik je prijavljen.
Stanje sistema po PU: Sistem shrani novo nalogo in jo prikaže uporabniku.
Scenarij:
Uporabnik izbere možnost "Dodaj novo nalogo".
Sistem prikaže obrazec za vnos podrobnosti naloge (naziv, rok, prioriteta).
Uporabnik vnese podatke in pritisne "Shrani".
Sistem shrani nalogo in jo prikaže na seznamu nalog.
Alternativni tokovi: Če uporabnik zapre obrazec brez shranjevanja, se naloga ne ustvari.
Izjeme: Če so podatki nepopolni ali napačno vneseni, sistem prikaže sporočilo o napaki.

**Primer uporabe: Brisanje To-Do nalog**
ID: UC6
Cilj: Uporabnik želi izbrisati obstoječo nalogo.
Akterji: Prijavljen uporabnik
Predpogoji: Uporabnik je prijavljen in ima obstoječo nalogo.
Stanje sistema po PU: Sistem izbriše nalogo in jo odstrani s seznama nalog.
Scenarij:
Uporabnik izbere možnost za brisanje ob nalogi.
Sistem prikaže obvestilo za potrditev brisanja.
Uporabnik potrdi brisanje.
Sistem izbriše nalogo in jo odstrani s seznama.
Alternativni tokovi: Če uporabnik prekliče brisanje, se naloga ohrani.
Izjeme: Če pride do napake pri brisanju, sistem prikaže obvestilo o napaki.

**Primer uporabe: Filtriranje glede na rok in prioriteto**
ID: UC7
Cilj: Uporabnik želi filtrirati naloge glede na rok ali prioriteto, določeno z vrednostmi od 1 (nizka) do 3 (visoka prioriteta).
Akterji: Prijavljen uporabnik
Predpogoji: Uporabnik je prijavljen v aplikacijo in ima obstoječe naloge z določenimi roki in prioriteto.
Stanje sistema po PU: Sistem prikaže filtriran seznam nalog glede na izbrane filtre.
Scenarij:
Uporabnik odpre seznam nalog in izbere možnost za "Filtriranje po roku in prioritete".
Sistem prikaže možnosti filtriranja po rokih in prioritete (1, 2, ali 3).
Uporabnik izbere želene filtre in potrdi.
Sistem prikaže naloge, ki ustrezajo izbranim kriterijem.
Alternativni tokovi: Če ni najdenih nalog, sistem prikaže obvestilo "Ni nalog s temi kriteriji".
Izjeme: Če pride do napake pri filtriranju, sistem prikaže obvestilo o napaki.

**Primer uporabe: Vzdrževanje backenda**
ID: UC8
Cilj: Admin želi poskrbeti za nemoteno delovanje aplikacije s preverjanjem in posodabljanjem back-end sistema.
Akterji: Admin
Predpogoji: Admin mora imeti dostop do skrbniškega računa in sistem mora delovati.
Stanje sistema po PU: Backend sistema je posodobljen ali optimiziran za delovanje.
Scenarij:
Admin se prijavi v skrbniški račun.
Admin dostopa do konzole za upravljanje sistema.
Admin pregleda stanje backenda, zazna morebitne težave ali možnosti za izboljšave.
Admin izvede potrebne posodobitve ali popravke in preveri, če vse deluje pravilno.
Alternativni tokovi: Če sistem ne deluje pravilno, admin ponovno preveri nastavitve in izvede diagnostiko.
Izjeme: Če pride do napake pri posodabljanju, sistem prikaže obvestilo o napaki in admin poskuša težavo odpraviti ali ponastaviti sistem.

**Primer uporabe: Vzdrževanje frontenda**
ID: UC9
Cilj: Admin želi posodobiti ali optimizirati uporabniški vmesnik za boljšo uporabniško izkušnjo.
Akterji: Admin
Predpogoji: Admin ima dostop do skrbniškega računa in znanje o frontend razvoju.
Stanje sistema po PU: Sistem ima posodobljen ali optimiziran uporabniški vmesnik.
Scenarij:
Admin se prijavi v skrbniški račun in dostopa do možnosti urejanja frontenda.
Admin pregleda stanje uporabniškega vmesnika in prepozna morebitne izboljšave.
Admin posodobi vmesnik, popravi morebitne napake ali doda nove funkcionalnosti.
Sistem posodobi uporabniški vmesnik in omogoči adminu pregled posodobitev.
Alternativni tokovi: Če pride do težav z dizajnom, se admin odloči za povrnitev na prejšnjo različico.
Izjeme: Če pride do napake, admin prejme obvestilo in preveri frontend kodo.

**Primer uporabe: Dodajanje funkcionalnosti**
ID: UC10
Cilj: Admin želi izboljšati aplikacijo z dodajanjem novih funkcionalnosti za uporabnike.
Akterji: Admin
Predpogoji: Admin ima dostop do skrbniškega računa in razvojna orodja.
Stanje sistema po PU: Sistem ima novo funkcionalnost, ki je pripravljena za uporabnike.
Scenarij:
Admin se prijavi v skrbniški račun in dostopa do razvojne konzole.
Admin načrtuje in implementira novo funkcionalnost.
Sistem prenese kodo in nove funkcionalnosti ter omogoči testiranje.
Admin preveri delovanje funkcionalnosti in potrdi, da je na voljo uporabnikom.
Alternativni tokovi: Če funkcionalnost ni optimizirana, jo admin začasno odstrani in preveri kodo.
Izjeme: Če pride do napake pri implementaciji, admin prejme obvestilo in izvede popravek.

**Primer uporabe: Brisanje uporabnikov**
ID: UC11
Cilj: Admin želi odstraniti uporabniški račun zaradi kršitev pravil ali na željo uporabnika.
Akterji: Admin
Predpogoji: Admin mora biti prijavljen kot skrbnik.
Stanje sistema po PU: Uporabnik je izbrisan in njegov dostop do sistema onemogočen.
Scenarij:
Admin se prijavi v skrbniški račun.
Admin dostopa do seznama uporabnikov in izbere uporabnika, ki ga želi izbrisati.
Sistem prikaže opozorilo za potrditev brisanja uporabnika.
Admin potrdi brisanje in sistem odstrani uporabniški račun.
Alternativni tokovi: Če admin izbere napačnega uporabnika, brisanje prekliče.
Izjeme: Če pride do napake pri brisanju, sistem prikaže obvestilo in admin poskusi znova.

## 6. Besednjak

### 6.1 Naloga (Task)

-**Definicija:** Osnovna enota v aplikaciji, ki predstavlja opravilo ali cilj, ki ga želi uporabnik doseči.

-**Uporaba:** Naloge lahko uporabnik oznaci (narejeno, nenarejeno, v poteku...), zato da vidi svoj napredek.

### 6.2 Rok (Deadline)

-**Definicija:** Določen datum, do kdaj naj bo naloga dokončana.

-**Uporaba:** Uporabnik lahko nastavi rok za vsako nalogo, da jo lažje opravi pravočasno.

### 6.3 Prioriteta (Priority)

-**Definicija:** Vrednost, ki nalogi določa njeno pomembnost. Običajno je rangirana od "nizke" do "visoke" prioritete.

-**Uporaba:** Uporabniki lahko določijo prioriteto vsaki nalogi, kar omogoča lažjo organizacijo opravil po pomembnosti.

### 6.4 Iskalna vrstica (Search Bar)

-**Definicija:** Orodje v uporabniškem vmesniku, ki omogoča uporabnikom, da hitro poiščejo določeno nalogo.

-**Uporaba:** Uporabniki lahko hitro najdejo specifične naloge z vnosom besed v iskalno vrstico, kar izboljša navigacijo med številnimi nalogami.

### 6.5 Uporabnik (User)

-**Definicija:** Oseba, ki uporablja aplikacijo "To-Do" za organizacijo in upravljanje svojih nalog.

-**Uporaba:** Uporabnik se lahko registrira in prijavi v aplikacijo, da dostopa do svojih osebnih seznamov nalog, doda nove naloge, označi naloge kot opravljene, ter uporablja iskalne funkcije za učinkovitejše sledenje napredku.
