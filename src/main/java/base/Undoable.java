package base;

public interface Undoable<M> {
    void undo(M m);
}
