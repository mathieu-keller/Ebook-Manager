package tech.mathieu.util;

public record Pair<P1, P2>(P1 left, P2 right) {
  public static <P1, P2> Pair<P1, P2> of(P1 left, P2 right) {
    return new Pair<>(left, right);
  }
}
