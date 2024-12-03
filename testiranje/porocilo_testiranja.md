# Poročilo o testiranju

## Opis testov

V okviru testiranja smo pripravili več testov, ki preverjajo različne vidike delovanja aplikacije:

1. **`testGetTaskById`**  
   Ta test preverja, ali metoda `getTaskById` uspešno pridobi nalogo po njenem ID-ju. Pomemben je, ker zagotavlja, da aplikacija pravilno upravlja s podatki v bazi in omogoča uporabnikom dostop do specifičnih nalog.

2. **`testCreateTask`**  
   Ta test preverja, ali metoda `createTask` uspešno ustvari novo nalogo in ji dodeli ID. Preizkus je bil ponovljen petkrat, da se preveri stabilnost in zanesljivost ustvarjanja nalog.

3. **`testDeleteTask`**  
   Test preverja, ali metoda `deleteTask` uspešno izbriše nalogo iz baze in ali se po brisanju naloga več ne nahaja v bazi. Pomemben je za preverjanje integritete baze po brisanju podatkov.

4. **`testFilterTasksNoMatch`**  
   Test preverja, ali metoda za filtriranje nalog vrne prazno zbirko, če ni ujemajočih rezultatov. Pomembno je, da aplikacija pravilno obravnava primere, ko ne najde ustreznih nalog.

5. **`testGetAllTasksWhenEmpty`**  
   Test preverja, ali metoda za pridobivanje vseh nalog vrne prazno zbirko, ko je baza prazna. Zagotavlja pravilno delovanje aplikacije v mejnih primerih.

6. **`testGetAllTasksWhenEmpty2`**  
   Ta test preverja stanje baze po brisanju vseh nalog, vendar vključuje napačno pričakovanje (pričakuje, da baza vsebuje eno nalogo, ko bi morala biti prazna). Namen tega testa je bil preveriti robustnost sistema in odkriti morebitne logične napake.

## Sodelujoči

- Jan Ančevski
- Tilen Gabor
- Hanan Mešić

_Delo je bilo razporejeno enako._

## Analiza uspešnosti testov

- **Napake, odkrite s testiranjem**:

  - Test `testGetAllTasksWhenEmpty2` je odkril logično napako v pričakovanjih o stanju baze. Namesto pričakovane prazne baze je test napačno preverjal prisotnost ene naloge. Napako smo odpravili s pravilno posodobitvijo pričakovanja v testu.
  - Ostali testi niso odkrili napak, kar kaže na stabilnost in pravilno delovanje implementirane logike.

- **Kako so bile napake odpravljene**:
  - Logična napaka v testu `testGetAllTasksWhenEmpty2` je bila odpravljena z uskladitvijo pričakovanj v metodi `Assertions.assertEquals`.

Na splošno so testi potrdili pravilno delovanje večine funkcionalnosti, pri čemer so odkrili manjšo nepravilnost, ki smo jo uspešno odpravili.
