package taken;

import java.time.LocalTime;
import java.util.LinkedHashSet;
import java.util.Random;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 *
 * @author some team
 */
public class main extends Application {
    
    final int n = 4; //cell's number
    Button[] but;  //action buttons      
    int count=0; //action counter
    
    BorderPane root=new BorderPane(); //root pane
    GridPane glay=new GridPane(); //pane for buttons
    MenuBar menu=new MenuBar(); //main menu
       
    Label win=new Label("Победа)"); //win label
    Label result=new Label(); //turn's result label
        
    
    private void razdat(){
    
            //randomizer
        Random rd=new Random(n*n-1);
            //meshok dlja raskladki
        LinkedHashSet lst=new LinkedHashSet(n*n);
                
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) {    
                int k = i * n + j; 
                if(k>=0 && k<n*n-1) {          
                    int t;
                        //random fullfill
                    LocalTime time=LocalTime.now();                    
                    do {                
                        t=rd.nextInt(time.getMinute()+16)%(n*n-1)+1;                        
                        if (!lst.contains(t)){
                            lst.add(t);
                            break;
                        }                
                    } while (true);    
                            
                        
                    but[k]=new Button(Integer.toString(t));
                    
                    but[k].setFont((Font.font("SanSerif",36)));
                                        
                    but[k].setOnAction(ButHandler);
                    but[k].setPrefSize(100.0, 100.0);                    
                    
                    glay.add(but[k], j, i);
                }
                else {
                    but[k]=new Button("");                    
                    but[k].setFont((Font.font("SanSerif",36)));
                    
                    but[k].setOnAction(ButHandler);
                    but[k].setPrefSize(100.0, 100.0);
                    
                    glay.add(but[k], i, j);                     
                }
            } 
        }
        but[n*n-1].setVisible(false);        
    }
    
    EventHandler<ActionEvent> ButHandler = new EventHandler<ActionEvent>(){
        
        @Override
        public void handle(ActionEvent ae) {
            String num;
                Button clik=(Button)ae.getSource();
          
                for(int i = 0; i < n; i++) { 
                    for(int j = 0; j < n; j++) {            
                        int k = i * n + j;            
                        if (clik==but[k]){               
                        
                        //sdvig vlevo
                            if ((k-1)>=0                               
                            && but[k-1].getText().equalsIgnoreCase("")
                            && k!=0
                            && k!=4
                            && k!=8
                            && k!=12
                            ){                    
                                num=clik.getText();
                                clik.setText("");
                                clik.setVisible(false);                    
                                but[k-1].setText(num);
                                but[k-1].setVisible(true);                                
                                but[k-1].requestFocus();
                            }
                        
                        //sdvig vpravo
                            if (k<15 
                            && but[k+1].getText().equalsIgnoreCase("")                    
                            && k!=3
                            && k!=7
                            && k!=11
                            && k!=15
                            ){
                                num=clik.getText();
                                clik.setText("");
                                clik.setVisible(false);
                                but[k+1].setText(num);
                                but[k+1].setVisible(true);
                                but[k+1].requestFocus();
                            }
                        
                        //sdvig vverh
                            if ((k-4)>=0 
                            && but[k-4].getText().equalsIgnoreCase("")                    
                            && k!=0
                            && k!=1
                            && k!=2
                            && k!=3
                            ){                    
                                num=clik.getText();
                                clik.setText("");
                                clik.setVisible(false);
                                but[k-4].setText(num);
                                but[k-4].setVisible(true);
                                but[k-4].requestFocus();
                            }
                        
                        //sdvig vniz                               
                            if ((k-4)<12                     
                            && k!=12
                            && k!=13
                            && k!=14
                            && k!=15
                            && but[k+4].getText().equalsIgnoreCase("")
                            ){
                                num=clik.getText();
                                clik.setText("");
                                clik.setVisible(false);
                                but[k+4].setText(num);
                                but[k+4].setVisible(true);
                                but[k+4].requestFocus();
                            }               
                        }                        
                    } 
                    
                }
                   
                check();                    
        }       
        
    };
    
    private void peretasovka(){        
         //randomizer
        Random rd=new Random(n*n-1);
            //meshok dlja raskladki
        LinkedHashSet lst=new LinkedHashSet(n*n);
                
        for(int i = 0; i < n; i++) { 
            for(int j = 0; j < n; j++) {    
                int k = i * n + j; 
                if(k<n*n-1) {          
                    int t;
                        //random fullfill
                    LocalTime time=LocalTime.now();                    
                    do {                                             
                        t=rd.nextInt(time.getSecond()+16)%(n*n-1)+1;
                        if (!lst.contains(t)){
                            lst.add(t);
                            break;
                        }                
                    } while (true);    
                                                 
                    but[k].setText(""+t);
                    but[k].setVisible(true);                   
                }
                else {
                    but[k].setText(""); 
                    but[k].setVisible(false);
                }
            } 
        }                           
    }
    
    private void check() {
                      
        int ry=0; //right step's counter
        final int butlength=but.length; 
                
        for (int y=0;y<butlength;y++){
           
            if (but[y].getText().endsWith(Integer.toString(y+1))) {
            
                ry++;                                   
            }                    
            else {
                count++;
                System.out.println("Еще не победа "+count+" ход, по порядку стоят "+ry+" кнопок");            
                break;
            }
        }
                
        if (ry==n*n-1){
            showWin();                    
        }           
        
    }
    
    private void showWin(){        
        
        glay.setVisible(false);
                        
                
        win.setFont(new Font("Sans Serif", 100));
        result.setText("сделано "+count+" ходов");
        count=0;
       
        root.setCenter(win);        
        root.setBottom(result);        
    }
    
    private void zanovo(){
        
        
        int k;
        for(int i=0;i<n;i++){
            for(int j=0;j<n;j++){
                k=i*n+j;                
                but[k].setVisible(true);
            }
        }
        glay.setVisible(true);
                
                
        root.setCenter(glay);        
       
        peretasovka();
    }
    
    @Override
    public void init(){
        //button's creation
        but=new Button[n*n];
        //Меню ИГРА
        Menu mFile=new Menu("Игра");
        MenuItem FExit=new MenuItem("Выход");            
        FExit.setOnAction((ActionEvent ev)->{            
            Platform.exit();
        });
                    
            //Меню РАЗДАТЬ
        MenuItem FRazdat=new MenuItem("Раздать заново");
        FRazdat.setOnAction((ActionEvent e)-> {
            peretasovka();
        });
            //Меню Новая игра
        MenuItem FZanovo=new MenuItem("Новая игра");
        FZanovo.setOnAction((ActionEvent e)-> {
            zanovo();
        });
            //Меню Победа
        Menu mWin=new Menu("Победа");
        MenuItem pwin=new MenuItem("показать");
        pwin.setOnAction((ActionEvent e)-> {            
            showWin();
        });
        
        mWin.getItems().add(pwin);
            //menu.add(mWin);
            
        mFile.getItems().add(FZanovo);
        mFile.getItems().add(FRazdat);            
        mFile.getItems().add(FExit);           
        menu.getMenus().add(mFile);
        menu.getMenus().add(mWin);
           
        glay.setAlignment(Pos.CENTER);
        glay.setHgap(10.0);
        glay.setVgap(10.0);        
        glay.setPrefSize(500.0, 500.0);
            
        razdat();
    }
    
    @Override
    public void start(Stage primaryStage) {        
        
        root.setTop(menu);        
        root.setCenter(glay);                
        Scene scene = new Scene(root, 500, 500);        
        
        primaryStage.setTitle("TakenTheGame");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {            
        launch(args);
    }
    
}
