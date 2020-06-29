import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.Lighting;
import javafx.scene.effect.MotionBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class Applications extends Application {
	Timer timer;
	boolean timerActive = false;
	int secs;
	int flagAmount;
	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Minesweeper");
		primaryStage.setWidth(250);
		primaryStage.setHeight(400);
		primaryStage.getIcons().add(new Image("file:textures/icon.png"));
		primaryStage.setResizable(false);
		setupMainMenu(primaryStage);
		primaryStage.setOnCloseRequest(event -> {
			try {
				timer.cancel();
	            timer.purge();
			}catch(Exception IllegalStateException) {
				
			}
		});
		primaryStage.show();
	}

	public void setupMainMenu(Stage primaryStage) {
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 100% 100%, slategrey, white);");
		
		ImageView logo = new ImageView("file:textures/icon.png");
		logo.setFitHeight(150);
		logo.setFitWidth(150);
		logo.relocate(45, 0);
		
		Text title = new Text(33, 170, "Minesweeper");
		title.setFont(new Font("Century", 30));
		title.setStyle(" -fx-fill: linear-gradient(from 0% 0% to 195% 200%, repeat, red 10%, LightSteelBlue 40%);\r\n" + 
				" -fx-stroke: black;\r\n" +
			    " -fx-stroke-width: 1px;");
		
		Slider difficulty = new Slider();
		difficulty.setMin(1);
		difficulty.setMax(3);
		difficulty.setValue(1);
		difficulty.setPrefWidth(155);
		difficulty.setShowTickLabels(true);
		difficulty.setShowTickMarks(true);
		difficulty.setMajorTickUnit(1);
		difficulty.setMinorTickCount(0);
		difficulty.setSnapToTicks(true);
		difficulty.relocate(47, 280);
		difficulty.setStyle("-fx-control-inner-background: grey;" +
							"-fx-shadow-highlight-color: linear-gradient(from 0% 0% to 195% 200%, repeat, green 0%, red 80%);" +
							"-fx-font-size: 12px;" +
							"-fx-focus-color: grey;" +
							"-fx-faint-focus-color: rgba(0,0,0,0);");
		StringConverter<Double> stringConverter = difficultyTicks();
		difficulty.setLabelFormatter(stringConverter);
		
		Button start = new Button("Start");
		start.relocate(90, 210);
		start.setPrefHeight(35);
		start.setPrefWidth(70);
		start.setStyle("-fx-font-family: Century;\r\n" +
				"-fx-background-color: green;\r\n"+
				"-fx-text-fill: white;" +
				"-fx-border-color: black;" +
				"-fx-border-width: 1;\r\n" 
				);
		EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	switch((int)difficulty.getValue()) {
                case 1:
                	startGame(primaryStage, 8, 8, 9);
             	   	break;
                case 2:
                	startGame(primaryStage, 16, 16, 39);
             	   	break;
                case 3:
                	startGame(primaryStage, 32, 32, 115);
             	   	break;
                }
            } 
        }; 
        
        Bloom bloom = new Bloom();
        Lighting reflect = new Lighting();
        
        start.addEventHandler(MouseEvent.MOUSE_ENTERED, 
        	    new EventHandler<MouseEvent>() {
        	        @Override public void handle(MouseEvent e) {
        	        	start.setStyle("-fx-font-family: Century;\r\n" +
        	    				"-fx-background-color: green;\r\n"+
        	    				"-fx-text-fill: white;" +
        	    				"-fx-cursor: hand;" +
        	    				"-fx-border-color: black;" +
        	    				"-fx-border-width: 1;\r\n" );
        	        	start.setEffect(bloom);
        	        }
        });
        
        start.addEventHandler(MouseEvent.MOUSE_EXITED, 
        	    new EventHandler<MouseEvent>() {
        	        @Override public void handle(MouseEvent e) {
        	        	start.setStyle("-fx-border-width: 0;\r\n" +
        	        			"-fx-font-family: Century;\r\n" +
        	    				"-fx-background-color: green;\r\n" +
        	        			" -fx-border-color:black;"+
        	    				"-fx-text-fill: white;" +
        	    				"-fx-border-color: black;" +
        	    				"-fx-border-width: 1;\r\n" );
        	        	start.setEffect(null);
        	        	
        	        }
        });
        
        start.addEventHandler(MouseEvent.MOUSE_PRESSED, 
        	    new EventHandler<MouseEvent>() {
        	        @Override public void handle(MouseEvent e) {
        	        	start.setEffect(reflect);
        	        }
        });
        
        start.addEventHandler(MouseEvent.MOUSE_RELEASED, 
        	    new EventHandler<MouseEvent>() {
        	        @Override public void handle(MouseEvent e) {
        	        	start.setEffect(bloom);
        	        }
        });
		
		start.setOnAction(event);
		
		pane.getChildren().addAll(logo, title, start, difficulty);
		Scene mainMenu = new Scene(pane);
		primaryStage.setScene(mainMenu);
		
	}
	
	public StringConverter<Double> difficultyTicks() {
		StringConverter<Double> stringConverter = new StringConverter<Double>() {

			@Override
			public String toString(Double n) {
				if (n == 1) return "Beginner";
                if (n == 2) return "Intermediate";
                if (n == 3) return "Advanced";
				return null;
			}

			@Override
			public Double fromString(String string) {
				if (string == "Beginner") return 1d;
                if (string == "Intermediate") return 2d;
                if (string == "Advanced") return 3d;
				return null;
			}
        };
        return stringConverter;
	}
	
	public void startGame(Stage primaryStage, int width, int height, int bombs) {
		Pane pane = new Pane();
		pane.setStyle("-fx-background-color: slategrey;");
		
		Board game = new Board(width, height);
		game.placeBombs(bombs);
		GridSpace[][] grid = game.getBoard();
		GridSpace[][] flags = game.getFlag();
    	flagAmount = bombs;
		
    	int rectSize = 0;
		int xDis = 35, yDis = 75;
		int xSmile = 145, ySmile = 15;
		int xTime = 90, yTime = 30;
		int xFlag = 215, yFlag = 30;
		if(width == 8)
			rectSize = 35;
		else if(width == 16) {
			rectSize = 25;
			xSmile = 205;
			xTime = 150;
			xFlag = 275;
		}
		else if(width == 32) {
			rectSize = 17;
			xDis = 35; yDis = 55;
			xSmile = 280; ySmile = 5;
			xTime = 225; yTime = 15;
			xFlag = 350; yFlag = 15;
			
		}
    	
		StackPane timerPane = new StackPane();
		timerPane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 1.5;");
		timerPane.setPadding(new Insets(0,2,2,2));
		Text timerText = new Text(getSecString());
		timerText.setFont(new Font(20));
		timerPane.getChildren().add(timerText);
		timerPane.relocate(xTime, yTime);
		
		timer = new Timer(); 
		secs = 0;
		TimerTask task = new TimerTask(){
			public void run(){
				if(secs > 998) {
					timer.cancel();
					timer.purge();
				}else {
					secs++;  
			        timerText.setText(getSecString());
				}
		    }
		};
		timerActive = true;
		timer.schedule(task,0,1000);
		
		StackPane flagPane = new StackPane();
		flagPane.setStyle("-fx-background-color: lightgrey; -fx-border-color: black; -fx-border-width: 1.5;");
		flagPane.setPadding(new Insets(0,2,2,2));
		Text flagText = new Text(getFlagString());
		flagText.setFont(new Font(20));
		flagPane.getChildren().add(flagText);
		flagPane.relocate(xFlag, yFlag);
		
    	Button smile = new Button();
    	MotionBlur light = new MotionBlur(-15, 5);
    	smile.setGraphic(new ImageView(new Image("file:textures/smiley.png")));
    	smile.setStyle("-fx-faint-focus-color: rgba(0,0,0,0);"+
    					"-fx-focus-color: grey;" + 
    					"-fx-border-color:black;" + 
    					"-fx-border-width: 1.5;");
    	smile.relocate(xSmile, ySmile);
    	smile.addEventHandler(MouseEvent.MOUSE_PRESSED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	smile.setGraphic(new ImageView(new Image("file:textures/smileyO.png")));
    		        	smile.setEffect(light);
    		        }
    		});
    	smile.addEventHandler(MouseEvent.MOUSE_RELEASED, 
    		    new EventHandler<MouseEvent>() {
    		        @Override public void handle(MouseEvent e) {
    		        	smile.setGraphic(new ImageView(new Image("file:textures/smiley.png")));
    		        	smile.setEffect(null);
    		        }
    		});
    	smile.setOnMouseClicked(event ->{
    		if(timerActive == true) {
    			timer.cancel();
    			timer.purge();
    		}
    		startGame(primaryStage, width, height, bombs);
    	});
    	
    	pane.getChildren().addAll(smile, timerPane, flagPane);
    	
		Rectangle[][] board = new Rectangle[width][height];
		for(int i = 0; i < width; i++) {
			for(int j = 0; j < height; j++) {
				Stop[] stops = new Stop[] { new Stop(0, Color.rgb(67, 67, 67)), new Stop(1, Color.WHITE)};
        	    LinearGradient lg = new LinearGradient(0, 0, 1, 0, true, CycleMethod.NO_CYCLE, stops);
        	    
				Rectangle rect = new Rectangle(rectSize,rectSize);
				rect.relocate(rectSize*i + xDis, rectSize*j + yDis);
				rect.setUserData(new int[]{i, j});
				rect.setFill(lg);
				rect.setStyle("-fx-stroke: black; -fx-stroke-width: 2;");
				
				board[i][j] = rect;
				
				Bloom bloom = new Bloom();
				bloom.setThreshold(0.2);
				rect.addEventHandler(MouseEvent.MOUSE_ENTERED, 
		        	    new EventHandler<MouseEvent>() {
		        	        @Override public void handle(MouseEvent e) {
		        	        	rect.setEffect(bloom);
		        	        }
		        });
				rect.addEventHandler(MouseEvent.MOUSE_EXITED, 
		        	    new EventHandler<MouseEvent>() {
		        	        @Override public void handle(MouseEvent e) {
		        	        	rect.setEffect(null);
		        	        }
		        });
				
				rect.addEventHandler(MouseEvent.MOUSE_PRESSED, 
		        	    new EventHandler<MouseEvent>() {
		        	        @Override public void handle(MouseEvent e) {
		        	        	smile.setGraphic(new ImageView(new Image("file:textures/smileyO.png")));
		    		        	smile.setEffect(light);
		        	        }
		        });
				rect.addEventHandler(MouseEvent.MOUSE_RELEASED, 
		        	    new EventHandler<MouseEvent>() {
		        	        @Override public void handle(MouseEvent e) {
		        	        	smile.setGraphic(new ImageView(new Image("file:textures/smiley.png")));
		    		        	smile.setEffect(null);
		        	        }
		        });
				
				int[] values = (int[]) rect.getUserData();
				rect.setOnMouseClicked(event ->{
		            if (event.getButton() == MouseButton.PRIMARY){
			            	switch(grid[values[1]][values[0]].getNum()) {
			            	case 0:
			            		floodFill(grid, board, values[1], values[0]);
			            		break;
			            	case 1:
			            		rect.setFill(new ImagePattern(new Image("file:textures/one.png")));
			            		break;
			            	case 2:
			            		rect.setFill(new ImagePattern(new Image("file:textures/two.png")));
			            		break;
			            	case 3:
			            		rect.setFill(new ImagePattern(new Image("file:textures/three.png")));
			            		break;
			            	case 4:
			            		rect.setFill(new ImagePattern(new Image("file:textures/four.png")));
			            		break;
			            	case 5:
			            		rect.setFill(new ImagePattern(new Image("file:textures/five.png")));
			            		break;
			            	case 6:
			            		rect.setFill(new ImagePattern(new Image("file:textures/six.png")));
			            		break;
			            	case 7:
			            		rect.setFill(new ImagePattern(new Image("file:textures/seven.png")));
			            		break;
			            	case 8:
			            		rect.setFill(new ImagePattern(new Image("file:textures/eight.png")));
			            		break;
			            	case 9:
			            		rect.setFill(new ImagePattern(new Image("file:textures/bomb.png")));
			            		for(int x = 0; x < grid.length; x++) {
			            			for(int y = 0; y < grid[x].length; y++) {
			            				board[x][y].setOnMouseClicked(null);
				            			if(grid[x][y].getNum() == 9)
				            				board[y][x].setFill(new ImagePattern(new Image("file:textures/bomb.png")));
				            		}
			            		}
			            		smile.setGraphic(new ImageView(new Image("file:textures/smileyDead.png")));
			            		timer.cancel();
			                    timer.purge();
			            		break;
			            	}
			            	rect.setOnMouseClicked(null);
			            	grid[values[1]][values[0]].setExplored(true);
		            } else if (event.getButton() == MouseButton.SECONDARY){
		            	if(flagAmount >= -1) {
		            		if(flags[values[1]][values[0]].getFlag() == true) {
			            		rect.setFill(lg);
			         		    flags[values[1]][values[0]].setFlag(false);
			         		    flagAmount++;
			         		    flagText.setText(getFlagString());
			            	}
			            	else {
			            		if(flagAmount != -1) {
				            		rect.setFill(new ImagePattern(new Image("file:textures/flag.png")));
				            		flags[values[1]][values[0]].setFlag(true);
				            		flagAmount--;
				            		flagText.setText(getFlagString());
			            		}
			            	}
		            	}
		            }
		            checkWin(game, smile);
		        });
				
				pane.getChildren().add(rect);
			}
		}
		
		Scene inGame = new Scene(pane);
		primaryStage.setScene(inGame);
		switch(width) {
		case 8:
			primaryStage.setWidth(350);
			primaryStage.setHeight(425);
			break;
		case 16:
			primaryStage.setWidth(475);
			primaryStage.setHeight(550);
			break;
		case 32:
			primaryStage.setWidth(625);
			primaryStage.setHeight(650);
			break;
		}
		
	}
	
	public void checkWin(Board game, Button smile ) {
		GridSpace[][] grid = game.getBoard();
		GridSpace[][] flag = game.getFlag();
		boolean win = true;
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[0].length; j++) {
				if(grid[i][j].getBomb() == true && flag[i][j].getFlag() != true ) {
					win = false;
					break;
				}
				if(grid[i][j].getExplored() == false && grid[i][j].getBomb() != true) {
					win = false;
					break;
				}
			}
		}
		if(win == true) {
			smile.setGraphic(new ImageView(new Image("file:textures/smileyCool.png")));
			timer.cancel();
	        timer.purge();
		}
	}
	
	public void floodFill(GridSpace[][] grid, Rectangle[][] board, int x, int y) {
        if (x >= 0 && x < grid.length && y >= 0 && y < grid[x].length) {
            if (grid[x][y].getExplored() != true && grid[x][y].getNum() == 0) {
            	grid[x][y].setExplored(true);
                board[y][x].setFill(Color.rgb(187, 187, 187));
            	if(x == 0) {   
                    floodFill(grid, board, x+1, y);   
                    floodFill(grid, board, x, y-1);    
                    floodFill(grid, board, x, y+1);
            	}
            	else if(x == grid.length -1) {
            		floodFill(grid, board, x-1, y);      
                    floodFill(grid, board, x, y-1);    
                    floodFill(grid, board, x, y+1);
            	}
            	else if(y == 0) {
            		floodFill(grid, board, x-1, y);    
                    floodFill(grid, board, x+1, y);     
                    floodFill(grid, board, x, y+1);
            	}
            	else if(y == grid[0].length -1) {
            		floodFill(grid, board, x-1, y);    
                    floodFill(grid, board, x+1, y);   
                    floodFill(grid, board, x, y-1);    
            	}
            	else {
                    floodFill(grid, board, x-1, y);    
                    floodFill(grid, board, x+1, y);   
                    floodFill(grid, board, x, y-1);    
                    floodFill(grid, board, x, y+1);
            	}
                
                if(x != 0 && y != 0 && grid[x-1][y-1].getNum() > 0 && grid[x-1][y-1].getNum() < 9) {
                	grid[x-1][y-1].setExplored(true);
                	board[y-1][x-1].setFill(new ImagePattern(new Image("file:textures/"+grid[x-1][y-1].numToString()+".png")));
                	board[y-1][x-1].setOnMouseClicked(null);
                }
                
                if(x != 0 && grid[x-1][y].getNum() > 0 && grid[x-1][y].getNum() < 9) {
                	grid[x-1][y].setExplored(true);
                	board[y][x-1].setFill(new ImagePattern(new Image("file:textures/"+grid[x-1][y].numToString()+".png")));
                	board[y][x-1].setOnMouseClicked(null);
                }
                
                if(x != 0 && y != grid[0].length-1 && grid[x-1][y+1].getNum() > 0 && grid[x-1][y].getNum() < 9) {
                	grid[x-1][y+1].setExplored(true);
                	board[y+1][x-1].setFill(new ImagePattern(new Image("file:textures/"+grid[x-1][y+1].numToString()+".png")));
                	board[y+1][x-1].setOnMouseClicked(null);
                }
                
                if(y != 0 && grid[x][y-1].getNum() > 0 && grid[x][y-1].getNum() < 9) {
                	grid[x][y-1].setExplored(true);
                	board[y-1][x].setFill(new ImagePattern(new Image("file:textures/"+grid[x][y-1].numToString()+".png")));
                	board[y-1][x].setOnMouseClicked(null);
                }
                
                if(y != grid[0].length-1 && grid[x][y+1].getNum() > 0 && grid[x][y+1].getNum() < 9) {
                	grid[x][y+1].setExplored(true);
                	board[y+1][x].setFill(new ImagePattern(new Image("file:textures/"+grid[x][y+1].numToString()+".png")));
                	board[y+1][x].setOnMouseClicked(null);
                }
                
                if(x != grid.length-1 && y != 0 && grid[x+1][y-1].getNum() > 0 && grid[x+1][y-1].getNum() < 9) {
                	grid[x+1][y-1].setExplored(true);
                	board[y-1][x+1].setFill(new ImagePattern(new Image("file:textures/"+grid[x+1][y-1].numToString()+".png")));
                	board[y-1][x+1].setOnMouseClicked(null);
                }
                
                if(x != grid.length-1 && grid[x+1][y].getNum() > 0 && grid[x+1][y].getNum() < 9) {
                	grid[x+1][y].setExplored(true);
                	board[y][x+1].setFill(new ImagePattern(new Image("file:textures/"+grid[x+1][y].numToString()+".png")));
                	board[y][x+1].setOnMouseClicked(null);
                }
                
                if(x != grid.length-1 && y != grid.length-1 && grid[x+1][y+1].getNum() > 0 && grid[x+1][y+1].getNum() < 9) {
                	grid[x+1][y+1].setExplored(true);
                	board[y+1][x+1].setFill(new ImagePattern(new Image("file:textures/"+grid[x+1][y+1].numToString()+".png")));
                	board[y][x+1].setOnMouseClicked(null);
                }
            }
        }
    }
	
	public String getSecString() {
		String s = "";
		if(secs < 10)
			s = "00"+secs;
		else if(secs < 100)
			s = "0"+secs;
		else if(secs >= 100)
			s = Integer.toString(secs);
		return s;
	}
	
	public String getFlagString() {
		String s = "";
		if(flagAmount+1 < 10)
			s = "00"+ (flagAmount+1);
		else if(flagAmount+1 < 100)
			s = "0" + Integer.toString(flagAmount+1);
		else
			s = Integer.toString(flagAmount+1);
		return s;
	}
	
}
