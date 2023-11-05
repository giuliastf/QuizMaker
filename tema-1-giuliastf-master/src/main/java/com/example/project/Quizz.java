package com.example.project;
import java.io.*;
import java.lang.*;
import java.util.*;

public class Quizz {
    public static int answerID=1;
    public static int QuizzID=1;
    public final int id;
    User u;
    String name;
    List <Integer> questions_id;

    public Quizz(User u, String name, List<Integer> qid ){
        this.id = QuizzID;
        QuizzID++;
        answerID = 1;
        this.u = u;
        this.name =name;
        this.questions_id = qid;
    }

    public static void CreateQuizz(String[] arg) {
        char []exist = {0,0,0};//{u,p,name}
        String user = "", psw="", name ="";
        int q_id = 1;
        int j=1;
        List <Integer> a = new ArrayList<Integer>();
        for(int i=1; i < arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if(arg[i].split(" ")[0].equals("-name")) {
                exist[2]=1;
                name = arg[i].split("'")[1].replace("'", "");
            }
            if(arg[i].split(" ")[0].equals("-question-"+j)) {
                q_id = Integer.parseInt(arg[i].split(" ")[1].replace("'", ""));
                //trb sa verific daca exista intebarea
                if (QuestionDatabase.findQuestionByID(q_id) == 0) {
                    System.out.println("{'status':'error','message':'Question ID for question " + q_id + " does not exist'}");
                    return;
                } else { //adaug intrebarea
                    a.add(q_id);
                }
                j++;
            }
        }
        //verific daca am primit parametrii
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if(QuizzDatabase.findQuizzByName(name) != -1) {  // a ret un id
            System.out.println("{'status':'error','message':'Quizz name already exists'}");
            return;
        }
        if(a.size() > 10) {
            System.out.println("{'status':'error','message':'Quizz has more than 10 questions'}");
            return;
        }
        User u = new User(user, psw);
        Quizz quizz = new Quizz(u,name,a);
        QuizzDatabase.addQuizz(quizz);
        System.out.println("{'status':'ok','message':'Quizz added succesfully'}");
    }

    public static void GetQuizzByName(String []arg){
        char []exist = {0,0,0};//u p name
        String user="", psw="", name="";
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-name")) { // -text 'intrebare 1'
                exist[2] = 1;
                name = arg[i].split("'")[1].replace("'", "");
            }
        }
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if(exist[2] == 0) {
            System.out.println("{'status':'error','message':'No quizz name provided'}");
            return;
        } else if(QuizzDatabase.findQuizzByName(name) == -1) {
            System.out.println("{'status':'error','message':'Quizz does not exist'}");
            return;
        }
        else {
            System.out.println("{'status':'ok','message':'"+ QuizzDatabase.findQuizzByName(name)+"'}");
        }
    }
    public static void GetAllQuizzes(String arg[]){
        char []exist = {0,0};
        String user="", psw="";
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
        }
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        System.out.println("{'status':'ok','message':'["+ QuizzDatabase.getQuizzes(user)  +"]'}");
    }
    public static void GetQuizzDetails(String[] arg){
        char []exist = {0,0,0};
        String user="", psw="";
        int quiz_id=0;
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-id")) {
                exist[2] = 1;
                quiz_id = Integer.parseInt(arg[i].split(" ")[1].replace("'", ""));
            }
        }
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if(exist[2]==0){
            System.out.println("{'status':'error','message':'No identifier was provided'}");
            return;
        }
        System.out.println("{'status':'ok','message':'" + QuizzDatabase.getDetails(quiz_id) + "'}");
    }
    public static void SubmitQuizz(String [] arg) {
        char []exist = {0,0,0};
        String user="", psw="";
        int quiz_id=0;
        int j=1;
        List<Integer> ans_id = new ArrayList<>();
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-quiz-id")) {
                exist[2] = 1;
                quiz_id = Integer.parseInt(arg[i].split(" ")[1].replace("'", ""));
            }
            if(arg[i].split(" ")[0].equals("-answer-id-"+(j))){
                ans_id.add(Integer.parseInt(arg[i].split(" ")[1].replace("'", "")));
                j++;
            }

        }
        int nOfQuestions = QuizzDatabase.findQuizzById(quiz_id);
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if(exist[2]==0){
            System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
            return;
        }
        else if( nOfQuestions == -1){
            System.out.println("{'status':'error','message':'No quiz was found'}");
            return;
        }
        if(QuizzDatabase.CheckQuizzCompleted(quiz_id,user).equals("True")){
            System.out.println("{'status':'error','message':'You already submitted this quizz'}");
            return;
        }
        if(QuizzDatabase.CheckOwnerQuizz(quiz_id,user) == 1){
            System.out.println("{'status':'error','message':'You cannot answer your own quizz'}");
            return;
        }
        int score = AnswersDatabase.Score(nOfQuestions,ans_id);
        System.out.println("{'status':'ok','message':'"+(int)score+" points'}");
        QuizzDatabase.AddScores(quiz_id, user, score);
    }

    public static void DeleteQuizz(String[]arg){
        char []exist = {0,0,0};
        String user="", psw="";
        int quiz_id=0;
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-id")) {
                exist[2] = 1;
                quiz_id = Integer.parseInt(arg[i].split(" ")[1].replace("'", ""));
            }
        }
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }

        if(exist[0] == 1) {
            if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
            }
        }
        if(exist[2]==0){
            System.out.println("{'status':'error','message':'No quizz identifier was provided'}");
            return;
        }

        if(QuizzDatabase.DeleteQuizz(quiz_id) == 0){
            System.out.println("{'status':'error','message':'No quiz was found'}");
            return;
        }else {
            System.out.println("{'status':'ok','message':'Quizz deleted successfully'}");
            return;
        }
    }

    public static void GetMySolutions(String []arg){
        char []exist = {0,0};//u p name
        String user="", psw="";
        for(int i=0; i<arg.length; i++){
            if (arg[i].split(" ")[0].equals("-u")) {
                exist[0] = 1;
                user = arg[i].split(" ")[1].replace("'", "");
            }
            if (arg[i].split(" ")[0].equals("-p")) {
                exist[1] = 1;
                psw = arg[i].split(" ")[1].replace("'", "");
            }
        }
        if(exist[0] == 0 || exist[1]==0){
            System.out.println("{'status':'error','message':'You need to be authenticated'}");
            return;
        }
        else if (UsersDatabase.login(user, psw) == 0 ) {
                System.out.println("{'status':'error','message':'Login failed'}");
                return;
        }
        System.out.print("{ 'status' : 'ok', 'message' : '"+ QuizzDatabase.PrintSolutions(user)  + "'}");
    }

}
