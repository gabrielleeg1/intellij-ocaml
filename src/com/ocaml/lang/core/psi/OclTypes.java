// This is a generated file. Not intended for manual editing.
package com.ocaml.lang.core.psi;

import com.intellij.psi.tree.IElementType;
import com.intellij.psi.PsiElement;
import com.intellij.lang.ASTNode;
import com.ocaml.lang.core.OcElementType;
import com.ocaml.lang.core.OclTokenType;
import com.ocaml.lang.core.psi.impl.*;

public interface OclTypes {

  IElementType EXPR = new OcElementType("EXPR");

  IElementType AMPERSAND = new OclTokenType("&");
  IElementType AND = new OclTokenType("and");
  IElementType ARROBASE = new OclTokenType("@");
  IElementType ARROBASE_2 = new OclTokenType("@@");
  IElementType ARROBASE_3 = new OclTokenType("@@@");
  IElementType ARROW = new OclTokenType("=>");
  IElementType AS = new OclTokenType("as");
  IElementType ASR = new OclTokenType("asr");
  IElementType ASSERT = new OclTokenType("assert");
  IElementType BACKTICK = new OclTokenType("`");
  IElementType BEGIN = new OclTokenType("begin");
  IElementType BOOL_VALUE = new OclTokenType("true");
  IElementType CARRET = new OclTokenType("\\^");
  IElementType CHAR_VALUE = new OclTokenType("CHAR_VALUE");
  IElementType CLASS = new OclTokenType("class");
  IElementType COLON = new OclTokenType(":");
  IElementType COLON_EQ = new OclTokenType(":=");
  IElementType COLON_GT = new OclTokenType(":>");
  IElementType COMMA = new OclTokenType(",");
  IElementType CONSTRAINT = new OclTokenType("constraint");
  IElementType DIRECTIVE_ELIF = new OclTokenType("#elif");
  IElementType DIRECTIVE_ELSE = new OclTokenType("#else");
  IElementType DIRECTIVE_END = new OclTokenType("#end");
  IElementType DIRECTIVE_ENDIF = new OclTokenType("#endif");
  IElementType DIRECTIVE_IF = new OclTokenType("#if");
  IElementType DO = new OclTokenType("do");
  IElementType DOLLAR = new OclTokenType("$");
  IElementType DONE = new OclTokenType("done");
  IElementType DOT = new OclTokenType(".");
  IElementType DOTDOT = new OclTokenType("..");
  IElementType DOTDOTDOT = new OclTokenType("...");
  IElementType DOWNTO = new OclTokenType("downto");
  IElementType ELSE = new OclTokenType("else");
  IElementType END = new OclTokenType("end");
  IElementType ENDIF = new OclTokenType("endif");
  IElementType EQ = new OclTokenType("=");
  IElementType EQEQ = new OclTokenType("==");
  IElementType EQEQEQ = new OclTokenType("===");
  IElementType EXCEPTION = new OclTokenType("exception");
  IElementType EXCLAMATION_MARK = new OclTokenType("!");
  IElementType EXTERNAL = new OclTokenType("external");
  IElementType FLOAT_VALUE = new OclTokenType("FLOAT_VALUE");
  IElementType FOR = new OclTokenType("for");
  IElementType FUN = new OclTokenType("fun");
  IElementType FUNCTION = new OclTokenType("function");
  IElementType FUNCTOR = new OclTokenType("functor");
  IElementType GT = new OclTokenType(">");
  IElementType GT_OR_EQUAL = new OclTokenType(">=");
  IElementType IF = new OclTokenType("if");
  IElementType IN = new OclTokenType("in");
  IElementType INCLUDE = new OclTokenType("include");
  IElementType INHERIT = new OclTokenType("inherit");
  IElementType INITIALIZER = new OclTokenType("initializer");
  IElementType INT_VALUE = new OclTokenType("INT_VALUE");
  IElementType LAND = new OclTokenType("land");
  IElementType LARRAY = new OclTokenType("[|");
  IElementType LAZY = new OclTokenType("lazy");
  IElementType LBRACE = new OclTokenType("{");
  IElementType LBRACKET = new OclTokenType("[");
  IElementType LEFT_ARROW = new OclTokenType("<-");
  IElementType LET = new OclTokenType("let");
  IElementType LIDENT = new OclTokenType("LIDENT");
  IElementType LOR = new OclTokenType("lor");
  IElementType LPAREN = new OclTokenType("(");
  IElementType LSL = new OclTokenType("lsl");
  IElementType LSR = new OclTokenType("lsr");
  IElementType LT = new OclTokenType("<");
  IElementType LT_OR_EQUAL = new OclTokenType("<=");
  IElementType LXOR = new OclTokenType("lxor");
  IElementType L_AND = new OclTokenType("&&");
  IElementType L_OR = new OclTokenType("||");
  IElementType MATCH = new OclTokenType("match");
  IElementType METHOD = new OclTokenType("method");
  IElementType MINUS = new OclTokenType("-");
  IElementType MINUSDOT = new OclTokenType("-.");
  IElementType MOD = new OclTokenType("mod");
  IElementType MODULE = new OclTokenType("module");
  IElementType MULTI_COMMENT = new OclTokenType("MULTI_COMMENT");
  IElementType MUTABLE = new OclTokenType("mutable");
  IElementType NEW = new OclTokenType("new");
  IElementType NONE = new OclTokenType("None");
  IElementType NONREC = new OclTokenType("nonrec");
  IElementType NOT_EQ = new OclTokenType("!=");
  IElementType NOT_EQEQ = new OclTokenType("!==");
  IElementType OBJECT = new OclTokenType("object");
  IElementType OF = new OclTokenType("of");
  IElementType OPEN = new OclTokenType("open");
  IElementType OPTION = new OclTokenType("option");
  IElementType OP_STRUCT_DIFF = new OclTokenType("<>");
  IElementType OR = new OclTokenType("or");
  IElementType PERCENT = new OclTokenType("%");
  IElementType PIPE = new OclTokenType("|");
  IElementType PIPE_FORWARD = new OclTokenType("|>");
  IElementType PLUS = new OclTokenType("+");
  IElementType PLUSDOT = new OclTokenType("+.");
  IElementType POLY_VARIANT = new OclTokenType("POLY_VARIANT");
  IElementType PRI = new OclTokenType("pri");
  IElementType PRIVATE = new OclTokenType("private");
  IElementType PUB = new OclTokenType("pub");
  IElementType QUESTION_MARK = new OclTokenType("?");
  IElementType RAISE = new OclTokenType("raise");
  IElementType RARRAY = new OclTokenType("|]");
  IElementType RAW = new OclTokenType("raw");
  IElementType RBRACE = new OclTokenType("}");
  IElementType RBRACKET = new OclTokenType("]");
  IElementType REC = new OclTokenType("rec");
  IElementType REF = new OclTokenType("ref");
  IElementType RIGHT_ARROW = new OclTokenType("->");
  IElementType RPAREN = new OclTokenType(")");
  IElementType SEMI = new OclTokenType(";");
  IElementType SEMISEMI = new OclTokenType(";;");
  IElementType SHARP = new OclTokenType("#");
  IElementType SHARPSHARP = new OclTokenType("##");
  IElementType SHORTCUT = new OclTokenType("::");
  IElementType SIG = new OclTokenType("sig");
  IElementType SINGLE_COMMENT = new OclTokenType("SINGLE_COMMENT");
  IElementType SINGLE_QUOTE = new OclTokenType("'");
  IElementType SLASH = new OclTokenType("/");
  IElementType SLASHDOT = new OclTokenType("/.");
  IElementType SOME = new OclTokenType("Some");
  IElementType STAR = new OclTokenType("*");
  IElementType STARDOT = new OclTokenType("*.");
  IElementType STRING_VALUE = new OclTokenType("STRING_VALUE");
  IElementType STRUCT = new OclTokenType("struct");
  IElementType SWITCH = new OclTokenType("switch");
  IElementType TAG_AUTO_CLOSE = new OclTokenType("/>");
  IElementType TAG_LT_SLASH = new OclTokenType("</");
  IElementType THEN = new OclTokenType("then");
  IElementType TILDE = new OclTokenType("~");
  IElementType TO = new OclTokenType("to");
  IElementType TRY = new OclTokenType("try");
  IElementType TYPE = new OclTokenType("type");
  IElementType TYPE_ARGUMENT = new OclTokenType("TYPE_ARGUMENT");
  IElementType UIDENT = new OclTokenType("UIDENT");
  IElementType UNDERSCORE = new OclTokenType("_");
  IElementType UNIT = new OclTokenType("unit");
  IElementType VAL = new OclTokenType("val");
  IElementType VIRTUAL = new OclTokenType("virtual");
  IElementType WHEN = new OclTokenType("when");
  IElementType WHILE = new OclTokenType("while");
  IElementType WITH = new OclTokenType("with");

  class Factory {
    public static PsiElement createElement(ASTNode node) {
      IElementType type = node.getElementType();
      if (type == EXPR) {
        return new OclExprImpl(node);
      }
      throw new AssertionError("Unknown element type: " + type);
    }
  }
}
