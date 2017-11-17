# Playing The Dragon

### ​[Problem](https://code.google.com/codejam/contest/5304486/dashboard#s=p2&a=2)

The problem is a simulation of a turn based battle, where you are a benign dragon(or so you say), defending yourself against a greedy knight.  And, as they say, the rest is story...

### Process

The first thought was modelling a class that represented a character and using them in a loop that ran while the dragon was alive, following some simple rules reacting to the circumstances. After a long time trying, I perceived that this structure would be very inefficient and would not account for giving absolute confidence that the best path was being taken.

After that, I changed to a similar strategy, but modelled turns as a class, where I would have an immutable state of the moment. Thanks to that strategy, I had a better chance to do backtracking. Along with that, I made the character immutable and gave him the responsibility of curing himself, buffing himself... and debuffing and doing damage to himself... Sorry, nothing is perfect.

Again after that, I got an version that got the right answer. Good enough for the the small dataset but not feasible for the large dataset. On face of that, the best choice I had was making the process less iterative and more mathematical. It was a good choice for efficiency but not for conciseness or understandability, as evident for almost half of the code being the init method for NextTurn, where the it is calculated the state of the turn after a given number of actions of a given type. Another detail is that I only applied those changes to the attacking and debuffing actions, as it showed enough to execute the code without problems. The buffing action remains iterative and simpler.

#### Five Months Later

Today, five months later, here I am again. To start, I separated the responsibilities between different files and added a model package. One time done that, I started make a `tailrec` version of debuff and of buffsNeeded, to avoid mutability and do a, hopefully for all, simpler to understand version.

After that, I perceived that the function that iterate the buffs and attacks was being unnecessary, so I deleted then, in favour of a method that only calculated the number of cures needed, that was simpler.

Another difference was that I retired my ImpossibleException and replace it with null returns that are filtered out in the needed time. It left the code a lot cleaner, to my surprise, with null as a fail state. 

Talking about state, one of my favourite changes now is the addition of a new sealed class called Result, that can be a Success or a Failure. Success always receives a non-nullable Int, and Failure is a singleton. With this addition I created a extension function for nullable Ints, that return a Success or a Failure, depending on the circumstances. 

Another interesting fact about that is that it seemed more testable for me after that, but even so, I didn't feel completely satisfied with the testability of my models.

### ​[Solution](Solver1AC.kt)

As said before, the solution isn't really simple. First I create a base Turn, that derives from the facts given by user's input. From this Turn, I derive a list of only debuffed turns, until the knight can do no damage. As there can be a enormous number of those, I do only the first iteration on each damage threshold, a limit that changes the number of attacks that the knight should make before he kills a full health dragon. 

After that, it's possible to mathematically determine the optimal number of buffs and attacks to kill the knight and for each debuffed turn, I only have to buff the dragon and attack the knight until he dies, on the end, I check which has the minimal number of turns.

For the impossible cases, I return a null, that is filtered out of the possibility space every time it happens.

### For the Future?

For the future I would like to simplify the NextTurn initialization, which composed almost half of the code and is a example of spaghetti code, as even I after doing the code fear trying to make changes in it in fear of breaking the program. Now I just have to get some courage and a idea of how to solve.

#### Five Months Later

I think in the end I got to do some of the changes proposed before. Even so, I feel a little disappointed, as although I did a [tail call recursive](https://blog.jetbrains.com/kotlin/2013/12/m6-2-available/) version of the three action types, that didn't gave stack overflows, those implementations showed yet again too slow for a competitive programming program and yet again I had to do the mathematical version of those functions, it is a shame, as the `tailrec` versions were dar more elegant in my vision.