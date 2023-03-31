package greendar.domain.eventtodo.model;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class TodoImage {

    private  static final int MAX_IMAGE_LENGTH = 10_000;

    @Column(name = "todo_image_url",nullable = false)
    private  String todoImageUrl;

    protected TodoImage(){

    }
    public TodoImage(String todoImageUrl){
        validate(todoImageUrl);
        this.todoImageUrl = todoImageUrl;
    }
    private void validate(String todoImageUrl){
        if(todoImageUrl.length() > MAX_IMAGE_LENGTH){
//            throw  new InvalidImageException();  Need Invalid class in domain for  Exception
            throw  new RuntimeException("Wrong Length");
        }
    }
    @Override
    public boolean equals(Object o){
        if(this == o ){
            return true;
        }
        if(!(o instanceof  TodoImage)){
            return false;
        }
        TodoImage todoImage = (TodoImage) o;
        return Objects.equals(todoImageUrl, todoImage.todoImageUrl);
    }
    @Override
    public int hashCode(){
        return Objects.hashCode(todoImageUrl);
    }
}