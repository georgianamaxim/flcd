
/* A Bison parser, made by GNU Bison 2.4.1.  */

/* Skeleton interface for Bison's Yacc-like parsers in C
   
      Copyright (C) 1984, 1989, 1990, 2000, 2001, 2002, 2003, 2004, 2005, 2006
   Free Software Foundation, Inc.
   
   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.
   
   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.
   
   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <http://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.
   
   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */


/* Tokens.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
   /* Put the tokens into the symbol table, so that GDB and other debuggers
      know about them.  */
   enum yytokentype {
     INTEGER = 258,
     ASSIGNMENT = 259,
     IF = 260,
     THEN = 261,
     ELSE = 262,
     CHARACTER = 263,
     CHARSEQ = 264,
     VECTOR = 265,
     FOR = 266,
     READ = 267,
     WRITE = 268,
     PLUS = 269,
     MINUS = 270,
     MULTIPLICATION = 271,
     REDUCTION = 272,
     SMALLER = 273,
     BIGGER = 274,
     SMALLER_OR_EQUAL = 275,
     BIGGER_OR_EQUAL = 276,
     DIFFERENT = 277,
     EQUAL = 278,
     READ_OP = 279,
     WRITE_OP = 280,
     LEFT_OPEN_BRACKET = 281,
     RIGHT_OPEN_BRACKET = 282,
     LEFT_SQUARE_PARANTHESIS = 283,
     RIGHT_SQUARE_PARANTHESIS = 284,
     LEFT_ROUND_PARANTHESIS = 285,
     RIGHT_ROUND_PARANTHESIS = 286,
     SEMICOLON = 287,
     COLON = 288,
     IDENTIFIER = 289,
     WORD = 290,
     NUMBER = 291,
     CONSTANT_CHAR = 292
   };
#endif



#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define yystype YYSTYPE /* obsolescent; will be withdrawn */
# define YYSTYPE_IS_DECLARED 1
#endif

extern YYSTYPE yylval;


