

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.StringTokenizer;

import Library.AudioContentNotFoundException;
import Library.AudioContentAlreadyDownloadedException;
import Library.SongNotFoundException;

// Simulation of a Simple Text-based Music App (like Apple Music)

public class MyAudioUI
{
	public static void main(String[] args)
	{
		// Simulation of audio content in an online store
		// The songs, podcasts, audiobooks in the store can be downloaded to your library
		AudioContentStore store = new AudioContentStore();
		
		// Create my music library
		Library library = new Library();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");

		// Process keyboard actions
		while (scanner.hasNextLine())
		{
			String action = scanner.nextLine();

			if (action == null || action.equals("")) 
			{
				System.out.print("\n>");
				continue;
			}
			else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
				return;
			
			else if (action.equalsIgnoreCase("STORE"))	// List all songs
			{
				store.listAll(); 
			}
			else if (action.equalsIgnoreCase("SONGS"))	// List all songs
			{
				library.listAllSongs(); 
			}
			else if (action.equalsIgnoreCase("BOOKS"))	// List all songs
			{
				library.listAllAudioBooks(); 
			}
			else if (action.equalsIgnoreCase("PODCASTS"))	// List all songs
			{
				library.listAllPodcasts(); 
			}
			else if (action.equalsIgnoreCase("ARTISTS"))	// List all songs
			{
				library.listAllArtists(); 
			}
			else if (action.equalsIgnoreCase("PLAYLISTS"))	// List all play lists
			{
				library.listAllPlaylists(); 
			}
			else if (action.equalsIgnoreCase("DOWNLOAD")) 
			{
				try{
					int firstindex = 0;	//first index
					int secondindex =0;	//second index
				
					System.out.print("From Store Content #: ");
					if (scanner.hasNextInt())
					{
						firstindex = scanner.nextInt();
					}
					System.out.print("To Store Content #: ");
					if (scanner.hasNextInt()){
						secondindex=scanner.nextInt();
						scanner.nextLine();
					}

					for(int i=firstindex; i <= secondindex; i++){	//for loop from first index to second index
						AudioContent content = store.getContent(i);	//grab the audiocontent at i
						library.download(content);	//download the content, so what happens here is it prints all content from firstindex to secondindex
					}
					
				}
				catch(NullPointerException nullException)	//nullpointer exception, throws content not found
                {
                    System.out.println("Content not Found");
                }
									
			}
			else if (action.equalsIgnoreCase("PLAYSONG")) 
			{
				try{
					int index = 0;


					System.out.print("Song Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
					// consume the nl character since nextInt() does not
						scanner.nextLine(); 
					}

					library.playSong(index);
				}
				catch(SongNotFoundException exception){
					System.out.println(exception.getMessage());
				}
					
			}
			else if (action.equalsIgnoreCase("BOOKTOC")) 
			{
				try{
					int index = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					library.printAudioBookTOC(index);
				}
				catch(AudioBookNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if (action.equalsIgnoreCase("PLAYBOOK")) 
			{

				try{
					int index = 0;

					System.out.print("Audio Book Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
					}
					int chapter = 0;
					System.out.print("Chapter: ");
					if (scanner.hasNextInt())
					{
						chapter = scanner.nextInt();
						scanner.nextLine();
					}
					library.playAudioBook(index, chapter);
				}
				catch(AudioBookNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			
			else if (action.equalsIgnoreCase("PODTOC")) 
			{
				try{
					int index = 0;
					int season = 0;
					
					System.out.print("Podcast Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
					}
					System.out.print("Season: ");
					if (scanner.hasNextInt())
					{
						season = scanner.nextInt();
						scanner.nextLine();
					}
					library.printPodcastEpisodes(index, season);
				}
				catch(PodcastNotFoundException exception){
					System.out.println(exception.getMessage());
				}	
			}
			else if (action.equalsIgnoreCase("PLAYPOD")) 
			{
				try{
					int index = 0;

					System.out.print("Podcast Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					int season = 0;
					System.out.print("Season: ");
					if (scanner.hasNextInt())
					{
						season = scanner.nextInt();
						scanner.nextLine();
					}
					int episode = 0;
					System.out.print("Episode: ");
					if (scanner.hasNextInt())
					{
						episode = scanner.nextInt();
						scanner.nextLine();
					}
					library.playPodcast(index, season, episode);
				}
				catch(PodcastNotFoundException exception){
					System.out.println(exception.getMessage());	
				}
				
			}
			else if (action.equalsIgnoreCase("PLAYALLPL")) 
			{
				try{
					String title = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					library.playPlaylist(title);
				}
				catch(PlaylistNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("PLAYPL")) 
			{
				try{
					String title = "";
					int index = 0;
			
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					System.out.print("Content Number: ");
					if (scanner.hasNextInt())
					{
						index = scanner.nextInt();
						scanner.nextLine();
					}
					library.playPlaylist(title, index);
				}
				catch(PlaylistNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			// Delete a song from the library and any play lists it belongs to
			else if (action.equalsIgnoreCase("DELSONG")) 
			{
				try{
					int songNum = 0;

					System.out.print("Library Song #: ");
					if (scanner.hasNextInt())
					{
						songNum = scanner.nextInt();
						scanner.nextLine();
					}
					library.deleteSong(songNum);
				}
				catch(SongNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			else if (action.equalsIgnoreCase("MAKEPL")) 
			{
				try{
					String title = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
					{
						title = scanner.nextLine();
					}
					library.makePlaylist(title);
				}
				catch(PlaylistExistenceException exception){
					System.out.println(exception.getMessage());
				}
					
			}
			else if (action.equalsIgnoreCase("PRINTPL"))	// print playlist content
			{
				try{
					String title = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
						title = scanner.nextLine();

					library.printPlaylist(title);
				}
				catch(PlaylistNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			// Add content from library (via index) to a playlist
			else if (action.equalsIgnoreCase("ADDTOPL")) 
			{
				try{
					int contentIndex = 0;
					String contentType = "";
					String playlist = "";
					
					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
						playlist = scanner.nextLine();
			
					System.out.print("Content Type [SONG, PODCAST, AUDIOBOOK]: ");
					if (scanner.hasNextLine())
						contentType = scanner.nextLine();
					
					System.out.print("Library Content #: ");
					if (scanner.hasNextInt())
					{
						contentIndex = scanner.nextInt();
						scanner.nextLine(); // consume nl
					}
					library.addContentToPlaylist(contentType, contentIndex, playlist);
				}

				catch(PlaylistNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				catch(SongNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				catch(AudioBookNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				catch(PodcastNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				
			}
			// Delete content from play list
			else if (action.equalsIgnoreCase("DELFROMPL")) 
			{
				try{
					int contentIndex = 0;
					String playlist = "";

					System.out.print("Playlist Title: ");
					if (scanner.hasNextLine())
						playlist = scanner.nextLine();
					
					System.out.print("Playlist Content #: ");
					if (scanner.hasNextInt())
					{
						contentIndex = scanner.nextInt();
						scanner.nextLine(); // consume nl
					}

					library.delContentFromPlaylist(contentIndex, playlist);
				}
				catch(PlaylistNotFoundException exception){
					System.out.println(exception.getMessage());
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if(action.equalsIgnoreCase("search")){
				try{
					System.out.print("Title: ");

					String title="";

					if(scanner.hasNextLine()){
						title=scanner.nextLine();
					}

					store.searchfunction(title);	//call search function with title input
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}


			}
			else if(action.equalsIgnoreCase("searcha")){
				try{
					System.out.print("Artist/Author: ");

					String artist="";

					if(scanner.hasNextLine()){
						artist=scanner.nextLine();
					}

					store.searchafunction(artist);	//call searcha function with artist input
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if(action.equalsIgnoreCase("searchg")){
				try{
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

					String genre ="";

					if(scanner.hasNextLine()){
						genre=scanner.nextLine();
					}

					store.searchgfunction(genre);	//call searchg function with genre input
				}
				catch(AudioContentNotFoundException exception){
					System.out.println(exception.getMessage());
				}
			}
			else if(action.equalsIgnoreCase("downloada")){
				try{
					System.out.print("Artist/Author: ");

					String artist = "";

					if(scanner.hasNextLine()){
						artist=scanner.nextLine();
					}

					//downloada function

					ArrayList<Integer> artindexcopy = store.searcha.get(artist.toUpperCase()); //here, create an arraylist equal to arraylist that is mapped to the artist key in searcha hashmap
			
					for(int i =0; i < artindexcopy.size(); i++){	//for loop for the arraylist
						AudioContent content = store.getContent(artindexcopy.get(i) + 1);	//get content, add 1 since getcontent function removes 1
						library.download(content);	//download the content
					}

					}
				catch(NullPointerException exception){
					System.out.println("Artist/Author not found");
				}

			}
			else if(action.equalsIgnoreCase("downloadg")){

				try{
					System.out.print("Genre [POP, ROCK, JAZZ, HIPHOP, RAP, CLASSICAL]: ");

					String genre ="";

					if(scanner.hasNextLine()){
						genre=scanner.nextLine();
					}

					//download g function

					ArrayList<Integer> genreindexcopy = store.searchg.get(genre.toUpperCase()); //create an arraylist equal to the arraylist mapped to the genre key in the searchg hashmap

					for(int i =0; i <genreindexcopy.size(); i++){	//for loop
						AudioContent content = store.getContent(genreindexcopy.get(i) + 1); //getcontent of index +1 (+1 since getcontent removes 1)
						library.download(content);	//download the content
					}

				}

				catch (NullPointerException exception){
					System.out.println("No songs matched with Genre");
				}



			}
			else if (action.equalsIgnoreCase("SORTBYYEAR")) // sort songs by year
			{
				library.sortSongsByYear();
			}
			else if (action.equalsIgnoreCase("SORTBYNAME")) // sort songs by name (alphabetic)
			{
				library.sortSongsByName();
			}
			else if (action.equalsIgnoreCase("SORTBYLENGTH")) // sort songs by length
			{
				library.sortSongsByLength();
			}

			System.out.print("\n>");
		}
	}
}
