package tech.mathieu.book;

import io.quarkus.mongodb.panache.common.MongoEntity;
import io.quarkus.mongodb.panache.reactive.ReactivePanacheMongoEntityBase;
import org.bson.codecs.pojo.annotations.BsonId;

@MongoEntity(collection = "Book")
public class Book extends ReactivePanacheMongoEntityBase {
  @BsonId public String id;

  public String name;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Book{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
  }
}
