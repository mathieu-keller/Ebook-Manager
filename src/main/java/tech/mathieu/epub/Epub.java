package tech.mathieu.epub;

import tech.mathieu.epub.opf.Opf;

public record Epub(Opf opf, byte[] cover) {
}
