/*
 * Project Ren @ 2018
 * Rinkako, Ariana, Gordan. SYSU SDCS.
 */
package org.sysu.renResourcing;

import org.sysu.renResourcing.basic.ObservableMessage;
import org.sysu.renResourcing.basic.enums.TrackerPhase;
import org.sysu.renResourcing.context.ResourcingContext;
import org.sysu.renResourcing.utility.LogUtil;

import java.util.Observable;
import java.util.Observer;

/**
 * Author: Rinkako
 * Date  : 2018/2/2
 * Usage : A tracker is used to trace its binding resourcing service
 *         executing situation and control the service life-cycle.
 *         Tracker is running asynchronously, and when it is needed,
 *         it will notify the main scheduler about the changes.
 */
final class RTracker extends Observable implements Observer, Runnable {

    /**
     * Create a new tracker.
     */
    RTracker(ResourcingContext context) {
        this.context = context;
        this.phase = TrackerPhase.Ready;
    }

    /**
     * Get binding tracing context.
     *
     * @return {@code ResourcingContext} instance
     */
    public ResourcingContext getContext() {
        return this.context;
    }

    /**
     * Get current phase of this tracker.
     *
     * @return {@code TrackerPhase} enum
     */
    public TrackerPhase getPhase() {
        return this.phase;
    }

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        try {
            this.ActualRun();
        }
        catch (Exception ex) {
            LogUtil.Log(String.format("Tracker(%s) exception occurred, %s", this.context.getRstid(), ex),
                    RTracker.class.getName(), LogUtil.LogLevelType.ERROR, this.context.getRtid());
            this.setChanged();
            ObservableMessage obm = new ObservableMessage(GlobalContext.OBSERVABLE_NOTIFY_EXCEPTION);
            obm.AddPayload("message", ex.toString());
            this.notifyObservers(obm);
        }
    }

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's
     * observers notified of the change.
     *
     * @param o   the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code>
     */
    @Override
    public void update(Observable o, Object arg) {

    }

    /**
     * Actually run the tracker and handle resourcing request.
     */
    private void ActualRun() {
        switch (this.context.getService()) {
            case SubmitResourcingTask:
                break;
        }
    }

    /**
     * Tracker binding context.
     */
    private ResourcingContext context;

    /**
     * Current tracker phase.
     */
    private TrackerPhase phase;
}
