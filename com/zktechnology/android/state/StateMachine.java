package com.zktechnology.android.state;

import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import com.zktechnology.android.state.StateMachine;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;
import java.util.function.Predicate;

public class StateMachine {
    public static final boolean HANDLED = true;
    public static final boolean NOT_HANDLED = false;
    private static final int SM_INIT_CMD = -2;
    private static final int SM_QUIT_CMD = -1;
    /* access modifiers changed from: private */
    public String mName;
    /* access modifiers changed from: private */
    public SmHandler mSmHandler;
    /* access modifiers changed from: private */
    public HandlerThread mSmThread;

    /* access modifiers changed from: protected */
    public String getLogRecString(Message message) {
        return "";
    }

    /* access modifiers changed from: protected */
    public String getWhatToString(int i) {
        return null;
    }

    /* access modifiers changed from: protected */
    public void haltedProcessMessage(Message message) {
    }

    /* access modifiers changed from: protected */
    public void onHalting() {
    }

    /* access modifiers changed from: protected */
    public void onPostHandleMessage(Message message) {
    }

    /* access modifiers changed from: protected */
    public void onPreHandleMessage(Message message) {
    }

    /* access modifiers changed from: protected */
    public void onQuitting() {
    }

    /* access modifiers changed from: protected */
    public boolean recordLogRec(Message message) {
        return true;
    }

    public static class LogRec {
        private IState mDstState;
        private String mInfo;
        private IState mOrgState;
        private StateMachine mSm;
        private IState mState;
        private long mTime;
        private int mWhat;

        LogRec(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            update(stateMachine, message, str, iState, iState2, iState3);
        }

        public void update(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            this.mSm = stateMachine;
            this.mTime = System.currentTimeMillis();
            this.mWhat = message != null ? message.what : 0;
            this.mInfo = str;
            this.mState = iState;
            this.mOrgState = iState2;
            this.mDstState = iState3;
        }

        public long getTime() {
            return this.mTime;
        }

        public long getWhat() {
            return (long) this.mWhat;
        }

        public String getInfo() {
            return this.mInfo;
        }

        public IState getState() {
            return this.mState;
        }

        public IState getDestState() {
            return this.mDstState;
        }

        public IState getOriginalState() {
            return this.mOrgState;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("time=");
            Calendar instance = Calendar.getInstance();
            instance.setTimeInMillis(this.mTime);
            sb.append(String.format("%tm-%td %tH:%tM:%tS.%tL", new Object[]{instance, instance, instance, instance, instance, instance}));
            sb.append(" processed=");
            IState iState = this.mState;
            String str = "<null>";
            sb.append(iState == null ? str : iState.getName());
            sb.append(" org=");
            IState iState2 = this.mOrgState;
            sb.append(iState2 == null ? str : iState2.getName());
            sb.append(" dest=");
            IState iState3 = this.mDstState;
            if (iState3 != null) {
                str = iState3.getName();
            }
            sb.append(str);
            sb.append(" what=");
            StateMachine stateMachine = this.mSm;
            String whatToString = stateMachine != null ? stateMachine.getWhatToString(this.mWhat) : "";
            if (TextUtils.isEmpty(whatToString)) {
                sb.append(this.mWhat);
                sb.append("(0x");
                sb.append(Integer.toHexString(this.mWhat));
                sb.append(")");
            } else {
                sb.append(whatToString);
            }
            if (!TextUtils.isEmpty(this.mInfo)) {
                sb.append(" ");
                sb.append(this.mInfo);
            }
            return sb.toString();
        }
    }

    private static class LogRecords {
        private static final int DEFAULT_SIZE = 20;
        private int mCount;
        private boolean mLogOnlyTransitions;
        /* access modifiers changed from: private */
        public Vector<LogRec> mLogRecVector;
        /* access modifiers changed from: private */
        public int mMaxSize;
        private int mOldestIndex;

