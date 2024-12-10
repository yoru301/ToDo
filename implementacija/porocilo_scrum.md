# Poročilo o napredku (SCRUM)

## Sprint Pregled

**Trajanje Sprinta:** 2 tedna  
**Sprint cilj:** Implementacija funkcionalnosti za upravljanje prilog, varnost, in izboljšave vmesnika.

---

## Sprint Tabela (Scrum Board)

| **Status** | **Naloga**                                         | **Opis**                                                                                 |
| ---------- | -------------------------------------------------- | ---------------------------------------------------------------------------------------- |
| **Done**   | Analiza uporabniških zahtev                        | Zbiranje zahtev in pričakovanj uporabnikov glede funkcionalnosti sistema.                |
|            | Priprava tehničnega načrta                         | Priprava tehnične arhitekture za implementacijo funkcionalnosti.                         |
|            | Dodajanje podpore za shranjevanje prilog           | Implementacija funkcionalnosti za nalaganje in shranjevanje prilog na strežnik.          |
|            | Razširitev API-jev za upravljanje prilog           | Razvoj API-jev za manipulacijo prilog, vključno z nalaganjem in odstranjevanjem.         |
|            | Prilagoditev uporabniškega vmesnika                | Posodobitev uporabniškega vmesnika za podporo delu z datotekami.                         |
|            | Povezava med frontendom in backendom               | Vzpostavitev povezave med uporabniškim vmesnikom in API-ji za priloge.                   |
| **Doing**  | Validacija in obvestila za uporabnike              | Dodajanje preverjanja pravilnosti vnosa podatkov in obveščanje uporabnikov o rezultatih. |
|            | Priprava dokumentacije za razvijalce in uporabnike | Dokumentiranje ključnih funkcionalnosti in navodil za uporabo sistema.                   |
|            | Uvedba funkcionalnosti vmesnika                    | Finalizacija uporabniškega vmesnika z vsemi povezanimi funkcionalnostmi.                 |
| **To Do**  | Implementacija varnostnih ukrepov za priloge       | Dodajanje zaščite proti zlorabi, preverjanje datotek, varnostne omejitve za nalaganje.   |
|            | Testiranje backend funkcionalnosti                 | Izvedba avtomatskih in ročnih testov za preverjanje delovanja backend funkcionalnosti.   |
|            | Celovito testiranje sistema                        | Preverjanje celotne integracije sistema in odprava napak pred objavo.                    |

---

## Sprint Povzetek

### **Opravljene naloge:**

1. **Analiza uporabniških zahtev:** Ugotovili smo, da so uporabniki potrebovali robustno rešitev za shranjevanje prilog z jasnim API in varnostnimi omejitvami.
2. **Priprava tehničnega načrta:** Načrtovali smo arhitekturo, ki vključuje podporo za priloge, validacijo, varnostne ukrepe in integracijo s frontendom.
3. **Dodajanje podpore za shranjevanje prilog:** Implementirali smo funkcionalnost za nalaganje datotek in shranjevanje na strežnik, vključno z organizacijo shranjevalnih map.
4. **Razširitev API-jev za upravljanje prilog:** Ustvarili smo REST API-je za nalaganje, pridobivanje in brisanje prilog.
5. **Prilagoditev uporabniškega vmesnika:** Posodobili smo vmesnik, da uporabnikom omogoča nalaganje in pregledovanje prilog.
6. **Povezava med frontendom in backendom:** Uspešno integrirali API-je s frontend aplikacijo.

### **Trenutno v delu (Doing):**

- **Validacija in obvestila za uporabnike:** Dodajamo preverjanja za pravilnost vnosa in vizualna obvestila o uspehu ali napakah.
- **Priprava dokumentacije:** Dokumentiramo API-je in funkcionalnosti sistema za razvijalce ter pripravljamo navodila za uporabnike.
- **Uvedba funkcionalnosti vmesnika:** Finaliziramo implementacijo uporabniškega vmesnika.

### **Portebna implementacija:**

- **Implementacija varnostnih ukrepov za priloge:** Preverjanje varnosti datotek, zaščita pred nevarnimi datotekami in validacija.
- **Testiranje backend funkcionalnosti:** Izvedba temeljitega testiranja za zagotovitev pravilnega delovanja vseh API-jev.
- **Celovito testiranje sistema:** Izvedba testov celotne integracije, da zagotovimo stabilnost in funkcionalnost celotnega sistema.

---

## Retrospektiva

### **Kaj je šlo dobro?**

- Učinkovito zaključene nekatere naloge brez večjih odstopanj od načrta.
- Jasna komunikacija med člani ekipe in relativno sprotno posodabljanje table na GitHubu.
- Dobro zasnovan tehnični načrt, ki je olajšal implementacijo.

### **Kaj bi lahko izboljšali?**

- Boljše predvidevanje potencialnih varnostnih izzivov pri nalaganju datotek.
- Pravočasnejša priprava dokumentacije, da bi se izognili kopičenju dela na koncu sprinta.

---

## Scrum Dogovori

- **Dnevni standup:** Vsak član ekipe poroča o napredku in težavah.
- **Sprint review:** Predstavitev dosežkov na koncu sprinta.
- **Sprint retrospective:** Pogovor o izboljšavah v procesu dela.
