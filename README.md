# Cron Parser

Cron Parser is a java library that converts string into a cron integers

## Installation

Java21 and maven is required to run this project. Simply run

```bash
mvn package
```

and then run the jar file in the target folder with

```bash
java -jar target/cron-parser.jar -h
```

to see the help menu

## Usage

```bash
java -jar target/cron-parser.jar 1-10/2 '*' 3 4 5 /usr/bin/find
```

output:

```text
minute         1 3 5 7 9
hour           2
day of month   3
month          4
day of week    5
command        /usr/bin/find

```
 ## Troubleshooting
If you have problem running the code with * (asterisk) in the command line, try to put it in quotes like this

```bash
'*'
```