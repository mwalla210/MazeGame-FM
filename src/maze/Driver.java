package maze;

import maze.ui.MazeViewer;

public class Driver {
	public static RedMazeGameCreator rmgc = new RedMazeGameCreator();
	public static BlueMazeGameCreator bmgc = new BlueMazeGameCreator();
	public static MazeGameCreator mgc = new MazeGameCreator();
	public static void main(String[] args)
	{
	    if (args.length > 1) //passed type and filepath
	    {
	    	if (args[0].equals("red"))
	    	{
	    		//Red load
	    		Maze maze2 = rmgc.loadMaze(args[1]) ;
		    	MazeViewer viewer = new MazeViewer(maze2);
			    viewer.run();
	    	}
	    	else if (args[0].equals("blue"))
	    	{
	    		//Blue load
	    		Maze maze2 = bmgc.loadMaze(args[1]) ;
		    	MazeViewer viewer = new MazeViewer(maze2);
			    viewer.run();
	    	}
	    	else if (args[0].equals("basic"))
	    	{
	    		//Basic load
	    		Maze maze2 = mgc.loadMaze(args[1]) ;
		    	MazeViewer viewer = new MazeViewer(maze2);
			    viewer.run();
	    	}
	    }
	    else if (args.length == 1)
	    {
	    	if (args[0].equals("red")) //passed type but no filepath
	    	{
	    		//Make default red
	    		Maze maze = rmgc.createMaze() ;
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
	    	}
	    	else if (args[0].equals("blue")) //passed type but no filepath
	    	{
	    		//Make default blue
	    		Maze maze = bmgc.createMaze();
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
	    	}
	    	else if (args[0].equals("basic")) //passed type but no filepath
	    	{
	    		//Make default basic
	    		Maze maze = mgc.createMaze();
			    MazeViewer viewer = new MazeViewer(maze);
			    viewer.run();
	    	}
	    	else //passed just a file path, proceed with basic
	    	{
	    		//Basic load
	    		Maze maze2 = mgc.loadMaze(args[0]) ;
		    	MazeViewer viewer = new MazeViewer(maze2);
			    viewer.run();
	    	}
	    }
	    else{
	    	Maze maze = mgc.createMaze();
		    MazeViewer viewer = new MazeViewer(maze);
		    viewer.run();
	    }
	}
}
