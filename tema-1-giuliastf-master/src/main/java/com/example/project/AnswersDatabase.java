package com.example.project;
import java.io.*;
import java.util.List;

public class AnswersDatabase {
    /* adaug un raspuns in fiserul "answers.txt" : id, text, points, value */
    public static int addAnswer(Answer answer) {
        try (FileWriter fw = new FileWriter("answers.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(answer.id + "," + answer.text + ","  + answer.points + "," + answer.value);
        } catch (IOException e) { }
        return 0;
    }
    /*calculeaza scorul la un quizz */
    static public int Score(int nrQuestions, List<Integer> ans){
        double question_value = 100/(double)nrQuestions;
        double tot = 0.0;
        try (BufferedReader br = new BufferedReader(new FileReader(("answers.txt")))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                for(int i=0; i<ans.size();i++)
                    if(ans.get(i) == Integer.parseInt(data[0]) )
                        tot = tot + Double.parseDouble(data[2]); //adun punctele de la fiecare raspuns
            }
        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }
        long score = Math.round(tot*question_value);
        Math.round(score);
        if(score<0)
            score=0;
        return (int)score;
    }
}
