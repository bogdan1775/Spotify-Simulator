# Proiect GlobalWaves  - Etapa 3
# Croitoru Constantin-Bogdan
# Grupa 324CA

#Pentru rezolvare am folosit rezolvarea mea de la etapa2, insa am luat functia de removeAlbum din rezolvarea oficila a etapei2 deoarece a mea nu mergea pe toate cazurile. (Mentionez ca  etapa2 a fost rezolvata cu rezolvarea oficiala de la etapa1).

###Explicare Cod:
In clase *Admin* am adaugat o lista de tipul clasei *Song* numita "songsHistory" pentru a retine si cantecele care sunt sterse pentru a le putea afisa la comanda wrapped.
Pentru fiecare user am introdus in clasa *Player* noi campuri precum: o lista numita "songsListened" de tipul *Song* care retine toate cantecele ascultate, "songsFree" o lista care retine cantele ascultate cand nu este premium, "songsPremium" o lista care retine cantecele ascultate cand este premium, "episodesListened" o lista cu episoadele ascultate si "hostListened" o lista cu numele fiecarui host pe care l-a ascultat.

**Main**
In main se selecteaza ce comanda se executa. Pe langa comenzile de la etapa 2 am implementat comenzile:

1.**wrapped** - aceasta afiseaza anumite statistici in functie de tipul utilizatorului. Pentru fiecare tip de user mi-am creat cate o clasa: **ShowWrappedUser**, **ShowWrappedArtist** si  **ShowWrappedHost**,  cu campurile necesare pentru afisare si le-am introdus in pachetul *display*.
a)**User** -pentru un user normal am metoda *wrappedUser* care calculeaza statisticile. Pentru determinarea:
	1)topSongs se parcurg toate melodiile ascultate de acesta, se adauga cate un listenings la melodie pentru fiecare aparitie a acesteia. Se creaza o lista de tipul *SongWrapped*, se ordoneaza lista in functie de listenings si se selecteaza primele 5.
	2)topAlbum se parcurge lista de melodii ascultate si creste numarul de listenings al albumului din care face parte melodia. Se creeaza a lista de tipul *AlbumWrapped* care contine toate albumele cu un numar de ascultari mai mare de 0, iar pentru albumele cu acelasi nume se aduna numarul de ascultari, apoi se ordoneaza si se selecteaza primele 5. 
	3)topEpisodes se ceeaza o lista de tipul "EpisodeWrapped", se parcurge fiecare episod din lista de episoade ascultate si se adauga in noua lista daca nu exista deja, altfel creste numarul de ascultari. Se ordoneaza lista si se afiseaza primele 5.
	4)topArtist pentru fiecare artist se parcurge lista cu cantele ascultate de user si numara cate melodii ii arpartin artistului din cele asculate, si se adauga intr-o lista de tipul "Artist". Se ordonea aceasta lista si se afiseaza primii 5.
	5)topGenre- se creeaza o lista cu toate genurile, se retine cate melodii apartin fiecarui gen, se ordoneaza si afiseaza primele 5.

b)**Artist** - pentru un artist am metoda *wrappedArtist* care calculeaza statisticile. Pentru determinarea:
	1)topSong se parcurge lista *songsHstory* care retine toate cantele, se creeaza o lista de tipul *SongWrapped* care retine melodiile artistului si numarul de ascultari a fiecarei melodii. Se ordoneaza si se afisea primele 5.
	2)topAlbum se parcurge lista de melodii ascultate si creste numarul de listenings al albumului din care face parte pentru fiecare aparitie a melodiei in lista. Se creeaza a lista de tipul *AlbumWrapped* care contine toate albumele cu un numar de ascultari mai mare ca 0 si care apartin artistului, apoi se ordoneaza si se selecteaza primele 5.
	3)topFans se creeaza o lista de tipul *Fan* care retine fanii unui artist. Pentru determinarea fanilor, pentru fiecare user se parcurge lista de cantece ascultate si se numara cate cantece de la acest artist a ascultat. Daca a ascultat ce putin unul, se adauga in lista cu fani. Se ordoneaza lista si se afiseaza primii 5.
	4)listeners este dimensiunea listei cu fani.
	