        private LogRecords() {
            this.mLogRecVector = new Vector<>();
            this.mMaxSize = 20;
            this.mOldestIndex = 0;
            this.mCount = 0;
            this.mLogOnlyTransitions = false;
        }

        /* access modifiers changed from: package-private */
        public synchronized void setSize(int i) {
            this.mMaxSize = i;
            this.mOldestIndex = 0;
            this.mCount = 0;
            this.mLogRecVector.clear();
        }

        /* access modifiers changed from: package-private */
        public synchronized void setLogOnlyTransitions(boolean z) {
            this.mLogOnlyTransitions = z;
        }

        /* access modifiers changed from: package-private */
        public synchronized boolean logOnlyTransitions() {
            return this.mLogOnlyTransitions;
        }

        /* access modifiers changed from: package-private */
        public synchronized int size() {
            return this.mLogRecVector.size();
        }

        /* access modifiers changed from: package-private */
        public synchronized int count() {
            return this.mCount;
        }

        /* access modifiers changed from: package-private */
        public synchronized void cleanup() {
            this.mLogRecVector.clear();
        }

        /* access modifiers changed from: package-private */
        public synchronized LogRec get(int i) {
            int i2 = this.mOldestIndex + i;
            int i3 = this.mMaxSize;
            if (i2 >= i3) {
                i2 -= i3;
            }
            if (i2 >= size()) {
                return null;
            }
            return this.mLogRecVector.get(i2);
        }

        /* access modifiers changed from: package-private */
        public synchronized void add(StateMachine stateMachine, Message message, String str, IState iState, IState iState2, IState iState3) {
            this.mCount++;
            if (this.mLogRecVector.size() < this.mMaxSize) {
                this.mLogRecVector.add(new LogRec(stateMachine, message, str, iState, iState2, iState3));
            } else {
                LogRec logRec = this.mLogRecVector.get(this.mOldestIndex);
                int i = this.mOldestIndex + 1;
                this.mOldestIndex = i;
                if (i >= this.mMaxSize) {
                    this.mOldestIndex = 0;
                }
                logRec.update(stateMachine, message, str, iState, iState2, iState3);
            }
        }
    }

    private static class SmHandler extends Handler {
        private static final Object mSmHandlerObj = new Object();
        /* access modifiers changed from: private */
        public boolean mDbg;
        /* access modifiers changed from: private */
        public final ArrayList<Message> mDeferredMessages;
        /* access modifiers changed from: private */
        public State mDestState;
        /* access modifiers changed from: private */
        public final HaltingState mHaltingState;
        private boolean mHasQuit;
        private State mInitialState;
        private boolean mIsConstructionCompleted;
        /* access modifiers changed from: private */
        public final LogRecords mLogRecords;
        private Message mMsg;
        private final QuittingState mQuittingState;
        /* access modifiers changed from: private */
        public StateMachine mSm;
        private final HashMap<State, StateInfo> mStateInfo;
        /* access modifiers changed from: private */
        public StateInfo[] mStateStack;
        /* access modifiers changed from: private */
        public int mStateStackTopIndex;
        private StateInfo[] mTempStateStack;
        private int mTempStateStackCount;
        private boolean mTransitionInProgress;

        private static class StateInfo {
            boolean active;
            StateInfo parentStateInfo;
            State state;

            private StateInfo() {
            }

            public String toString() {
                StringBuilder append = new StringBuilder().append("state=").append(this.state.getName()).append(",active=").append(this.active).append(",parent=");
                StateInfo stateInfo = this.parentStateInfo;
                return append.append(stateInfo == null ? "null" : stateInfo.state.getName()).toString();
            }
        }

        private class HaltingState extends State {
            private HaltingState() {
            }

            public boolean processMessage(Message message) {
                SmHandler.this.mSm.haltedProcessMessage(message);
                return true;
            }
        }

        private static class QuittingState extends State {
            public boolean processMessage(Message message) {
                return false;
            }

            private QuittingState() {
            }
        }

