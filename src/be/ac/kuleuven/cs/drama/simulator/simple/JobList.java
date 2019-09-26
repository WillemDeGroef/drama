/**
 * CVS: $Header: /export/home0/cvsroot/socsg/DRAMA/Sources/be/ac/kuleuven/cs/drama/simulator/simple/JobList.java,v 1.1.1.1 2001/09/07 09:41:38 dirkw Exp $
 * <p>
 * (C) 2000
 * Katholieke Universiteit Leuven
 * Developed at Dept. Computer Science
 */
package be.ac.kuleuven.cs.drama.simulator.simple;

import java.util.List;
import java.util.ArrayList;

public class JobList {

    private List _jobs;

    public JobList() {
        _jobs = new ArrayList();
    }

    public synchronized void add(Job job) {
        _jobs.add(job);
        notify();
    }

    public synchronized boolean hasNext() {
        //fixme: maybe bug code
        return !_jobs.isEmpty();
    }

    public synchronized Job next() {

        while (_jobs.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException ie) {
            }


        }

        return (Job) _jobs.remove(0);

    }

    public synchronized void clear() {
        _jobs.clear();
    }

}
