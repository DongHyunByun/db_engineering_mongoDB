package org.de.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.codecs.pojo.annotations.BsonProperty;
import org.bson.types.ObjectId;

import java.time.LocalDateTime;

@AllArgsConstructor //모든 변수를 생성자 함수로 받게 해줌
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @BsonId
    ObjectId id;
    String name;
    @BsonProperty("updated_at") //실제 DB의 필드이름은 스네이크 케이스인데 카멜케이스 변수를 써야하므로 다음과 같이 명시
    LocalDateTime updatedAt;
    String contents;
    int price;
}
