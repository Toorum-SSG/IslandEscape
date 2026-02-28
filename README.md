#  🏝️ Útěk z ostrova (IslandEscape)

Textová adventura napsaná v jazyce Java. Hráč se ocitne na tajemném ostrově a musí najít cestu k úniku – prozkoumat lokace, mluvit s postavami, plnit úkoly a nakonec se dostat do základny a vzlétnout helikoptérou.

---

## 📖 Příběh

Mladý kluk jménem **Tom** pochází z bohaté a vlivné rodiny. Ke svým 14. narozeninám dostává poukaz na vyhlídkovou plavbu luxusní jachtou po Tichém oceánu. Celý výlet byl ale pouze zástěrka – Tom byl prodán záhadnému bohatému člověku jako cenný kousek do jeho sbírky. Loď zasáhla náhlá řada přírodních katastrof a Tom samým zázrakem přežil. Probouzí se na nádherné písečné pláži plné života...

**Tvůj úkol:** Prozkoumej ostrov, navázej kontakty s místními postavami, splň jejich úkoly a unikni z ostrova!

---

## 🗂️ Struktura projektu

```
IslandEscape/
├── src/
│   ├── DataLoading/
│   │   ├── Main.java             # Vstupní bod aplikace
│   │   ├── Game.java             # Hlavní herní logika a smyčka
│   │   ├── GameData.java         # Datový kontejner pro herní data
│   │   ├── GameDataLoader.java   # Načítání dat z JSON souboru
│   │   └── JSONParser.java       # Vlastní parser JSON bez externích knihoven
│   ├── Dialogue/
│   │   ├── Dialogue.java         # Dialogový systém
│   │   ├── DialogueBranch.java   # Větve dialogu (podmíněné odpovědi)
│   │   └── DialogueOption.java   # Jednotlivé možnosti v dialogu
│   ├── commands/
│   │   ├── Command.java          # Rozhraní příkazu (vzor Command)
│   │   ├── CommandParser.java    # Parsování textových příkazů hráče
│   │   ├── CommandInvoker.java   # Spouštěč příkazů
│   │   ├── MoveCommand.java      # Pohyb mezi lokacemi
│   │   ├── PickUpCommand.java    # Sebírání předmětů
│   │   ├── DropCommand.java      # Vyhazování předmětů
│   │   ├── TalkCommand.java      # Rozhovor s postavou
│   │   ├── ExamineCommand.java   # Prozkoumání lokace
│   │   ├── ExamineItemCommand.java # Detaily předmětu
│   │   ├── ShowStatusCommand.java  # Zobrazení inventáře
│   │   ├── WriteNoteCommand.java   # Zapsání poznámky
│   │   ├── ReadNotesCommand.java   # Přečtení poznámek
│   │   ├── HelpCommand.java        # Nápověda
│   │   ├── MapCommand.java         # Zobrazení mapy
│   │   └── QuitCommand.java        # Ukončení hry
│   ├── gameEntities/
│   │   ├── Player.java           # Hráč (inventář, questy, stav)
│   │   ├── GameCharacter.java    # Herní postava (NPC)
│   │   ├── Location.java         # Herní lokace
│   │   ├── Item.java             # Předmět
│   │   ├── Inventory.java        # Správa inventáře
│   │   ├── Quest.java            # Herní úkol
│   │   └── Notebook.java         # Poznámkový blok hráče
│   └── test/
│       ├── IslandEscapeTest.java # Unit testy (JUnit Jupiter)
│       └── resources/
│           └── gamedata.json     # Herní data pro testy
├── out/
│   └── artifacts/
│       └── islandEscape_jar/
│           └── islandEscape.jar  # Spustitelný JAR soubor
├── gamedata.json                 # Herní data (lokace, předměty, postavy, questy)
└── README.md
```

---

## 🏗️ Architektura

Projekt využívá několik návrhových vzorů:

- **Command** – každý příkaz hráče (jdi, seber, mluv...) je samostatný objekt implementující rozhraní `Command`. `CommandParser` příkaz rozpozná, `CommandInvoker` ho provede.
- **Data Transfer Object** – třída `GameData` slouží jako přenosový objekt mezi načítáním dat a herní logikou.
- **Větvení dialogů** – `Dialogue` podporuje dynamické větve (`DialogueBranch`), které se aktivují podle stavu hráče (splněný quest, předmět v inventáři).

