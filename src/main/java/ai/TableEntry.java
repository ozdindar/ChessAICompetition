package ai;

public class TableEntry {
    enum EntryType { Exact, UpperBound, LowerBound};

    EntryType entryType;

    EvaluatedMove  move;
    int depth;

    public TableEntry(EntryType entryType, EvaluatedMove move, int depth) {
        this.entryType = entryType;

        this.move = move;
        this.depth = depth;
    }
}
