package be.ac.kuleuven.cs.drama.gui;

public class ExecutionState {
   private ExecutionState(String name, boolean canExecute, boolean isExecuting, boolean isStarted, boolean isInitial) {
      this.name = name;
      this.canExecute = canExecute;
      this.isExecuting = isExecuting;
      this.isStarted = isStarted;
      this.isInitial = isInitial;
   }
   
   private final String name;
   private final boolean canExecute;
   private final boolean isExecuting;
   private final boolean isStarted;
   private final boolean isInitial;
   
   public String getName() {
      return name;
   }
   
   public boolean canExecute() {
      return canExecute;
   }
   
   public boolean isExecuting() {
      return isExecuting;
   }
   
   public boolean isStarted() {
      return isStarted;
   }
   
   public boolean isInitial() {
      return isInitial;
   }
   
   public static final ExecutionState NO_CODE = new ExecutionState("Geen machinecode", false, false, false, false);
   public static final ExecutionState NOT_STARTED = new ExecutionState("Niet gestart", true, false, false, true);
   public static final ExecutionState RUNNING = new ExecutionState("Loopt", true, true, true, false);
   public static final ExecutionState STEPPING = new ExecutionState("Stapt", true, true, true, false);
   public static final ExecutionState SUSPENDED = new ExecutionState("Onderbroken", true, false, true, false);
   public static final ExecutionState FINISHED = new ExecutionState("Voltooid", true, false, false, false);
}
