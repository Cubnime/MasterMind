import java.util.ArrayList;

// HelloMVC: a simple MVC example
// the model is just a counter 
// inspired by code by Joseph Mack, http://www.austintek.com/mvc/

// View interface
interface IView {
	public void updateView();
}

public class Model {	
	// the data in the model, just a counter
	private int counter;	
	// all views of this model
	private ArrayList<IView> views = new ArrayList<IView>();
	

    /*
     * Jotto members
     */
    
    public static int NUM_LETTERS = 5;
    public static final String[] LEVELS = {
    "Easy", "Medium", "Hard", "Any Difficulty"};
    
    private Word secret;
    private WordList dict=new WordList("words.txt");

    private String[] guesses = new String[10]; 
    private int[] guessExact = new int[10];
    private int[] guessPartial = new int[10];

    private int attempts = 0;
    private String myGuess;

    private String[] matches;
    private String[] myPastGuess;

    private ArrayList<String> messageLog;

    private boolean needToStartNewGame = true;
    private int[] hint = new int[26];
    private boolean wantHint = false;
     /*-------------METHODS---------------*/
     //ctor
     Model() {
        //right now, start new game upon launch
        this.newGame();
     }

    public void newGame(int diff) {
    	//1. reset guesses
    	this.guesses = new String[10];
        this.guessExact = new int[10];
        this.guessPartial = new int[10];
        this.matches = new String[10];
        this.myPastGuess = new String[10];
        this.messageLog = new ArrayList<String>();
        this.needToStartNewGame = false;
    	this.attempts = 0;
        this.myGuess = "";
        this.wantHint = false;

        for (int i = 0; i < 26; i++) {
            this.hint[i]=0;
        }

    	//2. pick new word
    	this.secret = this.dict.randomWord(diff);
    	System.out.println("Model: newGame - word is " + this.secret.getWord() + " difficulty = " + this.secret.getDifficulty());

        notifyObservers();
    }
    public void newGame() {
    	//1. reset guesses
    	this.guesses = new String[10];
    	this.attempts = 0;
        this.myGuess = "";
        this.matches = new String[10];
        this.myPastGuess = new String[10];
        this.messageLog = new ArrayList<String>();
        this.needToStartNewGame = false;
        this.wantHint = false;

        for (int i = 0; i < 26; i++) {
            this.hint[i]=0;
        }
    	//2. pick new word
    	this.secret = this.dict.randomWord();
    	System.out.println("Model: newGame - word is " + this.secret.getWord());

        notifyObservers();
    }

    // not using this one
    public void insertGuess(String g) {
        this.secret = new Word("MANLY", 1);
        this.myGuess = g;

        notifyObservers();
    }
    public void insertGuessChar(char c) {

        if (this.myGuess == null || this.myGuess.isEmpty()) {
            this.myGuess = String.valueOf(c);
        } else if (this.myGuess.length() < 5) {
            this.myGuess+= c;
        } else {
            System.out.println("Model: insertGuess - can't insert char");
            this.addToLog("Can't insert char into guess!");
            notifyObservers();
            return;
        }
        System.out.println("Model: insertGuess - insert char " + String.valueOf(c) + " myGuess is "+ this.myGuess);
        this.findMatches();
        
        notifyObservers();
    }

    public void removeGuessChar() {
        if (this.myGuess != null && !this.myGuess.isEmpty() && this.myGuess.length() > 0) {
            this.myGuess = this.myGuess.substring(0, this.myGuess.length() -1);
            this.findMatches();
        }
        notifyObservers();
    }

    // finds all valid entries for current myGuess
    public void findMatches() {
        this.matches = this.dict.findMatches(myGuess);
    }

