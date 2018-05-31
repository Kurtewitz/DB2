
package de.h_da.fbi.db2.stud;

import de.h_da.fbi.db2.stud.entity.Answer;
import de.h_da.fbi.db2.stud.entity.Category;
import de.h_da.fbi.db2.stud.entity.Game;
import de.h_da.fbi.db2.stud.entity.Player;
import de.h_da.fbi.db2.stud.entity.Question;

import de.h_da.fbi.db2.tools.CsvDataReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

/**
 * Main Class.
 * @version 0.1.1
 * @since 0.1.0
 * @author A. Hofmann
 * @author B.-A. Mokroß
 */
public class Main {
    
    private List<Category> categories;
    private List<Question> questions;
    
    
    final EntityManagerFactory emf;
    final EntityManager emanag;
    EntityTransaction etrans;
    
    private Player player;
    
    
    public Main() {
        
        categories = new ArrayList<Category>();
        questions = new ArrayList<Question>();
        
        emf = Persistence.createEntityManagerFactory("Datenbanken2PU");
        emanag = emf.createEntityManager(); // Session wird erzeugt
        etrans = null;
        
        
        try {
            etrans = emanag.getTransaction();
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        
    }
    
    public void setOrCreatePlayer(String playerName) {
        
        try {
            etrans.begin();
            
            String playerExistsCheckQuery = "select p.playerId from Player p where p.playerName = '" + playerName.trim() + "'";
            
            List<Integer> result = emanag.createQuery(playerExistsCheckQuery, Integer.class).getResultList();
            
            //create new user or load an existing one
            Player plr = (result == null || result.isEmpty()) ? new Player(playerName) : emanag.find(Player.class, result.get(0));
            
            this.player = plr;
            
            emanag.persist(plr);
            
            etrans.commit();
            
        } catch (RuntimeException exc) {
            if ( etrans != null && etrans.isActive() ) {
                etrans.rollback();
            } // Rollback der Transaktion
            throw exc;
        } finally {
            //emanag.close(); // Session wird geschlossen
        }
        
        
    }
    
    
    public void loadCsvFile() {
        
        try {
            etrans.begin(); // Start der Transaktion
            // Laden, Verändern, Speichern von Daten …
        
            final HashMap<String, Category> categories = new HashMap<String, Category>();

            System.out.println("Die richtige Antwort ist mit einem \"!\" gekennzeichnet");
        
            try {
                //Read default csv
                final List<String[]> defaultCsvLines = CsvDataReader.read();



                for (int rowNr = 1; rowNr < defaultCsvLines.size(); rowNr++) {

                    final String[] row = defaultCsvLines.get(rowNr);

                    //name of category
                    final String cat = row[7];

                    //create a new category, if necessary
                    if (!categories.containsKey(cat)) {
                        final Category category = new Category(cat);
                        //category.setCategory(cat);
                        categories.put(cat, category);
                        emanag.persist(category);
                    }



                    //read question variables
                    final int qid = Integer.parseInt(row[0]);
                    final String qstr = row[1];

                    //create question with variables
                    final Question question = new Question(qid, qstr, categories.get(cat));
                    



                    //create 4 (by default wrong) answers
                    for (int answerNr = 0; answerNr < 4; answerNr++) {
                        final Answer answer = new Answer(answerNr, row[2 + answerNr], question);
                        question.addAnswer(answer);
                        emanag.persist(answer);
                    }

                    emanag.persist(question);
                    //set one answer to correct, keep in mind answers nr 1-4, but index 0-3
                    final int correctIndex = Integer.parseInt(row[6]) - 1;
                    question.getAnswers().get(correctIndex).setCorrect(true);


                    //add question to the category
                    categories.get(cat).addQuestion(question);


                }

                //Read (if available) additional csv-files and default csv-file
                final List<String> availableFiles = CsvDataReader.getAvailableFiles();
                for (final String availableFile: availableFiles) {
                    final List<String[]> additionalCsvLines = CsvDataReader.read(availableFile);
                }
            } catch (URISyntaxException use) {
                System.out.println(use);
            } catch (IOException ioe) {
                System.out.println(ioe);
            }
        
        
        
            for (final Category c : categories.values()) {

                System.out.println(c.getCategory() + ": ");

                for (final Question q : c.getQuestions()) {

                    System.out.println(q.getQuestion() + ": ");

                    for (final Answer a : q.getAnswers()) {

                        System.out.print(a.getNr() + ". " + a.getAnswer() + " ");
                        if (a.isCorrect()) {
                            System.out.print("!   ");
                        } else {
                            System.out.print("   ");
                        }
                    }
                    System.out.println("");
                }

            }
            
            etrans.commit();
        
        } catch (RuntimeException exc) {
            if ( etrans != null && etrans.isActive() ) {
                etrans.rollback();
            } // Rollback der Transaktion
            throw exc;
        } finally {
            //emanag.close(); // Session wird geschlossen
        }
        
    }
    
    public void playGame() {
        
        try {
            etrans.begin(); // Start der Transaktion
            
            
            String getCategoriesQuery = "select c from Category c";
            List<Category> allCategories = emanag.createQuery(getCategoriesQuery, Category.class).getResultList();
            
            for (Iterator i = allCategories.iterator(); i.hasNext();) {
                Category category = (Category) i.next();
                int id = category.getId();
                String categoryStr = category.getCategory();
                
                System.out.println(id + ". " + categoryStr);
                
            }
            
            System.out.println("Select at least 2 categories by typing their numbers with a [space] as separator.");
            
            byte[] userInputCategories = new byte[15];
            
            System.in.read(userInputCategories);
            
            String userInputCategoriesString = new String(userInputCategories).trim();
            
            String[] categoriesStrings = userInputCategoriesString.split(" ");
            
            categories.clear();
            
            for(String cat : categoriesStrings) {
                
                int chosenID = Integer.parseInt(cat.trim());
                
                Category category = emanag.find(Category.class, chosenID);
                
                categories.add(category);
            }
            
            int questionMaximum = 0;
            for(Category cat : categories) {
                questionMaximum = Math.max(questionMaximum, cat.getQuestions().size());
            }
            
            System.out.println("How many questions from each category do you want to answer? (max. " + questionMaximum + "):");
            
            byte[] userInputQuestions = new byte[5];
            System.in.read(userInputQuestions);
            
            int questionsPerCategory = Integer.parseInt(new String(userInputQuestions).trim());
            
            questions.clear();
            for(Category cat : categories) {
                List<Question> chosenQuestions = cat.getNQuestions(questionsPerCategory);
                questions.addAll(chosenQuestions);
            }
            
            Collections.shuffle(questions);
            Game game = new Game(player, categories, questions, LocalDateTime.now(), Instant.now());
            
            
            for(Question curQuestion : questions) {
                System.out.println(curQuestion.getQuestion());
                System.out.println("1. " + curQuestion.getAnswers().get(0).getAnswer() + "\t \t \t "
                        + "2. " + curQuestion.getAnswers().get(1).getAnswer());
                System.out.println("3. " + curQuestion.getAnswers().get(2).getAnswer() + "\t \t \t "
                        + "4. " + curQuestion.getAnswers().get(3).getAnswer());
                
                byte[] userInputAnswer = new byte[10];
                System.in.read(userInputAnswer);
                
                int chosenAnswerNr = Integer.parseInt(new String(userInputAnswer).trim()) - 1;
                
                Answer chosenAnswer = curQuestion.getAnswers().get(chosenAnswerNr);
                game.addAnswer(chosenAnswer);
                
                if(chosenAnswer.isCorrect()) {
                    game.increaseCorrect();
                    System.out.println("Correct!");
                }
                else {
                    System.out.println("Wrong!");
                }
                
                
            }
            game.setFinish(Instant.now());
            
            player.addGame(game);
            
            emanag.persist(game);
            
            
            etrans.commit();
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        } catch (RuntimeException exc) {
            if ( etrans != null && etrans.isActive() ) {
                etrans.rollback();
            } // Rollback der Transaktion
            throw exc;
        } finally {
            //emanag.close(); // Session wird geschlossen
        }
        
        
    }
    
    public void stop() {
        emanag.close();
        
    }
    
    
    
    /**
     * Main Method and Entry-Point.
     * @param args Command-Line Arguments.
     */
    public static void main(final String[] args) {
        
        try {
        
            Main main = new Main();
            //main.loadCsvFile();


            byte[] userInput = new byte[10];
            int playerNameLength = 0;

            
            System.out.println("Enter player name (first 10 characters are submited): ");
            
            playerNameLength = System.in.read(userInput);
            
            main.setOrCreatePlayer(new String(userInput).trim());
            
            main.playGame();
            
            main.stop();
            
            
            
            
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