        public final void handleMessage(Message message) {
            StateMachine stateMachine;
            if (!this.mHasQuit) {
                if (!(this.mSm == null || message.what == -2 || message.what == -1)) {
                    this.mSm.onPreHandleMessage(message);
                }
                if (this.mDbg) {
                    this.mSm.log("handleMessage: E msg.what=" + message.what);
                }
                this.mMsg = message;
                State state = null;
                if (this.mIsConstructionCompleted || message.what == -1) {
                    state = processMsg(message);
                } else if (this.mMsg.what == -2 && this.mMsg.obj == mSmHandlerObj) {
                    this.mIsConstructionCompleted = true;
                    invokeEnterMethods(0);
                } else {
                    throw new RuntimeException("StateMachine.handleMessage: The start method not called, received msg: " + message);
                }
                performTransitions(state, message);
                if (this.mDbg && (stateMachine = this.mSm) != null) {
                    stateMachine.log("handleMessage: X");
                }
                if (this.mSm != null && message.what != -2 && message.what != -1) {
                    this.mSm.onPostHandleMessage(message);
                }
            }
        }

        private void performTransitions(State state, Message message) {
            State state2 = this.mStateStack[this.mStateStackTopIndex].state;
            boolean z = this.mSm.recordLogRec(this.mMsg) && message.obj != mSmHandlerObj;
            if (this.mLogRecords.logOnlyTransitions()) {
                if (this.mDestState != null) {
                    LogRecords logRecords = this.mLogRecords;
                    StateMachine stateMachine = this.mSm;
                    Message message2 = this.mMsg;
                    logRecords.add(stateMachine, message2, stateMachine.getLogRecString(message2), state, state2, this.mDestState);
                }
            } else if (z) {
                LogRecords logRecords2 = this.mLogRecords;
                StateMachine stateMachine2 = this.mSm;
                Message message3 = this.mMsg;
                logRecords2.add(stateMachine2, message3, stateMachine2.getLogRecString(message3), state, state2, this.mDestState);
            }
            State state3 = this.mDestState;
            if (state3 != null) {
                while (true) {
                    if (this.mDbg) {
                        this.mSm.log("handleMessage: new destination call exit/enter");
                    }
                    StateInfo stateInfo = setupTempStateStackWithStatesToEnter(state3);
                    this.mTransitionInProgress = true;
                    invokeExitMethods(stateInfo);
                    invokeEnterMethods(moveTempStateStackToStateStack());
                    moveDeferredMessageAtFrontOfQueue();
                    State state4 = this.mDestState;
                    if (state3 == state4) {
                        break;
                    }
                    state3 = state4;
                }
                this.mDestState = null;
            }
            if (state3 == null) {
                return;
            }
            if (state3 == this.mQuittingState) {
                this.mSm.onQuitting();
                cleanupAfterQuitting();
            } else if (state3 == this.mHaltingState) {
                this.mSm.onHalting();
            }
        }

        private void cleanupAfterQuitting() {
            if (this.mSm.mSmThread != null) {
                getLooper().quit();
                HandlerThread unused = this.mSm.mSmThread = null;
            }
            SmHandler unused2 = this.mSm.mSmHandler = null;
            this.mSm = null;
            this.mMsg = null;
            this.mLogRecords.cleanup();
            this.mStateStack = null;
            this.mTempStateStack = null;
            this.mStateInfo.clear();
            this.mInitialState = null;
            this.mDestState = null;
            this.mDeferredMessages.clear();
            this.mHasQuit = true;
        }

        /* access modifiers changed from: private */
        public void completeConstruction() {
            if (this.mDbg) {
                this.mSm.log("completeConstruction: E");
            }
            int i = 0;
            for (StateInfo next : this.mStateInfo.values()) {
                int i2 = 0;
                while (next != null) {
                    next = next.parentStateInfo;
                    i2++;
                }
                if (i < i2) {
                    i = i2;
                }
            }
            if (this.mDbg) {
                this.mSm.log("completeConstruction: maxDepth=" + i);
            }
            this.mStateStack = new StateInfo[i];
            this.mTempStateStack = new StateInfo[i];
            setupInitialStateStack();
            sendMessageAtFrontOfQueue(obtainMessage(-2, mSmHandlerObj));
            if (this.mDbg) {
                this.mSm.log("completeConstruction: X");
            }
        }

