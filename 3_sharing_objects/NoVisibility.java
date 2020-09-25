/*
Two threads, the main thread and the reader thread, access the shared variables
ready and number. The main thread starts the reader thread and then sets number
to 42 and ready to true. The reader thread spins until it sees ready is true,
and then prints out number. This is all static code.

(Incidentally, this is a good example of how to code an experiment with two
threads sharing data. It avoids static/non-static issues.)
*/

public class NoVisibility {
  // variables that will be shared by threads
  private static boolean ready;
  private static int number;

  /*
  Because this inner class has access to the variables of the outer class, when
  ReaderThread is run in another thread it will be able to read them.
  */
  private static class ReaderThread extends Thread {
    // override run
    public void run() {
      // the task of the reader thread
      while (!ready) Thread.yield();
      System.out.println(number); // could print 42
    }
  }

  // main method of the main thread
  public static void main(String[] args) {
    new ReaderThread().start();
    // the task of the main thread
    number = 42;
    ready = true;
  }

}
/*
While it may seem obvious that NoVisibility will print 42, it is in fact poss-
ible that it will print zero or never terminate at all! Because it does not use
adequate synchronization, there is no guarantee that the value of ready and num-
ber written by the main thread will be visible to the reader thread.
*/