Veškerá herní data jsou oddělena od kódu a uložena v souboru `gamedata.json`, který se načítá vlastním parserem (`JSONParser`) bez použití externích knihoven.

---

## 🎮 Herní příkazy

| Příkaz | Popis |
|--------|-------|
| `jdi <lokace>` | Přesuň se do sousední lokace |
| `prohledej` | Prozkoumej aktuální lokaci |
| `prohlednout <předmět>` | Zobraz detaily předmětu |
| `seber <předmět>` | Seber předmět z lokace |
| `vyhod <předmět>` | Vyhoď předmět z inventáře |
| `mluv <postava>` | Promluv s postavou |
| `stav` | Zobraz obsah inventáře |
| `zapis` | Zapiš poznámku do bloku |
| `cti` | Přečti poznámky |
| `mapa` | Zobraz mapu ostrova |
| `pomoc` | Zobraz nápovědu |
| `konec` | Ukonči hru |

---

## 🗺️ Mapa ostrova

```
                      [ ZÁKLADNA ]
                           |
        [ CHRÁM ]     [ DŽUNGLE ]     [ JESKYNĚ ]
             |            / \            /
        [ OKRAJ ]--------+   +----------+
           /   \                \
    [ MOŘE ]   [ PLÁŽ ]       [ TÁBOR ]
```

---

## ▶️ Spuštění hry

### Požadavky
- Java 11 nebo novější (JRE)

### Spuštění z JAR souboru

JAR soubor se nachází ve složce `out/artifacts/islandEscape_jar/`. Před spuštěním se ujisti, že `gamedata.json` leží ve **stejné složce** jako JAR.

```bash
cd out\artifacts\islandEscape_jar
java -jar islandEscape.jar
```

Nebo s cestou k `gamedata.json` jako argumentem:
```bash
java -jar out\artifacts\islandEscape_jar\islandEscape.jar gamedata.json
```

#### Spuštění odkudkoliv přes CMD
1. Otevři složku s `.jar` v průzkumníku
2. Klikni do adresního řádku, napiš `cmd` a stiskni Enter
3. Spusť:
```bash
java -jar islandEscape.jar
```

### Kompilace ze zdrojových kódů
```bash
# Vytvoř výstupní složku
mkdir -p out

# Zkompiluj všechny zdrojové soubory
find src -name "*.java" > sources.txt
javac -d out @sources.txt

# Spusť hru
java -cp out DataLoading.Main
```

---

## 🧪 Unit testy

Projekt obsahuje testovací třídu `IslandEscapeTest` využívající framework **JUnit Jupiter (JUnit 5)**.

### Závislost

V IntelliJ IDEA přidej přes `File → Project Structure → Modules → Dependencies → + → Library → From Maven`:
```
org.junit.jupiter:junit-jupiter:5.10.0
```

### Spuštění testů v IntelliJ
Pravým kliknutím na `IslandEscapeTest.java` → **Run 'IslandEscapeTest'**

### Přehled testů

| # | Test | Testované metody |
|---|------|-----------------|
| 1 | `testInventory_addAndHasItem` | `Inventory.addItem()`, `hasItem()` |
| 2 | `testInventory_removeItem` | `Inventory.removeItem()`, `hasItem()` |
| 3 | `testPlayer_questLifecycle` | `Player.addQuest()`, `completeQuest()`, `hasQuest()`, `hasCompletedQuest()` |
| 4 | `testLocation_itemsAndAccessibility` | `Location.addItem()`, `getItem()`, `isAccessible()` |
| 5 | `testJSONParser_parse` | `JSONParser.parse()` |
| 6 | `testGameDataLoader_loadAllData` | `GameDataLoader.loadAllData()` |
| 7 | `testGame_initAndMove` | `Game.initializeGame()`, `Game.moveToLocation()` |

Celkem: **7 testů**, všechny prochází ✅

---

## 📄 Herní data (gamedata.json)

Veškerý obsah hry je definován v souboru `gamedata.json`. Lze zde upravovat:
- **lokace** – název, popis, propojení, podmínky přístupu
- **předměty** – název, popis, velikost v inventáři
- **postavy** – jméno, popis, dialogy s větvemi
- **questy** – název a popis úkolu
- **startLocation** – ID výchozí lokace
- **playerInventorySize** – kapacita inventáře hráče

---

## 👤 Autor

-Projekt vytvořen jako školní práce.
-Autor: Samuel Svoboda
-Jazyk: **Java**  
-Typ: **Textová adventura / konzolová hra**
