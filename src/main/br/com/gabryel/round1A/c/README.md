# Playing The Dragon

### [Problem](https://code.google.com/codejam/contest/5304486/dashboard#s=p0&a=2)

The problem is a simulation of a turn based battle, where you are a benign dragon(or so you say), defending yourself against a greedy knight.  And, as they say, the rest is story...

### Process

The first thought was modelling a class that represented a character and using them in a loop that ran while the dragon was alive, following some simple rules reacting to the circumstances. After a long time trying, I perceived that this structure would be very inefficient and would not account for giving absolute confidence that the best path was being taken.

After that, I changed to a similar strategy, but modelled turns as a class, where I would have an immutable state of the moment. Thanks to that strategy, I had a better chance to do backtracking. Along with that, I made the character immutable and gave him the responsibility of curing himself, buffing himself... and debuffing and doing damage to himself... Sorry, nothing is perfect.

Again after that, I got an version that got the right answer. Good enough for the the small dataset but not feasible for the large dataset. On face of that, the best choice I had was making the process less iterative and more mathematical. It was a good choice for efficiency but not for conciseness or understandability, as evident for almost half of the code being the init method for NextTurn, where the it is calculated the state of the turn after a given number of actions of a given type. Another detail is that I only applied those changes to the attacking and debuffing actions, as it showed enough to execute the code without problems. The buffing action remains iterative and simpler.

### [Solution](Solver1AC.kt)

As said before, the solution isn't really simple. First I create a base Turn, that derives from the facts given by user's input. From this Turn, I derive a list of only debuffed turns, until the knight can do no damage. As there can be a enormous number of those, I do only the first iteration on each damage threshold, a limit that changes the number of attacks that the knight should make before he kills a full health dragon. 

After that, it's possible to mathematically determine the optimal number of buffs and attacks to kill the knight and for each debuffed turn, I only have to buff the dragon and attack the knight until he dies, on the end, I check which has the minimal number of turns.

For the impossible cases, I throw an exception on the moment it is proved impossible, returning IMPOSSIBLE for the output.

### For the Future?

For the future I would like to simplify the NextTurn initialization, which composed almost half of the code and is a example of spaghetti code, as even I after doing the code fear trying to make changes in it in fear of breaking the program. Now I just have to get some courage and a idea of how to solve.
