package com.example.project;

import java.util.Arrays;
public class Question {
    public String text;
    public String type;
    User user;
    Answer[] ans;
    public static int questionID = 1;
    public final int id;

    public Question(User user, String text, String type, Answer[] ans) {
        this.id = questionID;
        ++questionID;
        this.user = user;
        this.text = text;
        this.type = type;
        this.ans = ans;
    }

    static void CreateQuestion(final String[] arg) {
        char[] exist = {0, 0, 0, 0, 0}; //u, p, text, type, ans
        int correct = 0;
        String user = "", psw = "", text = "", type = "";
        String[] ans_text = new String[5];
        int j = 0;
        int k = 0;
        int[] ans_value = {-1, -1, -1, -1, -1};
        for (int i = 1; i < arg.length; i++) {
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-text")) { // -text 'intrebare 1'
                exist[2] = 1;
                text = arg[i].split("'")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-type")) {
                exist[3] = 1;
                type = arg[i].split(" ")[1].replace("'", "");
            }

            if (arg[i].split(" ")[0].equals("-answer-" + (j + 1))) {
                if (j > 4) {
                    System.out.println("{'status':'error','message':'More than 5 answers were submitted'}");
                    return;
                }
                if (Arrays.stream(ans_text).anyMatch(arg[i].split(" ")[1].replace("'", "")::equals)) {
                    System.out.println("{'status':'error','message':'Same answer provided more than once'}");
                    return;
                }
                ans_text[j] = arg[i].split("'")[1].replace("'", "");
                k++;//k=1
            }
            if (arg[i].split(" ")[0].equals("-answer-" + (j + 1) + "-is-correct")) {
                ans_value[j] = Integer.parseInt(arg[i].split(" ")[1].replace("'", ""));
                k++;
                if (ans_text[j] == null || ans_value[j] == -1) {
                    break;   // nu exista text sau value k=1
                }
                if (ans_value[j] == 1)
                    correct++;
                exist[4] = 1;
                j++;

            }
        }
        //nu am username sau parola
        if (exist[0] == 0 || exist[1] == 0) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        if (exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if (exist[2] == 0) { //nu am text
            System.out.println("{'status':'error','message':'No question text provided'}");
            return;
        } else if (QuestionDatabase.findQuestionByText(text) != 0) {
            System.out.println("{'status':'error','message':'Question already exists'}");
            return;
        }
        if (exist[3] == 0) { //nu am type
            System.out.println("{'status':'error','message':'No type text provided'}");
            return;
        } else if (type.equals("single") && correct > 1) {
            System.out.println("{'status':'error','message':'Single correct answer question has more than one correct answer'}");
            return;
        }

        if (k % 2 != 0) { //k=1; nu am raspunsuri complete(text+value)
            if (ans_text[j] == null) {
                System.out.println("{'status':'error','message':'Answer " + (j + 1) + " has no answer description'}");
                return;
            } else if (ans_value[j] == -1) {
                System.out.println("{'status':'error','message':'Answer " + (j + 1) + " has no answer correct flag'}");
                return;
            }
        }
        if (j == 0) {
            System.out.println("{'status':'error','message':'No answer provided'}");
            return;
        }
        if (j == 1) {
            System.out.println("{'status':'error','message':'Only one answer provided'}");
            return;
        }
        //daca am ajuns aici inseamna ca am tot ceea ce trebuie
        int m = 0;//de la 0 la j answers
        double ans_points[] = new double[j];
        int wrong = j - correct;
        for (int i = 0; i < j; i++) {
            if (ans_value[i] == 1)
                ans_points[i] =  1 / (double) correct;
            else
                ans_points[i] = -1/ (double)wrong;
        }
        User u = new User(user, psw);
        Answer[] a = new Answer[j];
        for (int i = 0; i < j; i++) { //creezi fiecare raspuns si l pun in vector
            a[i] = new Answer(ans_text[i], ans_value[i], ans_points[i]);//(i+1,ans_text[i],ans_value[i]);
            AnswersDatabase.addAnswer(a[i]);
        }
        Question q = new Question(u, text, type, a);
        QuestionDatabase.addQuestion(q);
        System.out.println("{'status':'ok','message':'Question added successfully'}");
    }

    static void GetQuestionIdByText(String[] arg) {
        char[] exist = {0, 0, 0};
        String user = "", psw = "", text = "";
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-text")) { // -text 'intrebare 1'
                exist[2] = 1;
                text = arg[i].split("'")[1].replace("'", "");
            }
        }
        if (exist[0] == 0 || exist[1] == 0) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        if (exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if (exist[2] == 0) {
            System.out.println("{'status':'error','message':'No question text provided'}");
            return;
        } else if (QuestionDatabase.findQuestionByText(text) == 0) {
            System.out.println("{'status':'error','message':'Question does not exist'}");
            return;
        } else {
            System.out.println("{'status':'ok','message':'" + QuestionDatabase.findQuestionByText(text) + "'}");
        }
    }

    public static void GetAllQuestions(String[] arg) {
        char[] exist = {0, 0};
        String user = "", psw = "";
        for (int i = 0; i < arg.length; i++) {
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
        }
        if (exist[0] == 0 || exist[1] == 0) {
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        if (exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        System.out.println("{'status':'ok','message':'[" + QuestionDatabase.getQuestions(user) + "]'}");
    }
}
