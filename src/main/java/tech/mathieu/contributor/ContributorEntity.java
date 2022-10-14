package tech.mathieu.contributor;

import tech.mathieu.book.BookEntity;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "contributor")
public class ContributorEntity {
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Id
    @Column(name = "id", nullable = false)
    Long id;

    @Column(name = "name", nullable = true, length = 4000)
    String name;

    @ManyToMany(
            targetEntity = BookEntity.class,
            fetch = FetchType.LAZY,
            cascade = CascadeType.MERGE
    )
    @JoinTable(
            name = "contributor2book",
            joinColumns = @JoinColumn(name = "contributor_id"),
            inverseJoinColumns = @JoinColumn(name = "BOOK_ID")
    )
    List<BookEntity> bookEntities;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<BookEntity> getBookEntities() {
        return bookEntities;
    }

    public void setBookEntities(List<BookEntity> bookEntities) {
        this.bookEntities = bookEntities;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContributorEntity that)) {
            return false;
        }
        return id == that.id && Objects.equals(name, that.name) && Objects.equals(bookEntities, that.bookEntities);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bookEntities);
    }
}
