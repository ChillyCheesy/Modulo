# COMMAND INTERPRETER

## Table of contents
* Introduction
* Operator
  * Native Operators
* Command

## Introduction

## Operators
### Native Operators
| Operator                       | Priority | Description                                                                                      | Example                           |
|:-------------------------------|:--------:|--------------------------------------------------------------------------------------------------|-----------------------------------|
| `'<instruction>'`              |    10    | Replace instruction by a no interpretable value.                                                 | `echo '6 + 7'`                    |
| `;`                            |    9     | Separate operation instruction.<br/>Return the last instruction.                                 | `<echo I love>;<echo ewok>;`      |
| `=>`                           |    8     | Create a new Alias with the value.<br/>The alias was strict and interpreted.                     | `plus => a + b`                   |
| `(<instruction>)`              |    7     | Execute the instruction first<br/>Return the result.                                             | `5 * (9 + 2)`                     |
| `[<instruction>]`              |    6     | Return the instruction status.<br/> true if success, else false.                                 | `echo [mycommand]`                |
| `{<instruction>}`              |    6     | Execute the instruction with a sub alias manager.                                                | `echo { return "ewok" }`          |
| `<alias>`                      |    5     | Replace aliases with their values.                                                               | `ewokName = Wicket;echo ewokName` |
| `"<instruction>" :> {<block>}` |    4     | Inject instruction inside a block.                                                               |                                   |                                                                  |                                   |
| `<alias> = <value>`            |    3     | Create a new Alias with the value.                                                               | `ewokName = Wicket`               |
| `*`                            |    2     | Multiply operator on two numbers.                                                                | `6 * 7`                           |
| `/`                            |    2     | Divide operator on two numbers.                                                                  | `6 / 7`                           |
| `^`                            |    2     | Power operator on two numbers.                                                                   | `6 ^ 7`                           |
| `%`                            |    2     | Modulo operator on two numbers.                                                                  | `6 % 7`                           |
| `+`                            |    1     | Plus operator on two numbers.                                                                    | `6 + 7`                           |
| `-`                            |    1     | Minus operator on two numbers.                                                                   | `6 - 7`                           |
| `<`                            |    1     | Compare two number.<br/>Return true if the left number is lower than the right number.           | `6 < 7`                           |
| `>`                            |    1     | Compare two number.<br/>Return true if the right number is lower than the left number.           | `6 > 7`                           |
| `<=`                           |    1     | Compare two number.<br/>Return true if the left number is lower or equals than the right number. | `6 <= 7`                          |
| `>=`                           |    1     | Compare two number.<br/>Return true if the right number is lower or equals than the left number. | `6 >= 7`                          |
| `==`                           |    1     | Compare two number or boolean.<br/>Return true if there are equals.                              | `6 == 7`                          |
| `&&`                           |    0     | And operator on two booleans.                                                                    | `true && false`                   |
| `&brvbar;&brvbar;`             |    0     | And operator on two booleans.                                                                    | `true && false`                   |
| `&&brvbar;`                    |    0     | And operator on two booleans.                                                                    | `true && false`                   |
| `!`                            |    0     | Not operator on one boolean.                                                                     | `!true`                           |