    public void guess() {
    	//1. check if guess is valid EXPAND THIS!!!
        if (this.myGuess == null || this.myGuess.isEmpty()) {
            return;
        }
        if (this.myGuess.length() != 5) {
    		String m = " " + "," + " " + "," + " " + "," + " " + "," + "Invalid Entry!";
            this.messageLog.add(m);
            notifyObservers();
            return;
    	}

        if (!this.dict.findWord(this.myGuess)) {
            String m = " " + "," + " " + "," + " " + "," + " " + "," + "Word Not in Dictionary";
            this.messageLog.add(m);
            notifyObservers();
            return;
        }

        //find matches
        int exact = 0;
        int partial = 0;
        int[] secretMatched = new int[this.NUM_LETTERS];
        int[] guessMatched = new int[this.NUM_LETTERS];
        for (int i = 0; i < this.NUM_LETTERS; i++) {
            secretMatched[i] = 0;
            guessMatched[i] = 0;
               
        }

    	//2. find exact matches
        for (int i = 0; i < this.NUM_LETTERS; i++) {
            if (this.secret.getWord().charAt(i) == this.myGuess.charAt(i)) {
                secretMatched[i] = 1;
                guessMatched[i] =1;
                exact++;
            }
        }

        //3. find partial matches
        for (int i = 0; i < this.NUM_LETTERS; i++) {
            if (guessMatched[i] == 0) {
                // look in secret to find match
                for (int j = 0; j < this.NUM_LETTERS; j++) {
                    if (secretMatched[j] == 0 && this.secret.getWord().charAt(j) == this.myGuess.charAt(i)) {
                        secretMatched[j] = 2;
                        guessMatched[i] = 2;
                        partial++;
                        break;
                    }
                } // end look in secret loop
            }
        }
        System.out.println("Model:guess - " + this.secret.getWord() + " " + this.myGuess + " " + exact + " " + partial);
        
        this.guesses[attempts] = this.myGuess;
        this.guessExact[attempts] = exact;
        this.guessPartial[attempts] = partial;
        this.myPastGuess[attempts] = this.myGuess;

        // load into message log
        int a = attempts+1;
        int left = 10 - attempts - 1;
        String g = this.myGuess;
        String m = "" + a + "," + g + "," + exact + "," + partial  + "," + left + " Guesses Left";
        this.messageLog.add(m);
        

        System.out.println( "   resetting myGuess");
        this.myGuess="";
        this.attempts++;

        if (exact == 5) {
            addToLog("You Win! Select New Game");
            this.needToStartNewGame = true;

        } else if (this.attempts >=10) {
            addToLog("You Lose! Select New Game");
            
            this.needToStartNewGame = true;
        } 

        notifyObservers();
    }

    public void addToLog(String m) {
        //adds a message onto the log
        String s = " " + "," + " " + "," + " " + "," + " " + "," + m;
        this.messageLog.add(s);
        notifyObservers();
    }

    public void hint() {
        this.wantHint = true;
        if (this.secret == null || this.secret.equals("")) {
            for (int i =0; i< 26; i++) {
                this.hint[i]=0;
            }
        } else {
            //String s = secret.getWord().toCharArray();
            for(char c: secret.getWord().toCharArray()) {
                hint[(int)c-65]=1;
            }
        }
    notifyObservers();
    }

    /*
     * Get methods
     */

    public String[] getMatches() {
        return this.matches;
    }
    public int getMatcheSize() {
        return this.matches.length;
    }

    public String[] getMyGuesses(){
        return this.myPastGuess;
    }
    public String getMyGuess() {
        return this.myGuess;
    }

    public String[] getLog() {
        String[] l = this.messageLog.toArray(new String[this.messageLog.size()]);
        return l;
    }

    public boolean getNeedRestart() {
        return this.needToStartNewGame;
    }

    public int getAttempt(){
        return this.attempts;
    }

    public int[] getHint(){
        return this.hint;
    }

    public boolean getHintWanted() {
        return this.wantHint;
    }


    
    /*
     * Old methods
     */

	// set the view observer
	public void addView(IView view) {
		views.add(view);
		// update the view to current state of the model
		notifyObservers();
	}
	
	public int getCounterValue() {
		return counter;
	}
	
	public void incrementCounter() {
		counter++;
		System.out.println("Model: increment counter to " + counter);
		//System.out.println("Model: dict 5th word is " + this.dict.get(4).getWord());
		notifyObservers();
	} 	
	


	// notify the IView observer
	private void notifyObservers() {
			for (IView view : this.views) {
				//System.out.println("Model: notify View");
				view.updateView();
			}
	}
}
