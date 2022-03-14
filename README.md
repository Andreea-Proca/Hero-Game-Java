# Hero-Game-Java
 Swing, IntelliJ, JSON
 

In implemenatrea proiectului am uitilizat sabloanele de proiectare Singleton, Factory, Builder si Visitor.
La final, tot progresul este salvat intr-un fisier JSON.
In terminal, mutarea dintr-o casuta in alta se face cu literele „N”, „S”, „E”, „W”.
In interfata grafica, mutarea se face cu butoanele „North”, „South”, „East”, „West”.
Clasa Test
 Contine metoda statica main care instantiaza un obiect de tip Game si porneste jocul.
Clasa Game
 Contine:
- o instanta a clasei
- o metoda care returneaza o instanta a clasei
- un constructor privat de unde jucatorul poate alege din terminal daca vrea sa joace jocul in terminal sau interfata 
grafica.
Daca a fost selectat modul "terminal" atunci se va rula un set de instructiuni predefinite, altfel deschide o fereastra 
cu interfata grafica.
- lista de conturi in care s-au autentificat jucatorii
- un hashmap avand drept cheie tipul celulei si drept valoare o lista cu siruri care contine povestile
- metoda de run care porneste un joc nou (incarca datele parsate dintr-un JSON si ofera utilizatorului
posibilitatea sa aleaga contul si characterul cu care vrea sa joace.
- metoda care salveaza progresul intr-un fisier JSON (src/output/UpdatedAccounts.json)
 Am utilizat sablonul Singleton cu instantiere intarziata pentru a restrictiona numarul de instantieri
ale clasei Game.
Clasa TerminalType
 Ruleaza jocul in terminal, daca a fost ales acest mod.
 Contine:
- lista de conturi
- un hashmap avand drept cheie tipul celulei si drept valoare o lista cu siruri care contine povestile
- o metoda ce ruleaza jocul in terminal, jucatorul putand alege contul, characterul, apoi primind comenzi si afisand 
mesaje si harta;
in functie de celula curenta, afiseaza povestea si optiunile
- metoda care afiseaza optiunile jucatorului: daca este pe o celula de tip Shop sau Enemy, daca este Enemy,
simuleaza lupta dintre character si acesta pana cand unul din ei are viata mai mica sau egala cu 0, apoi verifica cine a 
murit
si afiseaza un mesaj corespunzator
- metoda care afiseaza Shop-ul si potiunile disponibile si primeste urmatoarea comanda: daca jucatorul cumpara sau 
nu o potiune,
daca da, se apeleaza functia de cumparare de potiuni din clasa Character
- metoda prin care jucatorul ataca inamicul: sunt afisate optiunile: atac (ataca inamicul cu damage-ul characterului),
folosire abilitati(se afiseaza abilitatile si este folosita cea aleasa de jucator) si folosire potiuni din inventar
(se afiseaza potiunile si este folosita cea aleasa de jucator)
- metoda prin care inamicul ataca jucatorul: foloseste o abilitate la intamplare
- metoda prin care este afisatata povestea corespunzatoare pentru tipul celulei curenta, daca nu a fost vizitata,
altfel se afiseaza un mesaj corespunzator
Clasa GUIType
 Ruleaza jocul in interfata garfica, daca a fost ales acest mod.
 Contine:
- lista de conturi
- un hashmap avand drept cheie tipul celulei si drept valoare o lista cu siruri care contine povestile
- o metoda ce ruleaza jocul intr-o fereastra, jucatorul putand alege contul la care vrea sa se conecteze
- metoda prin care jucatorul isi poate alege din interfata garfica characterul cu care vrea sa joace
- metoda care afiseaza interfata grafica in care se va desfasura jocul: harta, butoane
- metoda care afiseaza optiunile jucatorului: daca este pe o celula de tip Shop sau Enemy, daca este Enemy,
simuleaza lupta dintre character si acesta pana cand unul din ei moare
- metoda care afiseaza Shop-ul si potiunile disponibile si primeste urmatoarea comanda: daca jucatorul cumpara sau 
nu o potiune,
daca da, se apeleaza functia de cumparare de potiuni din clasa Character
- metoda care verifica daca characterul sau inamicul a murit si afiseaza un mesaj corespunzator(daca inamicul 
moare,
jucatorul poate trecec mai departe, daca jucatorul moare, jocul se termina si interfata garfica se inchide)
- metoda prin care jucatorul ataca inamicul: intre-o noua fereastra sunt afisate optiunile: atac (ataca inamicul cu 
damage-ul characterului),
folosire abilitati(se afiseaza abilitatile si este folosita cea aleasa de jucator) si folosire potiuni din inventar
(se afiseaza potiunile si este folosita cea aleasa de jucator)
- metoda prin care inamicul ataca jucatorul: foloseste o abilitate la intamplare
- metoda prin care este afisatata povestea corespunzatoare pentru tipul celulei curenta, daca nu a fost vizitata,
altfel se afiseaza un mesaj corespunzator
- metoda prin care se afiseaza informatiile(health, mana, experienta, nivel, bani, protectii) despre character si inamic,
daca acesta exista in casuta curenta
- metoda prin care, la finalul jocului, daca jucatorul a casitgat, este afisat progresul characterului:
experineta acumulata, bani castigati, noul nivel, inamici invinsi
Clasa Account
 Contine:
- informatii despre jucator, obiect de tip Information
- lista cu toate personajele contului
- numarul de jocuri jucate de jucator
- constructor al clasei
 Clasa Information
 -> interna clasei Account
 Contine:
 - credentialele jucatorului (tip Credentials)
 - o colectie sortata care retine jocurile preferate
 - nume
 - tara
 - constructor privat
 - metoda care returneaza credentialele
 - metoda care returneaza jocurile preferate
 - metoda care returneaza numele
 - metoda care returneaza tara
 - clasa interna statica "UserBuilder"
 Contine:
 - credentialele jucatorului (tip Credentials)
 - o colectie sortata care retine jocurile preferate
 - nume
 - tara
 - metoda constructor care instantiaza un obiect de tip Credentials
 - metoda constructor care seteaza jocurile favorite
 - metoda constructor care seteaza numele
 - metoda constructor care seteaza tara
 - metoda build care instantiaza un obiect de tip Information
 - o clasa exceptie care arunca un mesaj de eroare atunci cand lipsesc credentialele sau numele
 Am utilizat sablonul Builder pentru a instantia un obiect de tip Information.
Clasa Credentials
 Respecta principiul incapsularii.
 Contine:
- email
- parola
- un cosntructor al clasei
- o metoda getter care returneaza email-ul
- o metoda getter care returneaza parola
Clasa Grid
 Este o lista de liste.
 Mosteneste clasa ArrayList si reprezinta un ArrayList de ArrayList-uri de obiecte de tip Cell.
 Contine:
- lungimea tablei de joc
- latimea tablei de joc
- personajul curent
- celula curenta in care se afla characterul
- celula anterioara in care s-a aflat characterul
- constructorul (privat)
- metoda setter care seteaza characterul
- metoda setter care seteaza celula curenta
- metoda statica care genereaza harta in functie de lungimea si latimea date ca parametru
(initializeaza intre 2 si 6 obiece de tip Shop si intre 4 si 8 obiecte de tip Enemy,
unul de tip Finish, iar restul de tip Empty si le plaseaza la intamplare pe harta;
seteaza celula curenta ca fiind prima de pe harta)
- metoda de deplasare spre nord, daca nu este posibil, afiseaza un mesaj corespunzator
- metoda de deplasare spre sud, daca nu este posibil, afiseaza un mesaj corespunzator
- metoda de deplasare spre est, daca nu este posibil, afiseaza un mesaj corespunzator
- metoda de deplasare spre vest, daca nu este posibil, afiseaza un mesaj corespunzator
- metoda de afisare a hartii in terminal
- metoda de afisare a hartii in interfata grafica, sub forma unei matrici de butoane
- metoda care afiseaza imagini in celulele hartii, in functie de elementul care se afla
in celula respectiva si daca celula a fost vizitata
Clasa Cell
 Rreprezinta casuta a hartii.
 Contine:
- coordonatele pe Ox si Oy de pe harta
- tipul celulei
- elementul (inamicul/magazinul/finish/empty) din casuta (tip CellElement)
- variabila ce retine daca casuta a fost vizitata sau nu
- metoda getter care returneaza tipul celului
- metoda setter care seteaza tipul celului
- metoda getter care returneaza elementul din cazuta curenta
- metoda setter care seteaza elementul din cazuta curenta
- metoda care returneaza daca casuta a fost vizitata sau nu
- metoda care seteaza casuta ca fiind vizitata/nevizitata
Interfata CellElement
 Este implementata de clasele Enemy, Shop, Finish si Empty.
 Reprezinta elementul aflat pe casuta curenta.
 Contine:
- metoda ce intoarce caracterul de afisat in terminal corespunzator obiectului din casuta curenta
Interfata Element
 Folosita pentru a implementa sablonul Visitor.
 Contine:
- metoda care accepta un obiect de tip Visitor
 Metoda este implementata in clasele Warrior, Mage, Rouge, Enemy.
Clasa abstracta Entity
 Implementeaza interfata Element.
 Contine:
- o lista de abilitati(spells)
- viata curenta
- viata maxima
- mana curenta
- mana maxima
- variabia care retine daca obiectul are protectie contra fire
- variabia care retine daca obiectul are protectie contra ice
- variabia care retine daca obiectul are protectie contra earth
- metode concrete:
 - metoda pentru regenerarea vietii
 - metoda pentru regenerarea manei
 - metoda pentru folosirea unei abilitati (primeste abilitatea si inamicul; daca exista suficienta mana,
va folosi abilitatea impotriva acestuia)
- metode abstracte (implementate in clasele Warrior, Rogue, Mage, Enemy):
 - metoda pentru acceptarea unui obiect de tip Visitor
 - metoda pentru pierderea de viata
 - metoda pentru calcularea damage-ului dat de entitate
Clasa abstracta Character
 Extinde clasa Entity.
 Contine:
- numele characterului
- coordonatele curente ale personajului
- un obiect de tip Inventory
- experienta
- experienta pe care o avea cand a inceput jocul
- nivelul
- nivelul pe care il avea cand a inceput jocul
- numarul de inamici invinsi
- variabila care retine valoare pentru strength
- variabila care retine valoare pentru charisma
- variabila care retine valoare pentru dexterity
- metoda care accepta un obiect de tip Visitor
- metoda ce inregistreaza o pierdere de viata (exista o sansa ca damage-ul primit sa fie injumatatit)
- metoda de calculare a valorii damage-ului (exista o sansa ca damage-ul dat sa fie dublu)
- metoda de adaugare a abilitatilor
- metoda de adaugare de potiuni bonus la inceperea unui joc nou
- metoda care calculeaza nurmal de inamici invinsi
- metoda care calculeaza nivelul curent ai characterului
- metoda care inregistreaza cresterea experientei
- metoda care intregistreaza castigarea banilor
- metoda care calculeaza strength-ul characterului
- metoda care calculeaza charisma characterului
- metoda care calculeaza dexterity-ul characterului
- metoda pentru cumpararea unei potiuni din magazinul dat ca parametru
Clasa Warrior
 Extinde clasa Character.
 Este imun la fire si are ca atribut principal strength.
 Contine:
- damage
- metoda care accepta un obiect de tip Visitor
- constructor
- constructorul care initializeaza si seteaza greutatea inventarului, health, mana, protectia si abilitatile, nivelul si 
experienta
- metoda de adaugare a abilitatilor
- metoda de adaugare de potiuni bonus la inceperea unui joc nou
- metoda ce inregistreaza o pierdere de viata (exista o sansa ca damage-ul primit sa fie injumatatit)
- metoda de calculare a valorii damage-ului (exista o sansa ca damage-ul dat sa fie dublu)
- metoda care returneaza damage-ul characterului
 Am utilizat sablonul Factory pentru a instantia characterele din lista contului.
Clasa Rogue
 Extinde clasa Character.
 Este imun la earth si are ca atribut principal dexterity.
 Contine:
- damage
- metoda care accepta un obiect de tip Visitor
- constructor
- constructorul care initializeaza si seteaza greutatea inventarului, health, mana, protectia si abilitatile, nivelul si 
experienta
- metoda de adaugare a abilitatilor
- metoda de adaugare de potiuni bonus la inceperea unui joc nou
- metoda ce inregistreaza o pierdere de viata (exista o sansa ca damage-ul primit sa fie injumatatit)
- metoda de calculare a valorii damage-ului (exista o sansa ca damage-ul dat sa fie dublu)
- metoda care returneaza damage-ul characterului
 Am utilizat sablonul Factory pentru a instantia characterele din lista contului.
Clasa Mage
 Extinde clasa Character.
 Este imun la ice si are ca atribut principal charisma.
 Contine:
- damage
- metoda care accepta un obiect de tip Visitor
- constructor
- constructorul care initializeaza si seteaza greutatea inventarului, health, mana, protectia si abilitatile, nivelul si 
experienta
- metoda de adaugare a abilitatilor
- metoda de adaugare de potiuni bonus la inceperea unui joc nou
- metoda ce inregistreaza o pierdere de viata (exista o sansa ca damage-ul primit sa fie injumatatit)
- metoda de calculare a valorii damage-ului (exista o sansa ca damage-ul dat sa fie dublu)
- metoda care returneaza damage-ul characterului
 Am utilizat sablonul Factory pentru a instantia characterele din lista contului.
Clasa CharacterFactory
 Folosita pentru a implementa sablonul Factory.
 Contine:
- un constructor
- metoda care instantiaza un obiect de tip Warriror, Mage sau Rogue, in functie de tipul de character primit ca 
parametru
Interfata Visitor
 Folosita pentru a implementa sablonul Visitor.
 Cotine:
- metoda care visiteza un obiect de tip Warrior
- metoda care visiteza un obiect de tip Rogue
- metoda care visiteza un obiect de tip Mage
- metoda care visiteza un obiect de tip Enemy
 Metodele sunt implementate in clasele Ice, Fire, Earth.
Clasa abstracta Spell
 Modeleaza abilitatile(ice, fire, earth).
 Implementeaza interfata Visitor.
 Contine:
- metoda abstracta getter care returneaza damage-ul abilitatii
- metoda abstracta getter care returneaza costul de mana al abilitatii
- metoda abstracta care viziteaza un obiect de tip Warrior
- metoda abstracta care viziteaza un obiect de tip Rogue
- metoda abstracta care viziteaza un obiect de tip Mage
- metoda abstracta care viziteaza un obiect de tip Enemy
Clasa Ice
 Extinde(mostenseste) clasa abstracta Spell.
 Contine:
- damage-ul
- costul de mana
- metoda getter care returneaza damage-ul abilitatii
- metoda getter care returneaza costul de mana al abilitatii
- un constructor
- metoda care viziteaza un obiect de tip Warrior, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Rogue, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Mage, acesta primind damage 0 de la abilitate, deoarece are protectie 
impotriva ice
- metoda care viziteaza un obiect de tip Enemy, acesta primind damage de la abilitate, daca nu are protectie 
imporiva ice
Clasa Fire
 Extinde(mostenseste) clasa abstracta Spell.
 Contine:
- damage-ul
- costul de mana
- metoda getter care returneaza damage-ul abilitatii
- metoda getter care returneaza costul de mana al abilitatii
- un constructor
- metoda care viziteaza un obiect de tip Warrior, acesta primind damage 0 de la abilitate, deoarece are protectie 
impotriva fire
- metoda care viziteaza un obiect de tip Rogue, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Mage, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Enemy, acesta primind damage de la abilitate, daca nu are protectie 
imporiva fire
Clasa Earth
 Extinde(mostenseste) clasa abstracta Spell.
 Contine:
- damage-ul
- costul de mana
- metoda getter care returneaza damage-ul abilitatii
- metoda getter care returneaza costul de mana al abilitatii
- un constructor
- metoda care viziteaza un obiect de tip Warrior, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Rogue, acesta primind damage 0 de la abilitate, deoarece are protectie 
impotriva earth
- metoda care viziteaza un obiect de tip Mage, acesta primind damage de la abilitate
- metoda care viziteaza un obiect de tip Enemy, acesta primind damage de la abilitate, daca nu are protectie 
imporiva earth
Enemy
 Extinde clasa abstracta Entity si implementeaza interfata CellElement.
 Contine:
 - damage-ul
 - o metoda care returneaza litera corespunzatoare clasei (tipului de CellElement)
 - o metoda care accepta un obiect de tip Visitor
 - constructorul care initializeaza si seteaza damage, health, mana, protectiile si abilitatile
 - metoda pentru pierderea de viata
 - metoda getter pentru calcularea damage-ului dat de entitate
 - metoda setter pentru setarea damage-ului
Enumeratia Enum
 Contine tipul de element ce se poate afla intr-o celula.
Inventory
 Contine:
- lista de potiuni
- greutatea maxima a inventarului
- numarul de bani
- metoda care adauga o potiune in lista
- metoda care elimina p potiune din lista
- metoda care calculeaza greutatea libera ramasa
Interfata Potion
 Contine:
- metoda pentru utilizarea potiunii (regenereaza viata sau mana in functie de tipul potiunii)
- metoda getter care returneaza pretul
- metoda getter care returneaza valorea de regenerare
- metoda getter care returneaza greutatea potiunii
Clasa HealthPotion
 Implementeaza interfata Potion
 Contine:
- pretul
- valorea de regenerare a health
- greutatea
- o metoda de folosire a potiunii daca este in inventarul characterului
- o metoda getter care returneaza pretul
- o metoda getter care returneaza valoarea de regenerare
- o metoda getter care returneaza greutatea
Clasa ManaPotion
 Implementeaza interfata Potion
 Contine:
- pretul
- valorea de regenerare a manei
- greutatea
- o metoda de folosire a potiunii daca este in inventarul characterului
- o metoda getter care returneaza pretul
- o metoda getter care returneaza valoarea de regenerare
- o metoda getter care returneaza greutatea
Clasa Shop
Implementeaza interfata CellElement.
 Contine:
- lista de potiuni (care este populata in momentul instantierii unui obiect nou)
- o metoda care returneaza litera corespunzatoare clasei (tipului de CellElement)
- un constructor care initializeza si adauga potiuni in lista de potiuni
- metoda pentru selectarea unei potiuni din lista si eliminarea ei
Clasa InvalidCommandException
 Arunca o exceptie de tipul InvalidCommandException cand primeste o comanda invalida.
Clasa Finish
 Implementeaza interfata CellElement.
 Contine:
- o metoda care returneaza litera corespunzatoare clasei (tipului de CellElement)
Clasa Empty
 Implementeaza interfata CellElement.
 Contine:
- o metoda care returneaza litera corespunzatoare clasei (tipului de CellElement)
