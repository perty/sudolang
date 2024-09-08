# Sudolang experiments

This repository contains some experiments with the [Sudolang](https://github.com/paralleldrive/sudolang-llm-support) language.

Sudolang is a programming language designed to collaborate with AI language models.

## Hunt the Wumpus

I thought that the old 70' game Hunt the Wumpus would be a good example to try SudoLang. It is text based which simplifies 
the design of the first version of the user interface. I have used it in teaching good software development practices 
such as continuous integration. 

A problem might be that the game may already be known to the LLM which might interfere. The LLM might fill in blanks
in my program, sort of cheating, and is not a great evaluation of the technique.

I approached by first just write the description of the game with content from a 
[Wikipedia page](https://en.wikipedia.org/wiki/Hunt_the_Wumpus). It did fairly well, but broke some rules.

Next, I tried to do the same in Sudolang. I both tried to get it transpiled to JavaScript and run it in the browser but 
both ChapGpt 4o and Claude 3.5 Sonnet failed in different ways. 

The main problem seems to be shooting the arrows. The rule is that the user may enter any caves they are aiming the arrow
to but the arrow can only follow connected caves. If the given path is not possible, it should resort to pick caves 
randomly.

I tried with writing the constraints explicitly, however, no luck.

## Back to square one

So, how does different approaches compare. And does the LLM matter? I decided to create a structured approach.

The first test would be a simple free-text description of the game. I would compare the LLMs ChapGpt 4o and Claude 3.5
Sonnet. Each LLM would both play the game and transpile the game into Java.

Second test would be to use Sudolang to describe the game, taking the promise of pseudocode to the table.
