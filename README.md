
# Linked List Mini Project

This project is an implementation of a doubly-linked list in Java that utilizes a dummy node and circular linking to simplify the structure and operations of the list. The implementation covers basic functionalities expected from a doubly-linked list, such as addition, removal, and iteration over elements.

## Features

- **Dummy Node**: Incorporates a dummy node that acts as both the head and tail of the list.
- **Circular Linking**: Ensures that the list's last node points back to the first node through the dummy node, facilitating seamless traversal.
- **Iterator Support**: Includes full support for both forward and backward iteration with list iterators that comply with Java's `ListIterator` interface.
- **Generic Implementation**: Allows for storing elements of any type, making the list versatile for various use cases.


## Discussion

Using a dummy node and circular linking in this implementation simplified the code by eliminating the need for handling special cases when at the start/end of the list. The dummy node simplifies adding and removing nodes, as there's no need to specifically manage `head` or `tail` pointers or check for an empty list. The circular linking, where the last node points back to the first through the dummy, allowed easy traversal and iteration without special conditions for detecting list boundaries. As every node has a next and previous link, we have less risk of bugs associated with edge cases in list operations.

## Authors

- Garikai Gijima | Sam Rebelsky

## Acknowledgements

- Sam Rebelsky: Provided the started code and instructions