        private State processMsg(Message message) {
            StateInfo stateInfo = this.mStateStack[this.mStateStackTopIndex];
            if (this.mDbg) {
                this.mSm.log("processMsg: " + stateInfo.state.getName());
            }
            if (isQuit(message)) {
                transitionTo(this.mQuittingState);
            } else {
                while (true) {
                    if (stateInfo.state.processMessage(message)) {
                        break;
                    }
                    stateInfo = stateInfo.parentStateInfo;
                    if (stateInfo == null) {
                        this.mSm.unhandledMessage(message);
                        break;
                    } else if (this.mDbg) {
                        this.mSm.log("processMsg: " + stateInfo.state.getName());
                    }
                }
            }
            if (stateInfo != null) {
                return stateInfo.state;
            }
            return null;
        }

        private void invokeExitMethods(StateInfo stateInfo) {
            while (true) {
                int i = this.mStateStackTopIndex;
                if (i >= 0) {
                    StateInfo[] stateInfoArr = this.mStateStack;
                    if (stateInfoArr[i] != stateInfo) {
                        State state = stateInfoArr[i].state;
                        if (this.mDbg) {
                            this.mSm.log("invokeExitMethods: " + state.getName());
                        }
                        state.exit();
                        this.mStateStack[this.mStateStackTopIndex].active = false;
                        this.mStateStackTopIndex--;
                    } else {
                        return;
                    }
                } else {
                    return;
                }
            }
        }

        private void invokeEnterMethods(int i) {
            int i2 = i;
            while (true) {
                int i3 = this.mStateStackTopIndex;
                if (i2 <= i3) {
                    if (i == i3) {
                        this.mTransitionInProgress = false;
                    }
                    if (this.mDbg) {
                        this.mSm.log("invokeEnterMethods: " + this.mStateStack[i2].state.getName());
                    }
                    this.mStateStack[i2].state.enter();
                    this.mStateStack[i2].active = true;
                    i2++;
                } else {
                    this.mTransitionInProgress = false;
                    return;
                }
            }
        }

        private void moveDeferredMessageAtFrontOfQueue() {
            for (int size = this.mDeferredMessages.size() - 1; size >= 0; size--) {
                Message message = this.mDeferredMessages.get(size);
                if (this.mDbg) {
                    this.mSm.log("moveDeferredMessageAtFrontOfQueue; what=" + message.what);
                }
                sendMessageAtFrontOfQueue(message);
            }
            this.mDeferredMessages.clear();
        }

        private int moveTempStateStackToStateStack() {
            int i = this.mStateStackTopIndex + 1;
            int i2 = i;
            for (int i3 = this.mTempStateStackCount - 1; i3 >= 0; i3--) {
                if (this.mDbg) {
                    this.mSm.log("moveTempStackToStateStack: i=" + i3 + ",j=" + i2);
                }
                this.mStateStack[i2] = this.mTempStateStack[i3];
                i2++;
            }
            this.mStateStackTopIndex = i2 - 1;
            if (this.mDbg) {
                this.mSm.log("moveTempStackToStateStack: X mStateStackTop=" + this.mStateStackTopIndex + ",startingIndex=" + i + ",Top=" + this.mStateStack[this.mStateStackTopIndex].state.getName());
            }
            return i;
        }

