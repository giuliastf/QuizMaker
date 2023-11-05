README TEMA1 POO
IMBREA GIULIA-STEFANIA 321CB

=============================================================================
BONUS:

alte cazuri limita ?
1) Pentru -create-quizz as adauga cazul in care nu este furnizat niciun nume(lipseste -name)
2) Pentru -create-quizz as adauga cazul in care a fost data doar o intrebare sau niciuna(chestionarul
cu o intrebare este practic o intrebare si nu exista chestionare fara intrebari)
3) Pentru –get-quizz-details-by-id as adauga cazul in care lipseste quizz_id(-id)
4) Pentru –submit-quizz as adauga cazul in care lipseste idQuizz(-id)
5)Pentru –submit-quizz as adauga cazul in care user-ul nu a dat niciun raspuns
6)Pentru –delete-quizz-by-id as adauga cazul in care nu exista chesionar cu id-ul dat

Cum ați refactoriza comenzile?
PS: sper ca am inteles bine intrebarea
1) pentru metodele de tip findQuestionByName/ByUser/..., as face o singura metoda care sa primeasca ca parametru in plus
dupa cine se realizeaza cautarea
2)pt cele cu AddQuizz/AddQuestion/..., as face asemanator ca la 1)

=============================================================================
User.java

void CreateUser(final String[] arg)
- obtin arg[1] si il 'despart' (cu .split) pt a obtine 'my_username'
-verific daca exista deja un user cu numele "my_username'


UsersDatabase.java

int addUser(User user)
- adaug datele in fisierul "users.txt" sub forma: username,password.

int findUser(String username)
- parcurg fiecare linie din "users.txt" verific daca prima coloana(line.split(",")[0]) este egala cu username-ul => exista deja

int login(String username, String password)
- parcurg fiecare linie din "users.txt" cand dau de username pe prima coloana verific daca password este egal cu elementul de pe a doua coloana

=============================================================================
Answer.java
-contine clasa answer si constructor


AnswersDatabase.java
NB: “answers.txt” e sub forma de :“ansID,ansTEXT,ansPOINTS,ansVALUE”

int addAnswer(Answer answer)
-	Adaug o linie noua/raspuns in “answers.txt”
int Score(int nrQuestions, List<Integer> ans)
-	Calculez valoarea une intrebari in fct de nr de inrebari
-	Iau din “answers.txt” doar ansPOINTS de la rasounsurile care ma intereseaza

==============================================================================
Question.java
void CreateQuestion(final String[] arg)
ma folosesc de vectorul exist = {0, 0, 0, 0, 0} pt a verifica daca au fost dati toti parametri     exist = {u,p,text,type,ans}
-parcurg arg si actualizez vectorul exist.
-Pt Rapsunsuri o sa ma folosesc de un contor j.
-fiindca pentru a avea un raspuns complet am nevoie de text+value, ma folosesc de o variabila k, daca k este para, raspunsul e complet
-printare erori aferente + adaugare raspunsuri + intrebare cu metodele(AnswersDatabase.addAnswer, QuestionDatabase.addQuestion(q))

void GetQuestionIdByText(String[] arg)
-verific daca am primit corespunzator argumentele cu vectorul exist[]
-tot ok => QuestionDatabase.findQuestionByText(text) (ia linia cu  text)

void GetAllQuestions(String[] arg)
-ma folosesc de exist[]
-tot ok => QuestionDatabase.getQuestions(user)


QuestionDatabase.java
NB: “questions.txt” e sub forma de :
	“questID,username,password,text,type, ansID1, ansID2, … ”

int addQuestion(Question q) 
-Adaug o linie noua/intrebare in “questions.txt”

int findQuestionByText(String text)
-citesc din “questions.txt” si pentru fiecare linie compar coloanal 3 cu text
-ret 0  daca nu exista si ret questID daca l-am gasit

int findQuestionByID(int id)
-ca la metoda anterioara doar ca compar coloanal 0 cu id-ul dat
-ret1 daca exista, 0 altfel

