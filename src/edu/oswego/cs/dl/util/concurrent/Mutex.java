/*
  File: Mutex.java
 
  Originally written by Doug Lea and released into the public domain.
  This may be used for any purposes whatsoever without acknowledgment.
  Thanks for the assistance and support of Sun Microsystems Labs,
  and everyone contributing, testing, and using this code.
 
  History:
  Date       Who                What
  11Jun1998  dl               Create public version
*/

package edu.oswego.cs.dl.util.concurrent;

/**
 * A simple non-reentrant mutual exclusion lock.
 * The lock is free upon construction. Each acquire gets the
 * lock, and each release frees it. Releasing a lock that
 * is already free has no effect. 
 * <p>
 * This implementation makes no attempt to provide any fairness
 * or ordering guarantees. If you need them, consider using one of
 * the Semaphore implementations as a locking mechanism.
 * @see Semaphore
 * <p>[<a href="http://gee.cs.oswego.edu/dl/classes/EDU/oswego/cs/dl/util/concurrent/intro.html"> Introduction to this package. </a>]
**/

public class Mutex implements Sync {

   /** The lock status **/
   protected boolean inuse_ = false;

   public void acquire() throws InterruptedException {
      if (Thread.interrupted()) throw new InterruptedException();

      synchronized (this) {
         try {
            while (inuse_) wait();
            inuse_ = true;
         } catch (InterruptedException ex) {
            notify();
            throw ex;
         }

      }

   }

   public synchronized void release() {
      inuse_ = false;
      notify();
   }


   public boolean attempt(long msecs) throws InterruptedException {
      if (Thread.interrupted()) throw new InterruptedException();

      synchronized (this) {
         if (!inuse_) {
            inuse_ = true;
            return true;
         } else if (msecs <= 0)
            return false;
         else {
            long waitTime = msecs;
            long start = System.currentTimeMillis();

            try {
               for (;;) {
                  wait(waitTime);

                  if (!inuse_) {
                     inuse_ = true;
                     return true;
                  } else {
                     waitTime = msecs - (System.currentTimeMillis() - start);

                     if (waitTime <= 0)
                        return false;
                  }

               }
            } catch (InterruptedException ex) {
               notify();
               throw ex;
            }

         }

      }

   }

}

