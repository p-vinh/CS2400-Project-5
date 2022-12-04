// 
//  Name:		Pham, Vinh 
//  Project:	5
//  Due:		9 December 2022 
//  Course:		cs-2400-02-f22 
// 
//  Description: 
//
//
// 

import java.util.Iterator;
public interface ListWithIteratorInterface<T> extends ListInterface<T>, Iterable<T> {
    public Iterator<T> getIterator();
}
