package DeLP_GDPR.logics.commons.syntax.interfaces;



import java.util.List;

import DeLP_GDPR.logics.commons.error.LanguageException;
import DeLP_GDPR.logics.commons.syntax.Sort;

/**
 * This interface defines method which are given by every TypedStructure like a
 * Predicate or an Functor. It has an name, an arity (argument count) and a list
 * of Sort representing the types for the different arguments.
 */
public interface TypedStructure {
	
	/** @return the unique name of the structure */
	String getName();
	
	/**
	 * Changes the name of the Structure
	 * @param name	The new name of the structure
	 * @throws LanguageException if the new name is illegal in the language.
	 */
	void setName(String name) throws LanguageException;
	
	/** @return the arity of this structure */
	int getArity();
	
	/** 
	 * @return 	An unmodifiable list which length equals the 
	 * 			arity of the predicate if the structure isComplete().
	 */
	List<Sort> getArgumentTypes();
	
	/**
	 * Adds the given Sort as argument type to the typed Structure
	 * @param argType	The Sort descibing the argument type
	 * @throws LanguageException if there is some language issue
	 */
	void addArgumentType(Sort argType) throws LanguageException;
	
	/**
	 * Removes the argument type at the specified index
	 * @param index	The index
	 * @return A reference to the sort that has been remove or null if no sort has been removed.
	 * @throws LanguageException if something is wrong with the language
	 */
	Sort removeArgumentType(int index) throws LanguageException;
	
	/**
	 * Removes the given Sort from the list of argument types
	 * @param argType	The Sort which is removed
	 * @return	true if the Sort exists in the list of argument types
	 * 			and is successfully removed, false otherwise.
	 * @throws LanguageException if there is some language issue
	 */
	boolean removeArgumentType(Sort argType) throws LanguageException;
	
	/**
	 * @return true if at least one sort for an argument is not "Thing".
	 */
	boolean isTyped();
	
	/**
	 * @return 	true if the arity of this structure matches the length of it's arguments,
	 * 			false if the arity is bigger than the length of it's arguments.
	 */
	boolean isComplete();
	
	/**
	 * Creates a deep copy of this object
	 * @return	A deep copy of this object
	 */
	TypedStructure clone();
}