package tech.mathieu.util;

import java.util.Objects;

public record Pair<P1, P2>(P1 left, P2 right) {

  public static <P1, P2> Pair<P1, P2> of(P1 left, P2 right) {
    return new Pair<>(left, right);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Pair<?, ?> pair = (Pair<?, ?>) o;
    return Objects.equals(left, pair.left) && Objects.equals(right, pair.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }
}
