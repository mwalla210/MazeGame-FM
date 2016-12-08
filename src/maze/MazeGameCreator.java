/*
 * MazeGameCreator.java
 * Copyright (c) 2008, Drexel University.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 *       notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 *       notice, this list of conditions and the following disclaimer in the
 *       documentation and/or other materials provided with the distribution.
 *     * Neither the name of the Drexel University nor the
 *       names of its contributors may be used to endorse or promote products
 *       derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY DREXEL UNIVERSITY ``AS IS'' AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL DREXEL UNIVERSITY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package maze;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
/**
 * 
 * @author Sunny
 * @version 1.0
 * @since 1.0
 */
public class MazeGameCreator
{
	/**
	 * Creates a small maze.
	 */
	public Maze createMaze()
	{
		Maze maze = new Maze();
		Room firstroom = makeRoom(0) ;
		Room secondroom = makeRoom(1) ;
		maze.addRoom(firstroom);
		maze.addRoom(secondroom);
		Door door = makeDoor(firstroom, secondroom);
		firstroom.setSide(Direction.North, makeWall());
		firstroom.setSide(Direction.East, door);
		firstroom.setSide(Direction.South, makeWall());
		firstroom.setSide(Direction.West, makeWall());
		secondroom.setSide(Direction.North, makeWall());
		secondroom.setSide(Direction.East, makeWall());
		secondroom.setSide(Direction.South, makeWall());
		secondroom.setSide(Direction.West, door);
		maze.setCurrentRoom(0);
		//System.out.println("The maze does not have any rooms yet!");
		return maze;
	}

	public Maze loadMaze(final String path)
	{
		List <Room> rooms = new ArrayList <Room> () ; 
		List <Door> doors = new ArrayList <Door> () ;
		List <List <String>> doorinfo = new ArrayList <List <String>> () ;
		List <List <String>> roominfo = new ArrayList <List <String>> () ;
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
		    String line;
		    while ((line = br.readLine()) != null) {
		    	String[] arr = line.split("\\s+") ;
		    	if (arr.length > 2){
			    	if (arr[0].equals("room")){
			        	System.out.print("Was room ");
			        	try { 
		                    int c = Integer.parseInt(arr[1]); 
		                    System.out.println("with index " + c);
		                    rooms.add(makeRoom(c)) ;
		                } catch(NumberFormatException e) { 
		                    System.out.print(""); 
		                } catch(NullPointerException e) {
		                	System.out.print("");
		                }
			        	List <String> info = new ArrayList <String> () ;
			        	info.add(arr[2]) ; //N
			        	info.add(arr[3]) ; //S
			        	info.add(arr[4]) ; //E
			        	info.add(arr[5]) ; //W
			        	roominfo.add(info) ;
		            	//walls/openings/doors
			        }
			        else{
			        	System.out.println("Was door with index " + arr[1]);
			        	List <String> info = new ArrayList <String> () ;
			        	info.add(arr[2]) ; //room 1
			        	info.add(arr[3]) ; //room 2
			        	info.add(arr[4]) ; //opened/closed
			        	doorinfo.add(info) ;
			        }
		    	}
		    }
		} catch (FileNotFoundException e1) {
			System.out.println("Error opening file");
		} catch (IOException e1) {
			System.out.println("Error reading file");
		}
	  	
		Maze maze = new Maze();
		for (int i = 0; i < rooms.size(); i++){
			maze.addRoom(rooms.get(i));
		}
		//create doors
		for (int i = 0; i < doorinfo.size(); i++){
			String s = doorinfo.get(i).get(0) ;
			int roomindex1 = 0, roomindex2 = 0 ;
			try { 
				roomindex1 = Integer.parseInt(s) ;
            } catch(NumberFormatException e) { 
                System.out.println("Error converting to int"); 
            } catch(NullPointerException e) {
            	System.out.println("Error converting null to int");
            }
			s = doorinfo.get(i).get(1) ;
			try { 
				roomindex2 = Integer.parseInt(s) ;
            } catch(NumberFormatException e) { 
                System.out.println("Error converting to int"); 
            } catch(NullPointerException e) {
            	System.out.println("Error converting null to int");
            }
			Door temp = makeDoor(rooms.get(roomindex1), rooms.get(roomindex2)) ; 
			if (doorinfo.get(i).get(2).equals("open"))
					temp.setOpen(true) ;
			doors.add(temp) ;
		}
		//sides (walls, doors, rooms)
		for (int i = 0; i < rooms.size(); i++){
			Direction[] dirs = {Direction.North, Direction.South, Direction.East, Direction.West} ;
			for (int j = 0; j < roominfo.get(i).size(); j++){
				String s = roominfo.get(i).get(j) ;
				if (s.equals("wall")){
					rooms.get(i).setSide(dirs[j], makeWall()) ;
				}
				else if (s.charAt(0) == 'd'){
					int doorindex = Integer.parseInt(String.valueOf(s.charAt(1))) ;
					rooms.get(i).setSide(dirs[j], doors.get(doorindex)) ;
				}
				else
					rooms.get(i).setSide(dirs[j], rooms.get(Integer.parseInt(s))) ;
			}
		}
		
		maze.setCurrentRoom(0);
		//System.out.println("Please load a maze from the file!");
		return maze;
	}

	public Wall makeWall ()
	{
		return new Wall() ;
	}
	
	public Room makeRoom (int i)
	{
		return new Room(i);
	}
	
	public Door makeDoor (Room r1, Room r2)
	{
		return new Door(r1,r2) ;
	}
}
