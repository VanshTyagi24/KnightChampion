package ca.sheridancollege.controllers;


import java.util.ArrayList;
import java.util.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ca.beans.KnightBean;
import ca.beans.Moves;
import ca.beans.Path;
import ca.sheridancollege.database.DatabaseAccess;
import ca.sheridancollege.database.TheDatabase;


@Controller
public class ChampController {
	private static int positionI=0;
	private static int positionJ=0;
	private static int moveCounter = 1;
	private static boolean finish = false;
	private static double total = 0;
	private static int trial = 0;
	private static String name;
	private static double average;
	private static int[][] board = new int[8][8];
	    @Autowired
		private DatabaseAccess da;
		@GetMapping("/")
		public String goHome(Model model) {
			
			//da.addKnight();
			model.addAttribute("knightbean",new KnightBean());
			return "home.html";
			
		}
		@PostMapping("/insertKnight")
	    public String index(Model model, @ModelAttribute KnightBean knightbean) {
			//knightbean.setAverage(20);
			if(knightbean.getPositionX()>=0 && knightbean.getPositionX()<8 
					&& knightbean.getPositionY()>=0 && knightbean.getPositionY()<8
					&&knightbean.getTrial()>0) {
				TheDatabase.movesList.clear();
				TheDatabase.pathList.clear();
				total=0;
				chess(knightbean);
				calculateAverage();
				knightbean.setAverage(average);
				da.addKnight(knightbean);
		    	System.out.print(knightbean.getAverage());
		    	return "result.html";
			}
			else
				model.addAttribute("knightbean", new KnightBean());
				return "invalid.html";
			
	    }
		
		public static void initializeBoard(int[][] board) {
			for (int i = 0; i < 8; i++) {
	            for (int j = 0; j < 8; j++) {
	                board[i][j]=0;
	            }
	            
	        }
		}
		
		public static void priority(int[][] board){
	        
	        int[] hor = {-1, -2, -2, -1, 1, 2, 2, 1}; 
	        int[] ver = {2, 1, -1, -2, -2, -1,1, 2};
	        for (int i = 0; i < 8; i++) {
	           for (int j = 0; j < 8; j++) {
	               int count=0;
	                  for (int k = 0; k < 8; k++) {
	                    int a = i + hor[k];
	                    int b = j + ver[k];
	                    
	                    if (a >= 0 && b >= 0 && a < 8 && b < 8 ){
	                
	                    count++;
	                    }
	                  }
	                 board[i][j]=count;
	          }
	       }
	   }
		
		public static void move(int x,int y, int[][] chess, int[][] priority){
	        
	        int[] hor = {-1, -2, -2, -1, 1, 2, 2, 1}; 
	        int[] ver = {2, 1, -1, -2, -2, -1,1, 2};
	        
	        ArrayList<Integer> validH= new ArrayList<>(); //holds the value of i for valid positions
	        ArrayList<Integer> validV= new ArrayList<>(); //holds the value of j for valid positions
	         ArrayList<Integer> pri= new ArrayList<>();   //holds the value of priority for valid positions
	        for (int i = 0; i < 8; i++) {
	            int a = x + hor[i];
	            int b = y + ver[i];
	            
	            try{
	                if (a >= 0 && b >= 0 && a < 8 && y < 8 && chess[a][b] == 0){
	                
	                validH.add(hor[i]);
	                validV.add(ver[i]);
	                pri.add(priority[a][b]);
	                
	            }
	            
	            }
	            catch(Exception e){
	                
	            }
	        }
	            
	            if(validH.isEmpty()){
	                finish = true;
	            }
	            else{
	                
	                int small=Collections.min(pri);  //checks smallest priority value
	                ArrayList<Integer> pool = new ArrayList<>();
	                for (int i = 0;i<pri.size();i++){
	                       if(pri.get(i).equals(small)) {
	                           
	                    pool.add(i); //adds the positions of smallest value from pri arraylist
	               }
	        }
	                
	                
	                int random = (int)(Math.random()*pool.size());  //random number 0 to pool.size-1
	            //moving knight to next position
	            moveCounter++;
	            positionI = x + validH.get(pool.get(random));
	            positionJ = y + validV.get(pool.get(random));
	            chess[positionI][positionJ] = moveCounter;
	                
	                
	        }
		}
		
		public static void chess(KnightBean knight) {
	    
	    		trial = knight.getTrial();
	        	positionJ = knight.getPositionX();
	            positionI = knight.getPositionY();
	             name = knight.getKnightName();
	    	
	    	
	    	int[][] priorityBoard = new int[8][8];
	    		priority(priorityBoard);
	    		for(int i=0; i < trial;i++) {
	    			
	    			board = new int[8][8];
	    			
	                initializeBoard(board);
	                positionJ = knight.getPositionX();
		            positionI = knight.getPositionY();
	                
	                
	                moveCounter = 1;
	                
	                board[positionI][positionJ]=1;
	                while(finish==false){
	                    move(positionI,positionJ,board,priorityBoard);
	                }
	                Moves move = new Moves();
	                move.setMoves(moveCounter);
	                move.setTri(i+1);
	                
	                TheDatabase.movesList.add(move);
	                
	                Path path = new Path();
	                path.setKnightPath(board);
	                path.setTri(i+1);
	                
	                TheDatabase.pathList.add(path);
	               
	                total+=moveCounter;
	                finish = false;
	    		}      
	    }
		
		public static void calculateAverage() {
			average = total/trial;
		}
		
		@GetMapping("/viewAverage")
		public String average(Model model) {
			model.addAttribute("average", average);
			return "viewAverage.html";
		}
		
		@GetMapping("/viewMove")
		public String viewMove(Model model) {
		
             model.addAttribute("kMoves", TheDatabase.movesList);
             return "viewMoves.html";
			
			
		}
		
		@GetMapping("/viewPath")
		public String viewPath(Model model) {
		
             model.addAttribute("paths", TheDatabase.pathList);
             return "viewPath.html";
			
			
		}
		
		
		
		
	
}
