package fr.loria.mosel.rodin.eb2rc.core.helper;

import ie.nuim.cs.eventb.ASTextension.bParameter;

import java.util.ArrayList;

import org.eventb.core.ast.Assignment;
import org.eventb.core.ast.FormulaFactory;
import org.eventb.core.ast.FreeIdentifier;
import org.eventb.core.ast.IParseResult;
import org.eventb.core.ast.ITypeEnvironment;
import org.eventb.core.ast.LanguageVersion;
import org.eventb.core.ast.Predicate;
import org.eventb.core.ast.Type;

public class IRodinParsingHelper {
	public static bParameter[] calculateFreeIdentifier(Predicate pred,
			ITypeEnvironment env) {
		ArrayList<bParameter> varList = new ArrayList<bParameter>();

		// note: getFreeIdentifier only returns a list with distinct free identifiers.
		for (FreeIdentifier id : pred.getFreeIdentifiers()) {
			Type varType = env.getType(id.getName());
			if (varType == null) {
				// if goes here, which means env dont contain type info for this
				// var
				// so, we make our last attempt to retrieve the type from pred.
				varType = id.getType();
			}
			varList.add(new bParameter(id.getName(), varType));
		}

		return varList.toArray(new bParameter[varList.size()]);
	}




	public static Predicate typeCheckedParsing(String s, FormulaFactory ff, ITypeEnvironment env) throws Exception{
		IParseResult result;
		result = ff.parsePredicate(s, LanguageVersion.LATEST, null);
		
		Predicate pred = result.getParsedPredicate();
		pred.typeCheck(env);
		pred.getSyntaxTree();	//only for instantiate quantifier, i.e., rewrite the boundIdentifier to boundIdDecl
		
		return pred;
		
		
	}
	
	public static Assignment typeCheckedParsingAssignment(String s, FormulaFactory ff, ITypeEnvironment env) throws Exception{
		IParseResult result;
		result = ff.parseAssignment(s,LanguageVersion.LATEST, null);
		
		Assignment pred = result.getParsedAssignment();
		pred.typeCheck(env);
		pred.getSyntaxTree();	//only for instantiate quantifier, i.e., rewrite the boundIdentifier to boundIdDecl
		
		return pred;
		
		
	}
}