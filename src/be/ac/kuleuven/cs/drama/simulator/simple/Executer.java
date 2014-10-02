/**
 *
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/Executer.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 *
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 *
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

/**
 * The executer is a simpel batch thread that
 * executes Jobs in a JobList.
 * 
 * @version 1.0.0 08/29/2000
 * @author  Tom Schrijvers
 */

public class Executer extends Thread {

   private final JobList _jobList;

   public Executer() {
      super();
      _jobList = new JobList();
   }

   /**
    * Execute all job in the list.
    * If a job throws a Throwable, 
    * it will be ignored.
    */
   public void run() {
      while (true) {
         Job job = _jobList.next();

         try {
            job.execute();
         } catch (Throwable t) {
            System.err.println("Onverwachte fout in Executer: " + t);
         }

      }

   }

   public void add(Job job) {
      _jobList.add(job);
   }

   public void clear() {
      _jobList.clear();
   }

}
