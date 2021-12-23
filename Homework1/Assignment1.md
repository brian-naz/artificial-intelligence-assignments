For this lab assignment, you are given a starting location and goal location (or
destination), and must find a path to the goal location. In this problem, the optimal path is the
one which has the shortest travel time. You will be implementing the following four search
algorithms: Breadth-First Search (BFS), Depth-First Search (DFS), Uniform Cost Search (UCS),
and A* Search.

Your program will read an input.txt file. The input.txt file will specify one of the four searching
algorithms, as well as starting and goal locations. In addition, the input.txt file will contain live
traffic information, which is a list of current traveling times (in minutes) between different
locations. For example, suppose the starting location is your home and the destination is
Staples Center. An example of live traffic data is as follows:

Home CA73/NewportCoastDr 5
CA73/NewportCoastDr I405/CA73 10
I405/CA73 I110/I405 25
I110/I405 I10/I110 20
I10/I110 I110/I405 30
I10/I110 I10/I405 9
I105/I110 I10/I110 7
I10/I110 StaplesCenter 3

Here, each line indicates the current traveling time between two locations. For example, line 3
indicates that it takes 25 minutes to go from I405/CA73 to I110/I405. Note that the location can
be an intersection (such as I405/CA73).

Traveling time may not be the same for both directions. 
For example,
I110/I405 I10/I110 20
indicates that it takes 20 minutes to travel from I110/I405 to I10/I110, but
I10/I110 I110/I405 30
indicates that it takes 30 minutes in the other direction.

Beside the live traffic information, you also have an idea of how long it takes on a traffic-free
Sunday from each location to your goal location. Hence, the input.txt file will also contain the
Sunday traffic estimate of traveling time from each location listed in the file to your
destination. For the example above, the Sunday traffic data may look like this:

Home 55
CA73/NewportCoastDr 50
I405/CA73 40
I110/I405 28
I10/I110 8
I10/I405 39
I105/I110 23
StaplesCenter 0

Your program should write, in an output.txt file, the list of locations traveled over in your
solution path (including the starting and goal locations) and the accumulated time from start to
each location, in order of travel. The following is an example of output.txt:

Home 0
CA73/NewportCoastDr 5
I405/CA73 15
I110/I405 40
I10/I110 60
StaplesCenter 63

The full specification of input.txt and output.txt is shown on the next page.
Full specification for input.txt:

<ALGO>
<START STATE>
<GOAL STATE>
<NUMBER OF LIVE TRAFFIC LINES>
<... LIVE TRAFFIC LINES ...>
<NUMBER OF SUNDAY TRAFFIC LINES>
<... SUNDAY TRAFFIC LINES ...>
  
where:
<ALGO> is the algorithm to use and will be one of the following: “BFS”, “DFS”, “UCS”, “A*” .
<START STATE> is a string with the name of the start location (e.g., “Home”).
<GOAL STATE> is a string with the name of the goal location (e.g., “StaplesCenter”).
<NUMBER OF LIVE TRAFFIC LINES> is the number of lines of live traffic information that follow.
<... LIVE TRAFFIC LINES ...> are lines of live traffic information in the format previously
described, i.e., <STATE1> <STATE2> <TRAVEL TIME FROM STATE1 TO STATE2>
<NUMBER OF SUNDAY TRAFFIC LINES> is the number of lines of Sunday traffic estimates that
follow.
<... SUNDAY TRAFFIC LINES ...> are lines of Sunday traffic information in the format previously
described, i.e., <STATE> <ESTIMATED TIME FROM STATE TO GOAL>
Full specification for output.txt:
Any number of lines with the following format for each line:
<STATE> <ACCUMULATED TRAVEL TIME FROM START TO STATE>
  

Example 1: Consider this input.txt:

BFS
A
D
4
A B 5
A C 3
B D 1
C D 2
4
A 4
B 1
C 1
D 0
  
Should yield the following output.txt:

A 0
B 5
D 6

Example 2: Consider this input.txt:
  
UCS
A
E
5
A B 80
A D 90
B C 10
D E 20
C E 20
5
A 50
B 40
C 30
D 20
E 0
  
Should yield the following output.txt:
  
A 0
D 90
E 110
  
Example 3: Consider this input.txt:
  
DFS
A
E
6
A C 1
A B 1
B C 1
B D 1
C E 1
D E 1
5
A 2
B 1
C 1
D 1
E 0
  
Should yield the following output.txt:
  
A 0
B 1
D 2
E 3
  
Example 4: Consider this input.txt:
  
A*
A
D
4
A B 3
A C 3
B D 2
C D 1
4
A 4
B 2
C 1
D 0
  
Should yield the following output.txt:
  
A 0
C 3
D 4
