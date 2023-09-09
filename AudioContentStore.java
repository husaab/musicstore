

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

// Simulation of audio content in an online store
// The songs, podcasts, audiobooks listed here can be "downloaded" to your library

public class AudioContentStore
{

	public HashMap<String, Integer> search = new HashMap<>(); // creating hashmap for search function, mapping string to an integer

	public HashMap<String, ArrayList<Integer>> searcha = new HashMap<>(); //creating hashmap for searcha function, mapping string to arraylist, since there are multiple songs from an artist

	public HashMap<String, ArrayList<Integer>> searchg = new HashMap<>(); //mapping string to arraylist again as certain genres have multiple songs

	public ArrayList<AudioContent> contents; //creating public array contents

	public AudioContentStore(){
		contents = ReadStore(); //contents is equal to readstore function, which reads the store.txt file and returns an array list of the audiocontents

	}

	private ArrayList<AudioContent> ReadStore(){

		ArrayList<AudioContent> contentsresult = new ArrayList<AudioContent>(); //initialize arraylist to return

		try{
			File inputfile = new File("store.txt"); //initialize file

			Scanner in = new Scanner(inputfile);

			while(in.hasNextLine()){


				String typeline = in.nextLine();


				if(typeline.equalsIgnoreCase(Song.TYPENAME)){

					System.out.println("Loading " + typeline);

					String id = in.nextLine(); //following format for these next lines
					String title = in.nextLine();
					int year = Integer.valueOf(in.nextLine());
					int length = Integer.valueOf(in.nextLine());
					String artist = in.nextLine();
					String composer = in.nextLine();
					String genre = in.nextLine();
					
					String lyrics =""; //initializing string lyircs
					int numlyrics=Integer.valueOf(in.nextLine()); //initializing num lyrics that gives the number of lyrics

					for(int i =0; i < numlyrics; i++){	//for loop from 0 till number of lyrics
						lyrics+=in.nextLine() + "\n";		//add line to lyrics then add a space
					}

					search.put(title.toUpperCase(), contentsresult.size());	//here, we are putting title to upper case and mapping it to the size of the current contentsarray list
					//we put it uppercase to make the function non-case sensitive

					if(searcha.containsKey(artist.toUpperCase())){	//here, this is an if statement checking if search artist hashmap contains artist
						searcha.get(artist.toUpperCase()).add(contentsresult.size());	//if it does, then get the arraylist mapped with the artist, and add the index of the audiocontent
					}
					else{
						ArrayList<Integer> artistindex = new ArrayList<>();	//if artist is not in hashmap yet, then make an array list and map it to the artist
						artistindex.add(contentsresult.size());	//add current index to the arraylist (thats the index the audiocontent of the artist is)
						searcha.put(artist.toUpperCase(), artistindex);	//now, map artist to artistindex arraylist
					}

					if(searchg.containsKey(genre.toUpperCase())){	//similar to above logic, except now with genre function, check if genre is in map
						searchg.get(genre.toUpperCase()).add(contentsresult.size());	//get array list mapped to the genre and add the current index to it
					}
					else{
						ArrayList<Integer> genreindex = new ArrayList<>();	//if genre not in hashmap yet, create an arraylist for it
						genreindex.add(contentsresult.size());		//add the index of the genre to arraylist
						searchg.put(genre.toUpperCase(), genreindex);	//map genre to arraylist
					}


					contentsresult.add(new Song(title, year, id, Song.TYPENAME, "", length, artist, composer, Song.Genre.valueOf(genre), lyrics)); //this is adding audiocontent to contentsresult arraylist

					

				}

				else if(typeline.equalsIgnoreCase(AudioBook.TYPENAME)){

					System.out.println("Loading " + typeline);

					String id = in.nextLine(); //following format for these next lines
					String title = in.nextLine();
					int year = Integer.valueOf(in.nextLine());
					int length = Integer.valueOf(in.nextLine());
					String author = in.nextLine();
					String narrator = in.nextLine();

					ArrayList<String> chaptertitles = new ArrayList<String>(); //arraylist for chapter titles

					ArrayList<String> chapters = new ArrayList<String>();	// arraylist for chapters

					int chaptertitlesnum = Integer.valueOf(in.nextLine());	//number of chaptertitles variable

					for(int i =0; i < chaptertitlesnum; i++){	//for loop up until number of chapter titles
						chaptertitles.add(in.nextLine());	//add the line to chapter titles array list until chaptertitlesnum
					}

					int chaptersnum = Integer.valueOf(in.nextLine());	//chapters number

					for(int i =0; i < chaptersnum; i++){	//for loop up until number of chapter num
						chapters.add(in.nextLine());	//add line to chapter array list until chapternum
					}

					search.put(title.toUpperCase(), contentsresult.size());	//map title of audiobook to current index

					ArrayList<Integer> artistindex = new ArrayList<>();	//create a new artist(author here) arraylist to map to searchartist
					artistindex.add(contentsresult.size());	//add current index to ararylist

					searcha.put(author.toUpperCase(), artistindex);	//map the author to the artistindex (author index) arraylist
					
					contentsresult.add(new AudioBook(title, year, id, AudioBook.TYPENAME, "", length, author, narrator, chaptertitles, chapters)); //adding audiobook to contnetsresult



				}

	
			}

			in.close();

		}
		catch (IOException exception){	//catch ioexception
			System.out.println(exception.getMessage());	//print message
			System.exit(1);	//quit
		}

		return contentsresult;	//return the contents result arraylist, which contains all audiocontent in the txt file


	}

	

