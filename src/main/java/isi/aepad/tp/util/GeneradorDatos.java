package isi.aepad.tp.util;

import java.util.Random;

import javax.enterprise.context.RequestScoped;

@RequestScoped
public class GeneradorDatos {
	public String generateRandomWords(int numberOfWords)
	{
	    StringBuilder randomStrings = new StringBuilder();
	    Random random = new Random();
	    for(int i = 0; i < numberOfWords; i++)
	    {
	        char[] word = new char[random.nextInt(8)+3]; // words of length 3 through 10. (1 and 2 letter words are boring.)
	        for(int j = 0; j < word.length; j++)
	        {
	            word[j] = (char)('a' + random.nextInt(26));
	        }
	        randomStrings.append(" ");
	        randomStrings.append(word);
	    }
	    return randomStrings.toString();
	}

}
