package tech.mathieu.epub;

import java.util.Arrays;
import java.util.Objects;
import tech.mathieu.epub.opf.Opf;

public record Epub(Opf opf, byte[] cover) {
  @Override
  public String toString() {
    return "Epub{" + "opf=" + opf + ", cover=" + Arrays.toString(cover) + '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Epub epub = (Epub) o;
    return Objects.equals(opf, epub.opf) && Arrays.equals(cover, epub.cover);
  }

  @Override
  public int hashCode() {
    int result = Objects.hash(opf);
    result = 31 * result + Arrays.hashCode(cover);
    return result;
  }
}
