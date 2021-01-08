%{
#include <stdio.h>
#include <stdlib.h>
#define YYDEBUG 1
%}

%token INTEGER
%token ASSIGNMENT
%token IF
%token THEN
%token ELSE
%token CHARACTER
%token CHARSEQ
%token VECTOR
%token FOR
%token READ
%token WRITE

%token PLUS
%token MINUS
%token MULTIPLICATION
%token REDUCTION
%token SMALLER
%token BIGGER
%token SMALLER_OR_EQUAL
%token BIGGER_OR_EQUAL
%token DIFFERENT
%token EQUAL
%token READ_OP
%token WRITE_OP

%token LEFT_OPEN_BRACKET
%token RIGHT_OPEN_BRACKET
%token LEFT_SQUARE_PARANTHESIS
%token RIGHT_SQUARE_PARANTHESIS
%token LEFT_ROUND_PARANTHESIS
%token RIGHT_ROUND_PARANTHESIS
%token SEMICOLON
%token COLON

%token IDENTIFIER
%token WORD
%token NUMBER
%token CONSTANT_CHAR

%start program

%%

program : LEFT_OPEN_BRACKET decllist cmpdstmt RIGHT_OPEN_BRACKET ;

decllist : declaration | declaration decllist ;
declaration : type IDENTIFIER SEMICOLON ;

type1 : CHARACTER | INTEGER | CHARSEQ ;
arraydecl : VECTOR LEFT_SQUARE_PARANTHESIS type1 RIGHT_SQUARE_PARANTHESIS  NUMBER IDENTIFIER ;
arrayaccess : IDENTIFIER LEFT_SQUARE_PARANTHESIS numberoridnetifier RIGHT_SQUARE_PARANTHESIS ;
emptyarray : LEFT_SQUARE_PARANTHESIS RIGHT_SQUARE_PARANTHESIS;
numberoridnetifier : NUMBER | IDENTIFIER ;
type : type1 | arraydecl ;

cmpdstmt : LEFT_OPEN_BRACKET stmtlist RIGHT_OPEN_BRACKET ;
stmtlist : stmt | stmt stmtlist ;
stmt : simplestmt | structstmt ;
simplestmt : assignmstmt | istmt | ostmt ;

assignmstmt : idorarray ASSIGNMENT expression SEMICOLON ;
idorarray : IDENTIFIER | arrayaccess ;
expression : term | expression plusminus term ;
plusminus : PLUS | MINUS ;
term : factor | term multred factor ;
multred : MULTIPLICATION | REDUCTION ;
factor : IDENTIFIER | arrayaccess | NUMBER | WORD | CONSTANT_CHAR | emptyarray ;

istmt : READ READ_OP IDENTIFIER SEMICOLON ;
ostmt: WRITE WRITE_OP idorword SEMICOLON ;
idorword : IDENTIFIER | WORD
structstmt : cmpdstmt | ifstmt | forstmt ;

ifstmt : ifbranch | elsebranch ;
ifbranch : IF LEFT_ROUND_PARANTHESIS condition RIGHT_ROUND_PARANTHESIS THEN cmpdstmt ;
elsebranch : IF LEFT_ROUND_PARANTHESIS condition RIGHT_ROUND_PARANTHESIS THEN cmpdstmt ELSE cmpdstmt ;
forstmt : FOR LEFT_ROUND_PARANTHESIS assignmstmt condition SEMICOLON idorarray ASSIGNMENT expression RIGHT_ROUND_PARANTHESIS cmpdstmt ;
condition : expression relation expression ;
relation : SMALLER | SMALLER_OR_EQUAL | BIGGER | BIGGER_OR_EQUAL | EQUAL | DIFFERENT ;
%%

yyerror(char *s)
{
  printf("%s\n", s);
}

extern FILE *yyin;

main(int argc, char **argv)
{
  if(argc>1) yyin = fopen(argv[1], "r");
  if((argc>2)&&(!strcmp(argv[2],"-d"))) yydebug = 1;
  if(!yyparse()) fprintf(stderr,"\tO.K.\n");
}

