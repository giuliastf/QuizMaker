package com.example.project;

import java.io.*;

public class QuestionDatabase {
    /* id, user, psw, text, type, ans1, ans2    */
    public static int addQuestion(Question q) {
        try (FileWriter fw = new FileWriter("questions.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.print(q.id + ","+ q.user.username + "," + q.user.getPassword() + "," + q.text + "," + q.type);
            for(int i = 0; i < q.ans.length; i++ ) {
                out.print(",");
                out.print(q.ans[i].id );
            }
            out.println();
        } catch (IOException e) { }
        return 0;
    }
    /*returneaza id-ul intrebarii(>1) sau 0*/
    public static int findQuestionByText(String text) {
        try (BufferedReader br = new BufferedReader(new FileReader(("questions.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[3].equals(text))
                    return Integer.parseInt(data[0]);
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return 0; //nu am gasit
    }
    /* returneaza 1(daca am gasit intrebarea) sau 0 */
    public static int findQuestionByID(int id){
        try (BufferedReader br = new BufferedReader(new FileReader(("questions.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(Integer.parseInt(data[0]) == id) {
                    return 1;
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return 0;
    }

    /*returneaza un string cu formatul cerut*/
    public static String getQuestions(String user) {
        String q = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(("questions.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(data[1].equals(user)){
                    q += "{\"question_id\" : \"" + + Integer.parseInt(data[0]) + "\", \"question_name\" : \"" +data[3]+ "\"}, ";
                }
            }
            q = q.substring(0,q.length()-2);
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return q;
    }
    /* afiseaza o intrebare avand id */
    public static String printQuestionByID(int id){
        String q = new String();
        try (BufferedReader br = new BufferedReader(new FileReader(("questions.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                if(Integer.parseInt(data[0]) == id) {
                    q+="{\"question-name\":\"" + data[3] + "\", \"question_index\":\""+ data[0] +"\", \"question_type\":\"" +data[4] + "\", \"answers\":\"[";
                    for(int i=5; i<data.length; i++) {
                        try (BufferedReader br1 = new BufferedReader(new FileReader(("answers.txt")))){
                            String line1;
                            while ((line1 = br1.readLine()) != null) {
                                String[] data1 = line1.split(",");
                                if(data1[0].equals(data[i])) //linia cu ansID -ul meu
                                    q += "{\"answer_name\":\"" +data1[1]+"\", \"answer_id\":\""+data1[0]+"\"}, ";
                            }
                        } catch (FileNotFoundException e) {
                        } catch (IOException e) {
                        }
                    }
                    q = q.substring(0, q.length()-2);
                    q+="]\"}";
                }
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        return q;
    }
}
