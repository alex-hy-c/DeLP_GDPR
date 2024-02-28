package DeLP_GDPR.dung.reasoner;



import java.util.Collection;

/**
 * A signatures lists the atomic language structures for some language. It is
 * represented by a (multi-)set of formulas.
 * 

 * 
 */
public interface Signature extends Cloneable {
	/**
	 * Checks whether this signature is a sub-signature of the given signature, i.e.
	 * whether each logical expression expressible with this signature is also
	 * expressible with the given signature.
	 * 
	 * @param other a signature.
	 * @return "true" iff this signature is a sub-signature of the given one.
	 */
	public boolean isSubSignature(Signature other);

	/**
	 * Checks whether this signature has common elements with the given signature,
	 * i.e. whether there are logical expressions expressible with this signature
	 * that are also expressible with the given signature.
	 * 
	 * @param other a signature.
	 * @return "true" iff this signature is overlapping with the given one.
	 */
	public boolean isOverlappingSignature(Signature other);

	/**
	 * Adds the elements of the given signature to this signature.
	 * 
	 * @param other a signature.
	 */
	public void addSignature(Signature other);

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode();

	@Override
	public String toString();

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj);

	/**
	 * Adds the given formula to this signature.
	 * 
	 * @param obj some object
	 * 
	 */
	public void add(Object obj);

	/**
	 * Adds all elements of this collection to this signature.
	 * 
	 * @param c a collection
	 * 
	 */
	public void addAll(Collection<?> c);
	
	/**
	 * Adds the given formulas to the signature.
	 * 
	 * @param objects some objects to be added 
	 */
	public void add(Object... objects);
	
	/**
	 * Returns true if this signature is empty.
	 * 
	 * @return true if this signature is empty.
	 */
	public boolean isEmpty();

	/**
	 * Removes the given formula from this signature, if it is present (optional
	 * operation).
	 * 
	 * @param obj some object
	 */
	public void remove(Object obj);

	/**
	 * Removes all of this signature elements that are also contained in the
	 * specified collection (optional operation). After this call returns, this
	 * signature will contain no elements in common with the specified collection.
	 * 
	 * @param c a collection of objects
	 */
	public void removeAll(Collection<?> c);
	
	/**
	 * Removes all elements of this signature. After this call returns, this
	 * signature will contain no elements.
	 */
	public void clear();
	
	/**
	 * clones signature
	 * @return clone
	 */
	public Signature clone();

}
