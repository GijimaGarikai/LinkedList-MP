import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

/**
 * Circularly doubly-linked list.
 * @author Sam Rebelsky
 * @author Garikai
 */
public class SimpleCDLL<T> implements SimpleList<T> {

  // +--------+------------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * Counter of all changes made by an iterator
   */
  private int iterChanges = 0;

  /**
   * Dummy node we will reference
   */
  private Node2<T> dummy;

  /**
   * The number of values in the list.
   */
  private int size;

  // +--------------+------------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create an empty list.
   */
  public SimpleCDLL() {
    this.dummy = new Node2<>(null);
    this.dummy.next = this.dummy;
    this.dummy.prev = this.dummy;
    this.size = 0;
  } // SimpleCDLL




  // +-----------+---------------------------------------------------------
  // | Iterators |
  // +-----------+

  public Iterator<T> iterator() {
    return listIterator();
  } // iterator()

  public ListIterator<T> listIterator() {
    return new ListIterator<T>() {

      /**
       * The position in the list of the next value to be returned.
       * Included because ListIterators must provide nextIndex and
       * prevIndex.
       */
      int pos = 0;

      /**
       * The cursor is between neighboring values, so we start links
       * to the previous and next value..
       */
      
      Node2<T> prev = SimpleCDLL.this.dummy;
      Node2<T> next = SimpleCDLL.this.dummy.next;

      /**
       * The node to be updated by remove or set.  Has a value of
       * null when there is no such value.
       */
      Node2<T> update = null;

      /**
       * Counter that tracks number of changes made since iterator was created
       */
      int myChanges = SimpleCDLL.this.iterChanges;

      public boolean hasNext() {
        this.failFast();
        return (this.pos < SimpleCDLL.this.size);
      } // hasNext()

      public T next() {
        this.failFast();
        if (!this.hasNext()) {
          throw new NoSuchElementException();
         } // if
        // Identify the node to update
        this.update = this.next;
        // Advance the cursor
        this.prev = this.next;
        this.next = this.next.next;
        // Note the movement
        this.pos++;
        // And return the value
        return this.update.value;
       } // next()

      public boolean hasPrevious() {
        this.failFast();
        return (this.pos > 0);
      } // hasPrevious()

      public T previous() {
        this.failFast();
        if (!hasPrevious()) {
          throw new NoSuchElementException();
        } // if
        // move the cursor back, adjausting next to prev and prev to prev.prev
        this.update = this.prev;
        this.next = this.prev;
        this.prev = this.prev.prev;
        this.pos--;
        return this.next.value;
      } //previous()

      public int nextIndex() {
        this.failFast();
        return this.pos;
      } // nextIndex()


      public int previousIndex() {
        this.failFast();
        return this.pos - 1;
      } // previousIndex()

      public void remove() {
        this.failFast();
        if (this.update == null) {
          throw new IllegalStateException();
        } // if
        // reached point by call to previous()
        if (this.next == this.update) {
          this.next = this.update.next;
        } // if
        // reached point by call to next()
        if (this.prev == this.update) {
          this.prev = this.update.prev;
          this.pos--;
        } // if
        this.update.remove();
        SimpleCDLL.this.size--;
        SimpleCDLL.this.iterChanges++;
        this.myChanges++;
        this.update = null;
      } // remove()

      public void set(T val) {
        this.failFast();
        if (update == null) {
          throw new IllegalStateException();
        } // if
        update.value = val;
        update = null;

      } // set(T val)

      public void add(T val) {
        this.failFast();
        this.prev = this.prev.insertAfter(val);
        this.update = null;
        SimpleCDLL.this.size++;
        SimpleCDLL.this.iterChanges++;
        this.myChanges++;
        this.pos++;
      } // add(T val)

      private void failFast() {
        if (this.myChanges != SimpleCDLL.this.iterChanges) {
          throw new ConcurrentModificationException();
        }// if
      } // failFast()
    };
  }
}