c)**Host** - pentru un host am metoda *wrappedHost* care calculeaza statisticile. Pentru determinarea: 
	1)listeners se parcurge pentru fiecare user lista de episoade ascultate, si daca a ascultat cel putin un episod de-al host-ului, numarul de listeners creste.
	2)topEpisode se creeaza o lista de tipul *EpisodeWrapped* in care se retin episoadele hostului si numarul de listenings. Pentru fiecare utilizator se parcurge lista cu episoade asculate. Daca episodul apartine hostului se aduga in lista daca nu exista, sau se creste numarul de listenings daca exista in lista.
	
2.**updateRecommendations** - in functie de ce tip este recomandarea se face o anumita actiune. Daca este "random song" se alege random un cantec din cantecele care apartin aceluiasi cantec ca cel care este load in momentul comenzii, si se retine in lista de recomandari. Daca este "random playlist" se creeaza un playlist random cu melodiile din top3 gender si se adauga in lista playlistRecommendation, iar pentru "fans playlist" se creeza un playlist pe baza recomadarile celor top5 fans, iar playlistul este adaugat in lista playlistRecommendation.

3.**loadRecommendations** - se face load la ultima recomandare facuta. Daca utilizatoru este offline sau daca nu are nicio recomandare, nu se poate realiza comanda.

4**previousPage** - utilizatorul navigheza la pagina anterioara. Inainte de a incerca, se verifica daca poate sa navigheze, iar in caz pozitiv se duce pe pagina anterioara.

5.**nextPage** - utilizatorul navigheza pe pagina urmatoare. Inainte de a incerca, se verifica daca poate sa navigheze, iar in caz pozitiv se duce pe pagina urmatoare.

6.**buyMerch** - cumpara un anumit merch al unui artist. Initial se verifica sa se afle pe pagina buna, adica a artistului, in caz pozitiv, se cumpara merch-ul, fiind adaugat in lista de merch-uri cumparate.

7.**seeMerch** - aceasta afiseaza toate merch-urile cumparate de un utilizator. Se parcurge lista cu merch-urile cumparate si se afiseaza merch-urile.

8.**subscribe** - prin aceasta comanda un utilizator se poate abona sau dezabona de la un artist sau un host. Se verifica daca se afla pe pagina potrivita, iar apoi daca se afla in lista de abonati ai acestuia se dezaboneaza, iar daca nu se aboneaza.

9.**getNotifications** - aceata comanda afiseaza notificarile unui utilizator. Fiecare utilizator are o lista de tipul String cu notificarile acestuia. In momentul in care se realizeaza aceasta comanda, se afiseaza notificarile, iar lista se goleste.

10.**buyPremium** - prin aceasta comanda utilizatorul devine premium. Inainte de a deveni, se verifica daca utilizatorul este deja premium, iar in caz ca nu acesta devine.

11.**cancelPremium** - prin aceasta comanda utilizatorul renunta la premium. Inainte de a renunta se verifica daca este premium. Cand se renunta la premium calculez revenue pentru fiecare melodie asculata in timp ce a fost un user premium.

12.**adBreak** - aceasta incarca dupa melodia curenta din player o reclama. Pentru calcularea monetizarii, se parcurge lista de melodii care au fost ascultate cand userul nu era premium. La fiecare aparitie a unui adBreak se numara cate melodii au fost pana la el se se calculeaza revenue pentru fiecare melodie.

13**endProgram** - aceasta afiseaza toti artistii cu care s-a interactionat si anumite statistici despre ei precum merchRevenue, songRevenue, ranking si mostProfitableSong. Se creeaza o lista cu artistii cu care s-a interectionat, se determina cat are merchRevenue si songRevenue pentru fiecare artist, se determina cea mai profitabila melodie a acestui, se se ordonea artistii in functie de venitul total, adica cel de la merchRevenue + songEevenue, iar ranking-ul este dat de ordinea din lista.



###Design pattern
**Visitor pattern**
Pentru camanda "prinCurrentPage* mi-am facut un Visitor pattern care in metoda visit afiseza pagina corespunzatoare, adica daca e artist pagina artistului, daca e host pagina hostului, iar daca e user pagina corespunzatoare lui adica HomePage sau LikedContentPage in cazul in care nu se afla pe pagina unui artist sau host, iar daca se afla se afiseaza pagina artistului/hostului.

**Singleton pattern**
In clasa LibraaryInput mi-am facut un Singleton ca libraria sa fie initializa o singura data.





