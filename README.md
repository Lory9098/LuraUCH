# UHC
---
## Informazioni Progetto
- Nome: LuraEvents
- Spigot Ver: 1.20.4
- GroupId: net.luramc
- Puoi utilizzare Gradle, Redis, MongoDB e MySQL

## Come funziona:
Un giocatore entrerà nel server tramite un NPC nella Hub e utilizzerà il comando /play, che aprirà una GUI con una mela d'oro per entrare nell'evento UHC (per il momento solo questo, in futuro ci saranno altri eventi). Successivamente, chi avrà un determinato permesso potrà utilizzare il comando /event start per avviare l'evento.

## Informazioni Evento:
Appena inizierà l'evento, i giocatori saranno divisi in 8 team (come nelle Bed Wars: rosso, blu, ecc.) e teletrasportati in 8 location casuali (configurabili con dei comandi, se non si vogliono random. Tieni conto che il mondo si dovrà resettare). Dopo 5 minuti dall'inizio, il bordo del mondo si restringerà (da 500x500 a 1x1) e dopo altri 10 minuti (quindi per un totale di 15 minuti) si attiverà il PvP.

L'ultimo team rimasto in vita vincerà l'evento. Infine, tutti i giocatori saranno riportati alla lobby (il proxy li smisterà successivamente).