        /* JADX WARNING: Removed duplicated region for block: B:3:0x0017  */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        private com.zktechnology.android.state.StateMachine.SmHandler.StateInfo setupTempStateStackWithStatesToEnter(com.zktechnology.android.state.State r4) {
            /*
                r3 = this;
                r0 = 0
                r3.mTempStateStackCount = r0
                java.util.HashMap<com.zktechnology.android.state.State, com.zktechnology.android.state.StateMachine$SmHandler$StateInfo> r0 = r3.mStateInfo
                java.lang.Object r4 = r0.get(r4)
                com.zktechnology.android.state.StateMachine$SmHandler$StateInfo r4 = (com.zktechnology.android.state.StateMachine.SmHandler.StateInfo) r4
            L_0x000b:
                com.zktechnology.android.state.StateMachine$SmHandler$StateInfo[] r0 = r3.mTempStateStack
                int r1 = r3.mTempStateStackCount
                int r2 = r1 + 1
                r3.mTempStateStackCount = r2
                r0[r1] = r4
                if (r4 == 0) goto L_0x0019
                com.zktechnology.android.state.StateMachine$SmHandler$StateInfo r4 = r4.parentStateInfo
            L_0x0019:
                if (r4 == 0) goto L_0x001f
                boolean r0 = r4.active
                if (r0 == 0) goto L_0x000b
            L_0x001f:
                boolean r0 = r3.mDbg
                if (r0 == 0) goto L_0x0047
                com.zktechnology.android.state.StateMachine r0 = r3.mSm
                java.lang.StringBuilder r1 = new java.lang.StringBuilder
                r1.<init>()
                java.lang.String r2 = "setupTempStateStackWithStatesToEnter: X mTempStateStackCount="
                java.lang.StringBuilder r1 = r1.append(r2)
                int r2 = r3.mTempStateStackCount
                java.lang.StringBuilder r1 = r1.append(r2)
                java.lang.String r2 = ",curStateInfo: "
                java.lang.StringBuilder r1 = r1.append(r2)
                java.lang.StringBuilder r1 = r1.append(r4)
                java.lang.String r1 = r1.toString()
                r0.log(r1)
            L_0x0047:
                return r4
            */
            throw new UnsupportedOperationException("Method not decompiled: com.zktechnology.android.state.StateMachine.SmHandler.setupTempStateStackWithStatesToEnter(com.zktechnology.android.state.State):com.zktechnology.android.state.StateMachine$SmHandler$StateInfo");
        }

        private void setupInitialStateStack() {
            if (this.mDbg) {
                this.mSm.log("setupInitialStateStack: E mInitialState=" + this.mInitialState.getName());
            }
            StateInfo stateInfo = this.mStateInfo.get(this.mInitialState);
            int i = 0;
            while (true) {
                this.mTempStateStackCount = i;
                if (stateInfo != null) {
                    this.mTempStateStack[this.mTempStateStackCount] = stateInfo;
                    stateInfo = stateInfo.parentStateInfo;
                    i = this.mTempStateStackCount + 1;
                } else {
                    this.mStateStackTopIndex = -1;
                    moveTempStateStackToStateStack();
                    return;
                }
            }
        }

        /* access modifiers changed from: private */
        public Message getCurrentMessage() {
            return this.mMsg;
        }

        /* access modifiers changed from: private */
        public IState getCurrentState() {
            return this.mStateStack[this.mStateStackTopIndex].state;
        }

        /* access modifiers changed from: private */
        public StateInfo addState(State state, State state2) {
            StateInfo stateInfo;
            String str;
            if (this.mDbg) {
                StateMachine stateMachine = this.mSm;
                StringBuilder append = new StringBuilder().append("addStateInternal: E state=").append(state.getName()).append(",parent=");
                if (state2 == null) {
                    str = "";
                } else {
                    str = state2.getName();
                }
                stateMachine.log(append.append(str).toString());
            }
            if (state2 != null) {
                stateInfo = this.mStateInfo.get(state2);
                if (stateInfo == null) {
                    stateInfo = addState(state2, (State) null);
                }
            } else {
                stateInfo = null;
            }
            StateInfo stateInfo2 = this.mStateInfo.get(state);
            if (stateInfo2 == null) {
                stateInfo2 = new StateInfo();
                this.mStateInfo.put(state, stateInfo2);
            }
            if (stateInfo2.parentStateInfo == null || stateInfo2.parentStateInfo == stateInfo) {
                stateInfo2.state = state;
                stateInfo2.parentStateInfo = stateInfo;
                stateInfo2.active = false;
                if (this.mDbg) {
                    this.mSm.log("addStateInternal: X stateInfo: " + stateInfo2);
                }
                return stateInfo2;
            }
            throw new RuntimeException("state already added");
        }

