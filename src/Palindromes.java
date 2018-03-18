import java.util.Stack;

public class Palindromes {
public Palindromes() {
	
}

public boolean isPalindrome(String word) {
	String newString = "";
	Stack<Character> st = new Stack<Character>();
	for (int i = 0; i < word.length(); i++) {
		st.push(word.charAt(i));
	}
	for (int i = 0; i < word.length(); i++) {
		newString+=st.pop();
	}
	if(newString.equals(word)) 
	{
		return true;
	}
	else {
		return false;
		}
}

}
