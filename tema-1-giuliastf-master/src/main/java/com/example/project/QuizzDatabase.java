package com.example.project;
import java.io.*;
import java.util.*;
public class QuizzDatabase {
    //id, user, psw, numeQuizz, nr_intreb, q1@q2@q3
    public static int addQuizz(Quizz quizz){
        try (FileWriter fw = new FileWriter("quizzes.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(quizz.id+","+quizz.u.username + "," + quizz.u.getPassword()+","+quizz.name+","+quizz.questions_id.size()+",");//+","+quizz.is_completed);
            String buf = new String();
            for(int i = 0; i < quizz.questions_id.size(); i++ ) {
                buf += (quizz.questions_id.get(i)).toString();
                buf += "@";
            }
            buf = buf.substring(0,buf.length()- 1);
            out.println(buf);
        } catch (IOException e) { }
        return 0;
    }
    /*returneaza index-ul quizz-ului sau -1*/
    static int findQuizzByName(String name) {
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[3].equals(name)) //nume
                    return Integer.parseInt(data[0]); //id
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return -1;
    }

    static int findQuizzById(int id) {
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(Integer.parseInt(data[0]) == id) //nume
                    return Integer.parseInt(data[4]); //nr intrebari
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return -1;
    }
    /*"True" daca user a completat chestionarul cu id*/
    public static String CheckQuizzCompleted(int id, String u){
        try (BufferedReader br = new BufferedReader(new FileReader(("scores.txt")))) {
            String line;
            String []user_score;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(Integer.parseInt(data[0]) == id && data[1].equals(u)){
                            return "True";
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return "False";
    }
    /* scores.txt: idQUIZ, user, score */
    public static void AddScores(int id, String user, int score){
        try (FileWriter fw = new FileWriter("scores.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
             out.println(id+","+user + "," + score);
        } catch (IOException e) { }
    }
    public static int CheckOwnerQuizz(int id, String u){
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                String[] users = data[data.length-1].split("@");
                if(Integer.parseInt(data[0]) == id && data[1].equals(u)){
                    return 1; //daca user-ul este owner-ul quizzului
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return 0;
    }
    public static String getQuizzes(String user) {
        String q = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[1].equals(user)){
                    q = q + "{\"quizz_id\" : \"" + + Integer.parseInt(data[0]) + "\", \"quizz_name\" : \"" +data[3]+ "\", \"is_completed\" : \"" + CheckQuizzCompleted(Integer.parseInt(data[0]),user) +"\"}, ";//data[data.length-1]
                }
            }
            q = q.substring(0,q.length()-2);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return q;
    }
    public static String getDetails(int id){
        String q = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            q = "[";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if(Integer.parseInt(data[0]) == id){
                    String []questions = data[5].split("@"); //coloana 5 contine intrebarile
                    for(int i=0; i<questions.length; i++){
                        q = q+ QuestionDatabase.printQuestionByID(Integer.parseInt(questions[i])) + ", ";
                    }
                }
            }
            q = q.substring(0,q.length()-2);
            q += "]";
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return q;
    }
    public static int DeleteQuizz(int id){
        int ok=0;
        File tmp = new File("temp.txt"); //copiez doar liniile care trebuie
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")));
            FileWriter fw = new FileWriter(tmp, true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw))
        {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(Integer.parseInt(data[0]) != id){
                    out.println(line); //nu e linia cu quizz-ul=>o copiez
                }else
                    ok = 1;
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        File old = new File("quizzes.txt");
        if(old.delete())
            tmp.renameTo(old);
        return ok;
    }
    public static String GetQuizName(String id){
        try (BufferedReader br = new BufferedReader(new FileReader(("quizzes.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[0].equals(id)){
                        return data[3];
                    }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return null;
    }
    public static String PrintSolutions(String user){
        // [{"quiz-id" : "1", "quiz-name" : "Chestionarul 1", "score" : "100", "index_in_list" : "1"}]
        String q = new String();
        int i=0;
        try (BufferedReader br = new BufferedReader(new FileReader(("scores.txt")))) {
            String line;
            q = "[";
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[1].equals(user)){
                        i++;
                        q = q+ "{\"quiz-id\" : \"" + data[0] +"\", \"quiz-name\" : \"" + GetQuizName(data[0]) + "\", \"score\" : \""+ data[2] + "\", \"index_in_list\" : \""+ i +"\"}, ";
                }
            }
            q = q.substring(0,q.length()-2);
            q += "]";
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return q;
    }
}