        /* access modifiers changed from: private */
        public void removeState(State state) {
            StateInfo stateInfo = this.mStateInfo.get(state);
            if (stateInfo != null && !stateInfo.active) {
                boolean z = false;
                if (Build.VERSION.SDK_INT < 24) {
                    Iterator<StateInfo> it = this.mStateInfo.values().iterator();
                    while (true) {
                        if (it.hasNext()) {
                            if (it.next().parentStateInfo == stateInfo) {
                                z = true;
                                break;
                            }
                        } else {
                            break;
                        }
                    }
                } else {
                    z = this.mStateInfo.values().stream().anyMatch(new Predicate() {
                        public final boolean test(Object obj) {
                            return StateMachine.SmHandler.lambda$removeState$0(StateMachine.SmHandler.StateInfo.this, (StateMachine.SmHandler.StateInfo) obj);
                        }
                    });
                }
                if (!z) {
                    this.mStateInfo.remove(state);
                }
            }
        }

        static /* synthetic */ boolean lambda$removeState$0(StateInfo stateInfo, StateInfo stateInfo2) {
            return stateInfo2.parentStateInfo == stateInfo;
        }

        private SmHandler(Looper looper, StateMachine stateMachine) {
            super(looper);
            this.mHasQuit = false;
            this.mDbg = false;
            this.mLogRecords = new LogRecords();
            this.mStateStackTopIndex = -1;
            HaltingState haltingState = new HaltingState();
            this.mHaltingState = haltingState;
            QuittingState quittingState = new QuittingState();
            this.mQuittingState = quittingState;
            this.mStateInfo = new HashMap<>();
            this.mTransitionInProgress = false;
            this.mDeferredMessages = new ArrayList<>();
            this.mSm = stateMachine;
            addState(haltingState, (State) null);
            addState(quittingState, (State) null);
        }

        /* access modifiers changed from: private */
        public void setInitialState(State state) {
            if (this.mDbg) {
                this.mSm.log("setInitialState: initialState=" + state.getName());
            }
            this.mInitialState = state;
        }

        /* access modifiers changed from: private */
        public void transitionTo(IState iState) {
            if (this.mTransitionInProgress) {
                Log.wtf(this.mSm.mName, "transitionTo called while transition already in progress to " + this.mDestState + ", new target state=" + iState);
            }
            this.mDestState = (State) iState;
            if (this.mDbg) {
                this.mSm.log("transitionTo: destState=" + this.mDestState.getName());
            }
        }

        /* access modifiers changed from: private */
        public void deferMessage(Message message) {
            if (this.mDbg) {
                this.mSm.log("deferMessage: msg=" + message.what);
            }
            Message obtainMessage = obtainMessage();
            obtainMessage.copyFrom(message);
            this.mDeferredMessages.add(obtainMessage);
        }

        /* access modifiers changed from: private */
        public void quit() {
            if (this.mDbg) {
                this.mSm.log("quit:");
            }
            sendMessage(obtainMessage(-1, mSmHandlerObj));
        }

        /* access modifiers changed from: private */
        public void quitNow() {
            if (this.mDbg) {
                this.mSm.log("quitNow:");
            }
            sendMessageAtFrontOfQueue(obtainMessage(-1, mSmHandlerObj));
        }

        /* access modifiers changed from: private */
        public boolean isQuit(Message message) {
            return message.what == -1 && message.obj == mSmHandlerObj;
        }

