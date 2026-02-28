# 🏝️ Útěk z ostrova (IslandEscape)

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
│       └── GameTest.java         # Unit testy (bez externích závislostí)
├── gamedata.json                 # Herní data (lokace, předměty, postavy, questy)
├── IslandEscape.jar              # Spustitelný JAR soubor
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
```bash
java -jar IslandEscape.jar
```

Pokud je soubor `gamedata.json` na jiném místě, předej cestu jako argument:
```bash
java -jar IslandEscape.jar /cesta/k/gamedata.json
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

Projekt obsahuje vlastní testovací třídu `GameTest` bez závislosti na externích frameworcích (JUnit apod.).

### Spuštění testů
```bash
java -cp IslandEscape.jar test.GameTest
```

### Přehled testů

| # | Test | Testované metody |
|---|------|-----------------|
| 1 | `testInventory` | `Inventory.addItem()`, `removeItem()`, `hasItem()`, `hasSpace()` |
| 2 | `testPlayerQuests` | `Player.addQuest()`, `completeQuest()`, `hasQuest()`, `hasCompletedQuest()` |
| 3 | `testLocation` | `Location.addItem()`, `getItem()`, `removeItem()`, `addCharacter()`, `getCharacter()` |
| 4 | `testLocationAccessibility` | `Location.isAccessible()`, `setRequiresQuest()` |
| 5 | `testJSONParser` | `JSONParser.parse()` |
| 6 | `testDialogueOption` | `DialogueOption.getText()`, `getResponse()`, `setEndsGame()`, `isVictory()` |
| 7 | `testPlayerGameEnd` | `Player.endGame()`, `hasGameEnded()`, `hasWon()` |

Celkem: **33 asercí**, všechny prochází ✅

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

## 👤 Autor: Samuel Svoboda

Projekt vytvořen jako školní práce.  
Jazyk: **Java**  
Typ: **Textová adventura / konzolová hra**