String getQuestions(String user)
-parcurg fiecare linie din “questions.txt”,compar coloanal 1(user) cu param
-construiesc pe parcurs String-ul q, la final ii scot “, “ pt ca nu mai urmeaa nicio intrebare dupa el

String printQuestionByID(int id)
-parcurg fiecare linie din fisierul “questions.txt”
-ma opresc la linia cu id-ul corespunzator
-de aici preiau datele de la coloanele cu index-ul <= 5(answersID)
-deschid fisierul “answers.txt” si ma pozitionez pe linia cu id-ul corespunzator
-creez String ul q cu nume(col0) si id(col1)


Quizz.java
void CreateQuizz(String[] arg)
-ca la CreateQuestion folosesc vactorul exist pentru a memora ce parametri am primit(-u, -p, …)
-verific daca exista intrebarea
-o adaug in lista cu intrebari
-j=index pt intrebari

void GetQuizzByName(String []arg)
-daca toate datele au fost primite(exist) afiseaza quiz-ul folosind QuizzDatabase.findQuizzByName(name)

void GetAllQuizzes(String arg[])
-ca la anterioara metoda, dupa ce primesc User ul, afisez quiz-urile create de User-ul respectiv cu QuizzDatabase.getQuizzes(user)

void GetQuizzDetails(String[] arg)
-aceeasi poveste + folosesc metoda
-totul ok => QuizzDatabase.getDetails(quiz_id)

void SubmitQuizz(String [] arg)
-daca am primit toti parametrii(extist)
-verific daca quiz-ul a fost deja completat QuizzDatabase.CheckQuizzCompleted(quiz_id,user) 
-verific daca user-ul este si owner-ul quiz-ului
QuizzDatabase.CheckOwnerQuizz(quiz_id,user) 
-retin scorul = AnswersDatabase.Score(nOfQuestions,ans_id);
-creez fisierul “scores.txt” cu QuizzDatabase.AddScores(quiz_id, user, score)

void DeleteQuizz(String[]arg)
-verific parametri + QuizzDatabase.DeleteQuizz(quiz_id) 

void GetMySolutions(String []arg)
-verific parametri + QuizzDatabase.PrintSolutions(user)


QuizzDatabase.java
NB: “questions.txt” e sub forma de :
   quizzID,username,password,quizzNAME,nrOfQuestions,questID1@questID2@...

int addQuizz(Quizz quizz)
-creez fisierul “quizzes.txt” sub forma de mai sus

int findQuizzByName(String name)
int findQuizzById(int id)  => ca la Question

NB: “scores.txt” e sub forma de:
	quizzID, user, scorUser
String CheckQuizzCompleted(int id, String u)
-in fisierul “scores.txt” verific daca exista o linie care continue id si u

void AddScores(int id, String user, int score)
-creez un fisier “scores.txt” /adaug o linie 
-sub forma de id, user, score

int CheckOwnerQuizz(int id, String u)
-parcurg fiecare linie din “quizzes.txt” si verific daca in prima coloana este id-ul, iar in a doua user-ul u

String getQuizzes(String user) 
-parcurg fisierul “quizzes”, unde pe linie gasesc user-ul meu, printez folosind continutul liniei respective 

String getDetails(int id)
-parcurg “quizzes.txt”, ma opresc la linia cu id(coloanal 0) si folosesc QuestionDatabase.printQuestionByID pt a printa fiecare intrebare din quiz

int DeleteQuizz(int id)
-intr-un fisier “tmp.txt” copies liniile din “quizzes.txt” doar atunci cand nu contin id-ul dat ca parametru
-la final sterg “quizzes.txt” si redenumesc “temp.txt” ca “quizzes.txt”

String GetQuizName(String id)
-pe scurt, parcurg fisierul, la linia cu id dau ret la nume(col3)

String PrintSolutions(String user)
-citesc din “scores.txt” si construiesc String-ul q
-ma opresc la liniile ce contin user ul dat si in fct de id-ul quiz-ului(col0)printez numele quiz-ului GetQuizName(data[0])

