        /* access modifiers changed from: private */
        public boolean isDbg() {
            return this.mDbg;
        }

        /* access modifiers changed from: private */
        public void setDbg(boolean z) {
            this.mDbg = z;
        }
    }

    private void initStateMachine(String str, Looper looper) {
        this.mName = str;
        this.mSmHandler = new SmHandler(looper, this);
    }

    protected StateMachine(String str) {
        HandlerThread handlerThread = new HandlerThread(str);
        this.mSmThread = handlerThread;
        handlerThread.start();
        initStateMachine(str, this.mSmThread.getLooper());
    }

    protected StateMachine(String str, Looper looper) {
        initStateMachine(str, looper);
    }

    protected StateMachine(String str, Handler handler) {
        initStateMachine(str, handler.getLooper());
    }

    public final void addState(State state, State state2) {
        SmHandler.StateInfo unused = this.mSmHandler.addState(state, state2);
    }

    public final void addState(State state) {
        SmHandler.StateInfo unused = this.mSmHandler.addState(state, (State) null);
    }

    public final void removeState(State state) {
        this.mSmHandler.removeState(state);
    }

    public final void setInitialState(State state) {
        this.mSmHandler.setInitialState(state);
    }

    public final Message getCurrentMessage() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentMessage();
    }

    public final IState getCurrentState() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.getCurrentState();
    }

    public final void transitionTo(IState iState) {
        this.mSmHandler.transitionTo(iState);
    }

    public final void transitionToHaltingState() {
        SmHandler smHandler = this.mSmHandler;
        smHandler.transitionTo(smHandler.mHaltingState);
    }

    public final void deferMessage(Message message) {
        this.mSmHandler.deferMessage(message);
    }

    /* access modifiers changed from: protected */
    public void unhandledMessage(Message message) {
        if (this.mSmHandler.mDbg) {
            loge(" - unhandledMessage: msg.what=" + message.what);
        }
    }

    public final String getName() {
        return this.mName;
    }

    public final void setLogRecSize(int i) {
        this.mSmHandler.mLogRecords.setSize(i);
    }

    public final void setLogOnlyTransitions(boolean z) {
        this.mSmHandler.mLogRecords.setLogOnlyTransitions(z);
    }

    public final int getLogRecSize() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.size();
    }

    public final int getLogRecMaxSize() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.mMaxSize;
    }

    public final int getLogRecCount() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return 0;
        }
        return smHandler.mLogRecords.count();
    }

    public final LogRec getLogRec(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return null;
        }
        return smHandler.mLogRecords.get(i);
    }

    public final Collection<LogRec> copyLogRecs() {
        Vector vector = new Vector();
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            vector.addAll(smHandler.mLogRecords.mLogRecVector);
        }
        return vector;
    }

    public void addLogRec(String str) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.mLogRecords.add(this, smHandler.getCurrentMessage(), str, smHandler.getCurrentState(), smHandler.mStateStack[smHandler.mStateStackTopIndex].state, smHandler.mDestState);
        }
    }

    public final Handler getHandler() {
        return this.mSmHandler;
    }

    public final Message obtainMessage() {
        return Message.obtain(this.mSmHandler);
    }

    public final Message obtainMessage(int i) {
        return Message.obtain(this.mSmHandler, i);
    }

    public final Message obtainMessage(int i, Object obj) {
        return Message.obtain(this.mSmHandler, i, obj);
    }

    public final Message obtainMessage(int i, int i2) {
        return Message.obtain(this.mSmHandler, i, i2, 0);
    }

    public final Message obtainMessage(int i, int i2, int i3) {
        return Message.obtain(this.mSmHandler, i, i2, i3);
    }

    public final Message obtainMessage(int i, int i2, int i3, Object obj) {
        return Message.obtain(this.mSmHandler, i, i2, i3, obj);
    }

    public void sendMessage(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(obtainMessage(i));
        }
    }

    public void sendMessage(int i, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(obtainMessage(i, obj));
        }
    }

    public void sendMessage(int i, int i2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(obtainMessage(i, i2));
        }
    }

    public void sendMessage(int i, int i2, int i3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(obtainMessage(i, i2, i3));
        }
    }

    public void sendMessage(int i, int i2, int i3, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(obtainMessage(i, i2, i3, obj));
        }
    }

    public void sendMessage(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessage(message);
        }
    }

    public void sendMessageDelayed(int i, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(obtainMessage(i), j);
        }
    }

    public void sendMessageDelayed(int i, Object obj, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(obtainMessage(i, obj), j);
        }
    }

    public void sendMessageDelayed(int i, int i2, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(obtainMessage(i, i2), j);
        }
    }

    public void sendMessageDelayed(int i, int i2, int i3, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(obtainMessage(i, i2, i3), j);
        }
    }

    public void sendMessageDelayed(int i, int i2, int i3, Object obj, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(obtainMessage(i, i2, i3, obj), j);
        }
    }

    public void sendMessageDelayed(Message message, long j) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageDelayed(message, j);
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(obtainMessage(i));
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(int i, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, obj));
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(int i, int i2) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2));
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(int i, int i2, int i3) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3));
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(int i, int i2, int i3, Object obj) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(obtainMessage(i, i2, i3, obj));
        }
    }

    /* access modifiers changed from: protected */
    public final void sendMessageAtFrontOfQueue(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.sendMessageAtFrontOfQueue(message);
        }
    }

    /* access modifiers changed from: protected */
    public final void removeMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.removeMessages(i);
        }
    }

    /* access modifiers changed from: protected */
    public final void removeDeferredMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            Iterator it = smHandler.mDeferredMessages.iterator();
            while (it.hasNext()) {
                if (((Message) it.next()).what == i) {
                    it.remove();
                }
            }
        }
    }

    /* access modifiers changed from: protected */
    public final boolean hasDeferredMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        Iterator it = smHandler.mDeferredMessages.iterator();
        while (it.hasNext()) {
            if (((Message) it.next()).what == i) {
                return true;
            }
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean hasMessages(int i) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.hasMessages(i);
    }

    /* access modifiers changed from: protected */
    public final boolean isQuit(Message message) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return message.what == -1;
        }
        return smHandler.isQuit(message);
    }

    public final void quit() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.quit();
        }
    }

    public final void quitNow() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.quitNow();
        }
    }

    public boolean isDbg() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler == null) {
            return false;
        }
        return smHandler.isDbg();
    }

    public void setDbg(boolean z) {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.setDbg(z);
        }
    }

    public void start() {
        SmHandler smHandler = this.mSmHandler;
        if (smHandler != null) {
            smHandler.completeConstruction();
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println(getName() + ":");
        printWriter.println(" total records=" + getLogRecCount());
        for (int i = 0; i < getLogRecSize(); i++) {
            printWriter.println(" rec[" + i + "]: " + getLogRec(i).toString());
            printWriter.flush();
        }
        printWriter.println("curState=" + getCurrentState().getName());
    }

    public String toString() {
        String str;
        String str2 = "(null)";
        try {
            str = this.mName.toString();
            try {
                str2 = this.mSmHandler.getCurrentState().getName().toString();
            } catch (NullPointerException unused) {
            }
        } catch (NullPointerException unused2) {
            str = str2;
        }
        return "name=" + str + " state=" + str2;
    }

    /* access modifiers changed from: protected */
    public void logAndAddLogRec(String str) {
        addLogRec(str);
        log(str);
    }

    /* access modifiers changed from: protected */
    public void log(String str) {
        Log.d(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void logd(String str) {
        Log.d(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void logv(String str) {
        Log.v(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void logi(String str) {
        Log.i(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void logw(String str) {
        Log.w(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void loge(String str) {
        Log.e(this.mName, str);
    }

    /* access modifiers changed from: protected */
    public void loge(String str, Throwable th) {
        Log.e(this.mName, str, th);
    }
}