	public void searchfunction(String title){	//search function, take sin title

		if(search.containsKey(title.toUpperCase())){	//checks if search hashmap contains the key of title

			System.out.print(search.get(title.toUpperCase()) + 1 + ". ");	//if it does, print the index (+1)
			contents.get(search.get(title.toUpperCase())).printInfo();	//and continue to print the content at the index mapped to the title

		}
		else{
			throw new AudioContentNotFoundException("No matches for " + title);	//else, just throw new exception no matches for that title
		}

	}

	public void searchafunction(String artist){	//search a function, takes in artist parameter
		if(searcha.containsKey(artist.toUpperCase())){		//checks if key contains artist

			ArrayList<Integer> artindexcopy = searcha.get(artist.toUpperCase());	//create a new arraylist which is equal to the arraylist mapped to the artist containing the indexes

			for(int i =0; i < artindexcopy.size(); i++){	//for loop for the arraylist size
				System.out.print(artindexcopy.get(i) + 1 + ". ");	//printing the index + 1
				contents.get(artindexcopy.get(i)).printInfo();	//printing the content at the index
				System.out.println("");
			}
			
			
		}
		else{
			throw new AudioContentNotFoundException("No matches for " + artist);	//if hashmap does not contain key artist, then throw this exception
		}
	}

	public void searchgfunction(String genre){	//search g function, takes in string genre parameter
		if(searchg.containsKey(genre.toUpperCase())){

			ArrayList<Integer> genreindexcopy = searchg.get(genre.toUpperCase());	//similar to above, create an arraylist and make it equal to the arraylist mapped to the genre

			for(int i =0; i < genreindexcopy.size(); i++){	//for loop for the arraylist
				System.out.print(genreindexcopy.get(i) + 1 + ". ");	//print index + 1 at i
				contents.get(genreindexcopy.get(i)).printInfo();	//print content of index
				System.out.println("");
			}
		}
		else{
			throw new AudioContentNotFoundException("No matches for " + genre);	//if hashmap does not contain key genre, or genre does not have any value , throw exception
		}
	}

	
	
	public AudioContent getContent(int index)
	{
		if (index < 1 || index > contents.size())
		{
			return null;
		}
		return contents.get(index-1);
	}
	
	public void listAll()
	{
		for (int i = 0; i < contents.size(); i++)
		{
			int index = i + 1;
			System.out.print(index + ". ");
			contents.get(i).printInfo();
			System.out.println();
		}
	}
	
	
	
	// Podcast Seasons
	private ArrayList<Season> makeSeasons()
	{
		ArrayList<Season> seasons = new ArrayList<Season>();
	Season s1 = new Season();
	s1.episodeTitles.add("Bay Blanket");
	s1.episodeTitles.add("You Don't Want to Sleep Here");
	s1.episodeTitles.add("The Gold Rush");
	s1.episodeFiles.add("The Bay Blanket. These warm blankets are as iconic as Mariah Carey's \n"
			+ "lip-syncing, but some people believe they were used to spread\n"
			+ "smallpox and decimate entire Indigenous communities. \n"
			+ "We dive into the history of The Hudson's Bay Company and unpack the\n"
			+ "very complicated story of the iconic striped blanket.");
	s1.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \n"
			+ "But what did the mining industry cost the original people of the territory? \n"
			+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
	s1.episodeFiles.add("here is no doubt that the Klondike Gold Rush was an iconic event. \n"
			+ "But what did the mining industry cost the original people of the territory? \n"
			+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
	s1.episodeLengths.add(31);
	s1.episodeLengths.add(32);
	s1.episodeLengths.add(45);
	seasons.add(s1);
	Season s2 = new Season();
	s2.episodeTitles.add("Toronto vs Everyone");
	s2.episodeTitles.add("Water");
	s2.episodeFiles.add("There is no doubt that the Klondike Gold Rush was an iconic event. \n"
			+ "But what did the mining industry cost the original people of the territory? \n"
			+ "And what was left when all the gold was gone? And what is a sour toe cocktail?");
	s2.episodeFiles.add("Can the foundation of Canada be traced back to Indigenous trade routes?\n"
			+ "In this episode Falen and Leah take a trip across the Great Lakes, they talk corn\n"
			+ "and vampires, and discuss some big concerns currently facing Canada's water."); 
	s2.episodeLengths.add(45);
	s2.episodeLengths.add(50);
	
	seasons.add(s2);
	return seasons;
	}
}
