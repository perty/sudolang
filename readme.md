# Sudolang experiments

This repository contains some experiments with the [Sudolang](https://github.com/paralleldrive/sudolang-llm-support)
language.

Sudolang is a programming language designed to collaborate with AI language models.

## Hunt the Wumpus

I thought that the old 70' game Hunt the Wumpus would be a good example to try SudoLang. It is text based which
simplifies
the design of the first version of the user interface. I have used it in teaching good software development practices
such as continuous integration.

A problem might be that the game may already be known to the LLM which might interfere. The LLM might fill in blanks
in my program, sort of cheating, and is not a great evaluation of the technique.

I approached by first just write the description of the game with content from a
[Wikipedia page](https://en.wikipedia.org/wiki/Hunt_the_Wumpus). It did fairly well, but broke some rules.

Next, I tried to do the same in Sudolang. I both tried to get it transpiled to JavaScript and run it in the browser but
both ChapGpt 4o and Claude 3.5 Sonnet failed in different ways.

The main problem seems to be shooting the arrows. The rule is that the user may enter any caves they are aiming the
arrow
to but the arrow can only follow connected caves. If the given path is not possible, it should resort to pick caves
randomly.

I tried with writing the constraints explicitly, however, no luck.

## Back to square one

So, how does different approaches compare. And does the LLM matter? I decided to create a structured approach.

The first test would be a simple free-text description of the game. I would compare the LLMs ChapGpt 4o and Claude 3.5
Sonnet. Each LLM would both play the game and transpile the game into Java.

Second test would be to use Sudolang to describe the game, taking the promise of pseudocode to the table.

The hypothesis is that the LLMs will perform worse in the free-text description than in the Sudolang description.
The evaluation criteria are compliance with the rules of the game and the ability to transpile the game into fully
functional program code.

I renamed the game to Knossos so nothing would leak from training data.

## Experiment 1 Free-text description

File: [knossos_v1.md](back-to-square-1/knossos_v1.md) 
Free-text description, based on the [Wikipedia page](https://en.wikipedia.org/wiki/Hunt_the_Wumpus). 

**ChaptGpt** had no problem understanding the game. However, it did not follow the rules. It did not understand the 
constraints of the arrows, that they only travel through connected caves. Also, although the Minotaur smell was
there, it was not in a connected cave, which was a clear violation of the rules.

I tried ask it where the Minotaur was, but it stayed in the role a game master and did reveal the location.

Next thing, I just asked for a Java program that implemented the game. It gave me an outline of the game, but it was not
complete. Fair enough, it asked if I needed help with the implementation.

I asked for a test of the game, so I wouldn't have to do that manually. ChatGpt did so and there was a number of 
compiler errors. I iterated until the test passed. Told ChatGpt what was not passing and got more implementation back.

When the test passed, it was still not possible to play the game, so I asked for a new test where the interpretation
of user commands was tested. Again, this drove the implementation forward. 

One of the tests didn't pass but here I gave up. The way I worked with ChatGpt was just tiresome.
