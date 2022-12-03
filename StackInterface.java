// 
//  Name:		Pham, Vinh 
//  Project:	4
//  Due:		21 November 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//    The stack interface and the java doc describing the methods
//    
// 

public interface StackInterface<T> {

	/** Adds a new entry to the top of the stack
	 * 
	 * @param newEntry is the new entry
	 */
	public void push(T newEntry);

	/** Removes and returns the stack's top entry
	 * 
	 * @return the stack's top entry.
	 * @throws EmptyStackException an exception if the stack is empty before the operation
	 */
	public T pop();

	/** Retrieves the stack's top entry without changing the stack in any way
	 * 
	 * @return the stack's top entry
	 * @throws EmptyStackException if the stack is empty
	 */
	public T peek();

	/** Detects whether the stack is empty
	 * 
	 * @return true if the stack is empty
	 */
	public boolean isEmpty();

	/** 
	 * Removes all entries from the stack
	 */
	public void clear();

	
}