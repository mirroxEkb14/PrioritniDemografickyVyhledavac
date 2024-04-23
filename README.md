# Prioritní Demografický Vyhledávač

Projekt je rozšířením [DemografickéhoVyhledávače](https://github.com/mirroxEkb14/DemografickyVyhledavac) o prioritní frontu:
  * Priorita je definována (i) počtem obyvatel nebo (ii) názvem obce a je ji možné měnit za běhu aplikace;
  * Prioritní fronta je jako ADS realizována v samostatné třídě **AbstrHeap** a je vybudována jako levostranná halda na poli;

![alt form-image](/resources/form.png)

  * V rámci projektu je navržené a implementované rozhraní odpovídající operacím:
    * **Vybuduj*** - vybuduje požadovanou prioritní frontu, vstupní parametr pole obcí;
    * **Reorganizace*** - přebuduje prioritní frontu dle požadované priority;
    * **Zruš** - zrušení prioritní fronty;
    * **JePrázdný** - test naplněnosti prioritní fronty;
    * **Vlož** - vložení prvku do prioritní fronty;
    * **OdeberMax** - odebraní prvku z prioritní fronty s maximální prioritou;
    * **ZpřístupniMax** - zpřístupnění prvku z prioritní fronty s maximální prioritou;
    * **Vypiš** - vypíše prvky prioritní fronty (využívá iterátor do šířky i do hloubky);

Aplikace, nechť umožňuje:
  * pomocí formulářové aplikace volat výše uvedené metody;
  * zadávání vstupních dat z *klávesnice*, ze *souboru* a z *generátoru*, výstupy z programu, nechť je možné zobrazit na *obrazovce* a uložit do *souboru*;

Pozn.: jelikož operace **Vybuduj** a **Reorganizace** nejsou standartními operacemi ADS halda, je navržené vhodné řešení pro jejich realizaci.

## Vybudování levostranné haldy (Heap sort)

![alt heap-sort-image-1](/resources/heap-sort-1.png)
![alt heap-sort-image-2](/resources/heap-sort-2.png)
