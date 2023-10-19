package Handlers;

public class Move {
    private Square start;
    private Square end;
  
    public Move(Square start, Square end)
    {
        this.start = start;
        this.end = end;
    }

    public Square getStart() {
        return this.start;
    }

    public Square getEnd() {
        return this.end;
    }
}
